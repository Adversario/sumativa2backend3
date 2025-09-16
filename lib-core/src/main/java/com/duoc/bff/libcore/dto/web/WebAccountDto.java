package com.duoc.bff.libcore.dto.web;
public class WebAccountDto {
  public Long id; public Long customerId; public String type; public Double balance;
  public WebAccountDto() {}
  public WebAccountDto(Long id, Long customerId, String type, Double balance){ this.id=id; this.customerId=customerId; this.type=type; this.balance=balance; }
}
