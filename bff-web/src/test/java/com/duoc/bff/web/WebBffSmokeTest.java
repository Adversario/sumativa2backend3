package com.duoc.bff.web;
import org.junit.jupiter.api.*; import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort; import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient; import java.util.Map;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WebBffSmokeTest {
  @LocalServerPort int port;
  @Test void loginAndListAccounts(){
    var client = WebTestClient.bindToServer().baseUrl("http://localhost:"+port).build();
    client.post().uri("/auth/login").bodyValue(Map.of("username","alice","password","password"))
      .exchange().expectStatus().is2xxSuccessful().expectBody().jsonPath("$.token").exists();
  }
}
