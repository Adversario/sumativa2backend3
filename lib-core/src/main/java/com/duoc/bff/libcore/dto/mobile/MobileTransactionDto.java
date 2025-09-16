package com.duoc.bff.libcore.dto.mobile;
public class MobileTransactionDto {
  public String date; public String type; public Double amount;
  public MobileTransactionDto(){}
  public MobileTransactionDto(String date, String type, Double amount){ this.date=date; this.type=type; this.amount=amount; }
}
