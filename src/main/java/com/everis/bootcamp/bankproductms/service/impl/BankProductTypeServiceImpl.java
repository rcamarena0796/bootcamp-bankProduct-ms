package com.everis.bootcamp.bankproductms.service.impl;

import com.everis.bootcamp.bankproductms.dao.BankProductTypeRepository;
import com.everis.bootcamp.bankproductms.model.BankProductType;
import com.everis.bootcamp.bankproductms.service.BankProductTypeService;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BankProductTypeServiceImpl implements BankProductTypeService {

    private static final Logger log = LoggerFactory.getLogger(BankProductTypeServiceImpl.class);

    @Autowired
    private BankProductTypeRepository repo;


    @Override
    public Mono<BankProductType> findByNumId(int numId) {
        return repo.findByNumId(numId);
    }
}