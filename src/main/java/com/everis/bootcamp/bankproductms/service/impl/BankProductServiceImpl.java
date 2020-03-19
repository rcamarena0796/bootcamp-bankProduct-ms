package com.everis.bootcamp.bankproductms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.everis.bootcamp.bankproductms.dao.BankProductRepository;
import com.everis.bootcamp.bankproductms.dao.BankProductTypeRepository;
import com.everis.bootcamp.bankproductms.model.BankProduct;
import com.everis.bootcamp.bankproductms.service.BankProductService;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;

@Service
public class BankProductServiceImpl implements BankProductService {

    private static final Logger logger = LoggerFactory.getLogger(BankProductServiceImpl.class);

    @Autowired
    private BankProductRepository bankRepo;
    private BankProductTypeRepository bankProdTypeRepo;


    @Override
    public Mono<BankProduct> findByNumAccount(String name) {
        return bankRepo.findByNumAccount(name);
    }

    @Override
    public Mono<BankProduct> findById(String id) {
        return bankRepo.findById(id);
    }

    @Override
    public Flux<BankProduct> findByClientNumDoc(String numDoc) {
        return bankRepo.findAllByClientNumDoc(numDoc);
    }

    @Override
    public Flux<BankProduct> findAll() {
        return bankRepo.findAll();
    }

    @Override
    public Mono<BankProduct> update(BankProduct bp, String id) {
        try {
            return bankRepo.findById(id)
                    .flatMap(dbBankProd -> {

                        //CreateDate
                        if (bp.getCreateDate() != null) {
                            dbBankProd.setCreateDate(bp.getCreateDate());
                        }

                        //ModifyDate
                        dbBankProd.setModifyDate(new Date());

                        //idProdType
                        if (bp.getIdProdType() != null) {
                            dbBankProd.setIdProdType(bp.getIdProdType());
                        }

                        //total
                        if (bp.getTotal() != 0) {
                            dbBankProd.setTotal(bp.getTotal());
                        }

                        //clientNumDoc
                        if (bp.getClientNumDoc() != null) {
                            dbBankProd.setClientNumDoc(bp.getClientNumDoc());
                        }

                        //numAccount
                        if (bp.getNumAccount() != null) {
                            dbBankProd.setNumAccount(bp.getNumAccount());
                        }

                        //bankName
                        if (bp.getBankName() != null) {
                            dbBankProd.setBankName(bp.getBankName());
                        }


                        return bankRepo.save(dbBankProd);

                    }).switchIfEmpty(Mono.empty());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Void> delete(String id) {
        try {
            return bankRepo.findById(id).flatMap(cl -> {
                return bankRepo.delete(cl);
            });
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private Mono<Boolean> validClient(String clientNumDoc) {
        Flux<BankProduct> productosCliente = bankRepo.findAllByClientNumDoc(clientNumDoc);
        Mono<Long> cantAccounts = productosCliente.count();
        Mono<Boolean> ret = cantAccounts.map(c -> {
            if (c < 1) {
                return true;
            } else return false;
        });

        return ret;
    }

    private Mono<String> getClientTypeFromApi(String numDoc) {
        String url = "http://localhost:8001/api/client/getClientType/" + numDoc;
        return WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<BankProduct> saveV2(BankProduct bp) {
        try {
            if (bp.getCreateDate() == null) {
                bp.setCreateDate(new Date());
            } else {
                bp.setCreateDate(bp.getCreateDate());
            }
            String idProdType = bp.getIdProdType();
            //traer al tipo de cliente de la api clientes
            Mono<String> clientType = getClientTypeFromApi(bp.getClientNumDoc());
            return clientType.flatMap(ct -> {
                logger.info("client type -> " + ct);
                if (!ct.equals("-1")) {
                    //si al final existe, buscar el tipo de cliente
                    if (ct.equals("1")) { //personal

                        if (idProdType.equals("1") || idProdType.equals("2") || idProdType.equals("3"))
                        //si lo es, buscar el numero de cuentas con numDoc del cliente
                        {
                            //buscar todos los productos que coincidan con numDoc y idProdType 1,2 o 3
                            List<String> ids = Arrays.asList("1", "2", "3");
                            Flux<BankProduct> productosCliente = bankRepo.findByClientNumDocAndIdProdTypeIn(bp.getClientNumDoc(), ids);
                            Mono<Long> cantAccounts = productosCliente.count();
                            return cantAccounts.flatMap(c -> {
                                logger.info("cantidad de cuentas = " + c.toString());
                                if (c >= 1) {
                                    return Mono.error(new Exception("Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo."));
                                }
                                return bankRepo.save(bp);
                            });
                        }
                        return Mono.error(new Exception("Tipo de cuenta no soportado"));

                    } else if (ct.equals("2")) { //empresarial
                        //ver si el tipo de cuenta es ahorro o plazo fijo
                        if (idProdType.equals("1") || idProdType.equals("2")) {
                            //si es una de esas, no dejar guardar
                            return Mono.error(new Exception("Un cliente empresarial no puede tener cuenta de ahorro ni de plazo fijo."));

                        } else if (idProdType.equals("3")) {
                            //si es cuenta corriente guardar nomas
                            return bankRepo.save(bp);
                        }
                        return Mono.error(new Exception("Tipo de cuenta no soportado"));
                    }

                    return Mono.error(new Exception("Tipo de cliente no soportado"));


                } else {
                    return Mono.error(new Exception("Cliente no registrado"));
                }
            });

        } catch (Exception e) {
            return Mono.error(e);
        }

    }

    @Override
    public Mono<BankProduct> save(BankProduct bp) {
        try {
            if (bp.getCreateDate() == null) {
                bp.setCreateDate(new Date());
            } else {
                bp.setCreateDate(bp.getCreateDate());
            }
            //verificar si el cliente existe

            //verificar si es ahorro o corriente o plazo fijo
            String tipoCuenta = bp.getIdProdType();
            logger.info("tipoCuenta:" + tipoCuenta);
            if (tipoCuenta.equals("1") || tipoCuenta.equals("2") || tipoCuenta.equals("3"))
            //si lo es, buscar el numero de cuentas con numDoc del cliente
            {
                Flux<BankProduct> productosCliente = bankRepo.findAllByClientNumDoc(bp.getClientNumDoc());
                Mono<Long> cantAccounts = productosCliente.count();
                long n = cantAccounts.block();
                if (n > 1) {
                    return Mono.error(new Exception("Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo."));
                }
            }
            return bankRepo.save(bp);

            /*return bankRepo.findAllByClientNumDoc(bp.getClientNumDoc())
                    .flatMap(productosCliente)
*/

        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
