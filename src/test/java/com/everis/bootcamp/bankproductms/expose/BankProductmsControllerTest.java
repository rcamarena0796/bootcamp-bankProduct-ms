package com.everis.bootcamp.bankproductms.expose;



import com.everis.bootcamp.bankproductms.model.BankProduct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest
public class BankProductmsControllerTest {

    @Autowired
    private WebTestClient webClient;

    private static BankProduct bpTest;

    @BeforeAll
    public static void setup() {
        bpTest = new BankProduct();
        bpTest.setBankName("BCP");
    }

    @Test
    public void test_controller_hola_mundo(){
        webClient.get()
                .uri("/api/bankProduct/test")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BankProduct.class)
                .isEqualTo(bpTest);
    }
}
