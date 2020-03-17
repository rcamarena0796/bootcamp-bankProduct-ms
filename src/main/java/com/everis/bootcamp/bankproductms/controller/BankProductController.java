package com.everis.bootcamp.bankproductms.controller;

import com.everis.bootcamp.bankproductms.model.BankProduct;
import com.everis.bootcamp.bankproductms.service.BankProductService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.validation.Valid;

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

    @GetMapping("/selectAll")
    public Mono<ResponseEntity<Flux<BankProduct>>> list() {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
    }

    @GetMapping("/findAll")
    public Flux<BankProduct> findAll() {
        return service.findAll();
    }

    @GetMapping("/findById/{id}")
    public Mono<BankProduct> findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @GetMapping("/findByNumDoc/{numDoc}")
    public Mono<BankProduct> findByNumDoc(@PathVariable("numDoc") String numDoc) {
        return service.findByClientNumDoc(numDoc);
    }


    @PostMapping("/save")
    public Mono<ResponseEntity<BankProduct>> create(@Valid @RequestBody BankProduct bp) {
        return service.save(bp).map(b -> ResponseEntity.created(URI.create("/api/bankproduct".concat(b.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(b));
    }


    //ACTUALIZAR UN CLIENTE
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<BankProduct>> update(@PathVariable("id") String id, @RequestBody BankProduct bp) {
        return service.update(bp, id)
                .map(b -> ResponseEntity.created(URI.create("/api/bankproduct".concat(b.getId())))
                        .contentType(MediaType.APPLICATION_JSON).body(b))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    //ELIMINAR UN CLIENTE
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.findById(id)
                .flatMap(c -> {
                    return service.delete(c)
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}