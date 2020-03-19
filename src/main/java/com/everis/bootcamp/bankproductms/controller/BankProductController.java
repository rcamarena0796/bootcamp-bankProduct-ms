package com.everis.bootcamp.bankproductms.controller;

import com.everis.bootcamp.bankproductms.model.BankProduct;
import com.everis.bootcamp.bankproductms.service.BankProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.validation.Valid;

@Api(tags = "Bank Product API",  value = "Operations for bank products")
@RestController
@RequestMapping("/api/bankProduct")
public class BankProductController {

    @Autowired
    private BankProductService service;


    @GetMapping("/test")
    public Mono<BankProduct> saludo() {
        BankProduct hola = new BankProduct();
        hola.setBankName("BCP");
        return Mono.justOrEmpty(hola);
    }

    @ApiOperation(value = "Service used to find all bank products")
    @GetMapping("/findAll")
    public Flux<BankProduct> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Service used to find a bank product by id")
    @GetMapping("/find/{id}")
    public Mono<BankProduct> findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    //GUARDAR UN CLIENTE
    @ApiOperation(value = "Service used to save a bank product")
    @PostMapping("/save")
    public Mono<ResponseEntity<BankProduct>> create(@Valid @RequestBody BankProduct bp) {
        return service.saveV2(bp).map(b -> ResponseEntity.created(URI.create("/api/bankproduct".concat(b.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(b));
    }


    //ACTUALIZAR UN CLIENTE
    @ApiOperation(value = "Service used to update a bank product")
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<BankProduct>> update(@PathVariable("id") String id, @RequestBody BankProduct bp) {
        return service.update(bp, id)
                .map(b -> ResponseEntity.created(URI.create("/api/bankproduct".concat(b.getId())))
                        .contentType(MediaType.APPLICATION_JSON).body(b))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    //ELIMINAR UN CLIENTE
    @ApiOperation(value = "Service used to delete a bank product")
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}