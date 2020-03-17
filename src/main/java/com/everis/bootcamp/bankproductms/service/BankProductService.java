package com.everis.bootcamp.bankproductms.service;

import com.everis.bootcamp.bankproductms.model.BankProduct;
import com.everis.bootcamp.bankproductms.model.BankProductType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankProductService {
    public Mono<BankProduct> findByNumAccount(String name);
    public Mono<BankProduct> findById(String id);
    public Mono<BankProduct> findByClientNumDoc(String numDoc);
    public Flux<BankProduct> findAll();
    public Mono<BankProduct> update(BankProduct bp, String id);
    public Mono<Void> delete(String bp);
    public Mono<BankProduct> save (BankProduct bp);
}
