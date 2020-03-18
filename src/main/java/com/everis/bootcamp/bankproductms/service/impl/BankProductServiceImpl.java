package com.everis.bootcamp.bankproductms.service.impl;

import java.util.Date;

import com.everis.bootcamp.bankproductms.dao.BankProductRepository;
import com.everis.bootcamp.bankproductms.model.BankProduct;
import com.everis.bootcamp.bankproductms.service.BankProductService;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;
import rx.RxReactiveStreams;

@Service
public class BankProductServiceImpl implements BankProductService {

    private static final Logger logger = LoggerFactory.getLogger(BankProductServiceImpl.class);

    @Autowired
    private BankProductRepository bankRepo;


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
            logger.info("tipoCuenta:"+tipoCuenta);
            if (tipoCuenta.equals("1") || tipoCuenta.equals("2") || tipoCuenta.equals("3"))
            //si lo es, buscar el numero de cuentas con numDoc del cliente
            {
                Flux<BankProduct> productosCliente = bankRepo.findAllByClientNumDoc(bp.getClientNumDoc());
                Mono<Long> cantAccounts = productosCliente.count();
                long n = cantAccounts.block();
                if(n>1){
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
