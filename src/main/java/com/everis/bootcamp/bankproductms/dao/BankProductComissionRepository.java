package com.everis.bootcamp.bankproductms.dao;

import com.everis.bootcamp.bankproductms.model.BankProductComission;
import com.everis.bootcamp.bankproductms.model.BankProductType;
import com.everis.bootcamp.bankproductms.model.Comission;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import reactor.core.publisher.Mono;

public interface BankProductComissionRepository extends ReactiveMongoRepository<BankProductComission, String> {
}