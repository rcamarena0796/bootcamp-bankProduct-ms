package com.everis.bootcamp.bankproductms.dao;


import com.everis.bootcamp.bankproductms.model.BankProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BankProductRepository extends ReactiveMongoRepository<BankProduct, String> {
    public Flux<BankProduct> findAllByClientNumDoc(String clientNumDoc);
    public Mono<BankProduct> findByNumAccount(String numAccount);
    public Mono<Boolean> existsByClientNumDoc(String clientNumDoc);
    public Flux<BankProduct> findByClientNumDocAndIdProdTypeIn(String numDoc, List<String> idProdType);
}