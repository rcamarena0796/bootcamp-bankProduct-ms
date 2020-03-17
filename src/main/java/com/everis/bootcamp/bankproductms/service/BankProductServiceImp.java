package com.everis.bootcamp.bankproductms.service;

import java.net.URI;
import java.util.Date;

import com.everis.bootcamp.bankproductms.dao.BankProductRepository;
import com.everis.bootcamp.bankproductms.model.BankProduct;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import reactor.core.publisher.Flux;

@Service
public class BankProductServiceImp implements BankProductService{

    private static final Logger log = LoggerFactory.getLogger(BankProductServiceImp.class);

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
    public Mono<BankProduct> findByClientNumDoc(String numDoc) {
        return bankRepo.findByClientNumDoc(numDoc);
    }

    @Override
    public Flux<BankProduct> findAll() {
        return bankRepo.findAll();
    }

    @Override
    public Mono<BankProduct> update(BankProduct bp, String id) {
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
    }

    @Override
    public Mono<Void> delete(BankProduct bp) {
        return bankRepo.delete(bp);
    }

    @Override
    public Mono<BankProduct> save(BankProduct bp) {
        if (bp.getCreateDate() == null) {
            bp.setCreateDate(new Date());
        } else {
            bp.setCreateDate(bp.getCreateDate());
        }

        return bankRepo.save(bp);
    }
}
