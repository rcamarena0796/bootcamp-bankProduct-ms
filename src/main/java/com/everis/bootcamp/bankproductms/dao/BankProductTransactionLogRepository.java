package com.everis.bootcamp.bankproductms.dao;


import com.everis.bootcamp.bankproductms.model.BankProductTransactionLog;
import com.everis.bootcamp.bankproductms.model.BankProductType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankProductTransactionLogRepository extends ReactiveMongoRepository<BankProductTransactionLog, String> {
    public Flux<BankProductTransactionLog> findAllByClientNumDoc(String clientNumDoc);
}