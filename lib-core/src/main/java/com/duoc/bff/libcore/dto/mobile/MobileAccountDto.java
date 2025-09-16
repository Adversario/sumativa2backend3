package com.duoc.bff.libcore.dto.mobile;
public class MobileAccountDto {
  public Long id; public String type; public Double balance;
  public MobileAccountDto() {}
  public MobileAccountDto(Long id, String type, Double balance){ this.id=id; this.type=type; this.balance=balance; }
}
