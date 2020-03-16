package com.everis.bootcamp.bankproductms.dao;


import com.everis.bootcamp.bankproductms.model.BankProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;



import reactor.core.publisher.Mono;

public interface BankProductRepository extends ReactiveMongoRepository<BankProduct, String> {
    public Mono<BankProduct> findByClientNumDoc(String clientNumDoc);
    public Mono<BankProduct> findByNumAccount(String numAccount);
}
