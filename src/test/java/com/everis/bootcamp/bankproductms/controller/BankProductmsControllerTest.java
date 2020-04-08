package com.everis.bootcamp.bankproductms.controller;

import static org.mockito.Mockito.when;

import com.everis.bootcamp.bankproductms.dao.BankProductRepository;
import com.everis.bootcamp.bankproductms.dto.MessageDto;
import com.everis.bootcamp.bankproductms.model.BankProduct;
import com.everis.bootcamp.bankproductms.service.BankProductService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BankProductController.class)
@Import(BankProductService.class)
public class BankProductmsControllerTest {

  @Mock
  private List<BankProduct> expectedBankProdList;

  @Mock
  private BankProduct expectedBankProd;
  @Mock
  private MessageDto message;


  @BeforeEach
  void setUp() throws ParseException {
    String string = "2020-04-08";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date = format.parse(string);

    expectedBankProd = BankProduct.builder().id("1").numAccount("123456").bankId("1")
        .idProdType("1").total(1000)
        .minFin(150).currentTransNumber(2).clientNumDoc("132456").build();

    message = MessageDto.builder().code("1").message("success").build();

    expectedBankProdList = Arrays.asList(
        BankProduct.builder().id("1").numAccount("123456").bankId("1").idProdType("1").total(1000)
            .minFin(150).currentTransNumber(2).clientNumDoc("132456").build(),
        BankProduct.builder().id("2").numAccount("1234565").bankId("1").idProdType("1").total(1000)
            .minFin(150).currentTransNumber(2).clientNumDoc("1324356").build()
    );
  }

  @MockBean
  protected BankProductService service;

  @Autowired
  private WebTestClient webClient;

  private static BankProduct bpTest;

  @BeforeAll
  public static void setup() {
    bpTest = new BankProduct();
    bpTest.setBankId("1");
  }

  @Test
  public void test_controller_hola_mundo() {
    webClient.get()
        .uri("/bankprod/test")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(BankProduct.class)
        .isEqualTo(bpTest);
  }

  @Test
  void getAll() {
    when(service.findAll()).thenReturn(Flux.fromIterable(expectedBankProdList));

    webClient.get().uri("/bankprod/findAll")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(BankProduct.class)
        .isEqualTo(expectedBankProdList);
  }

  @Test
  void getBankProdById_whenExists_returnCorrect() {
    when(service.findById(expectedBankProd.getId())).thenReturn(Mono.just(expectedBankProd));

    webClient.get()
        .uri("/bankprod/findById/{id}", expectedBankProd.getId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(BankProduct.class)
        .isEqualTo(expectedBankProd);
  }

  @Test
  void getBankProdById_whenNotExist_returnNotFound() {
    String id = "-1";
    when(service.findById(id)).thenReturn(Mono.error(new NotFoundException()));

    webClient.get()
        .uri("bankprod/findById/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void getBankProdByNumDoc_whenExists_returnCorrect() {
    when(service.findByClientNumDoc(expectedBankProd.getClientNumDoc()))
        .thenReturn(Flux.fromIterable(expectedBankProdList));

    webClient.get()
        .uri("/bankprod/find/{numAccount}", expectedBankProd.getClientNumDoc())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(BankProduct.class)
        .isEqualTo(expectedBankProdList);
  }

  @Test
  void getBankIdByNumProd_whenExists_returnBankId() {
    when(service.getBankId(expectedBankProd.getNumAccount())).thenReturn(Mono.just(expectedBankProd.getBankId()));

    webClient.get()
        .uri("/bankprod/getBankId/{id}", expectedBankProd.getNumAccount())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .isEqualTo(expectedBankProd.getBankId());
  }
}