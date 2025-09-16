package com.duoc.bff.libcore.config;
import com.duoc.bff.libcore.repo.InMemoryDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
@Configuration
public class LegacyDataConfig {
  @Bean public ObjectMapper objectMapper(){ return new ObjectMapper(); }
  @Bean public InMemoryDataRepository repo(){ return new InMemoryDataRepository(); }
}
