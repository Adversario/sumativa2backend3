package com.duoc.bff.libcore.config;
import com.duoc.bff.libcore.model.*; import com.duoc.bff.libcore.repo.InMemoryDataRepository;
import com.fasterxml.jackson.core.type.TypeReference; import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
import java.io.InputStream; import java.util.List;
@Configuration
public class DataLoader {
  @Bean CommandLineRunner seedData(ObjectMapper om, InMemoryDataRepository repo){
    return args -> {
      try (InputStream c = getClass().getResourceAsStream("/data/customers.json");
           InputStream a = getClass().getResourceAsStream("/data/accounts.json");
           InputStream t = getClass().getResourceAsStream("/data/transactions.json")) {
        List<Customer> cs = om.readValue(c, new TypeReference<>(){});
        List<Account> as = om.readValue(a, new TypeReference<>(){});
        List<Transaction> ts = om.readValue(t, new TypeReference<>(){});
        repo.load(cs, as, ts);
      }
    };
  }
}
