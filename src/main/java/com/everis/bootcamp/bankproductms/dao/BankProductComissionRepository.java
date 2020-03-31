package com.everis.bootcamp.bankproductms.dao;

import com.everis.bootcamp.bankproductms.model.BankProductComission;
import com.everis.bootcamp.bankproductms.model.BankProductType;
import com.everis.bootcamp.bankproductms.model.Comission;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface BankProductComissionRepository extends ReactiveMongoRepository<BankProductComission, String> {

    Flux<BankProductComission> findByComissionDateBeforeAndComissionDateAfter(Date startDate, Date endDate);

    Flux<BankProductComission> findAllByComissionDateBetween(Date startDate, Date endDate);
}