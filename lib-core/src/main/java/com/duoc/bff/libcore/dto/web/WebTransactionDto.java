package com.duoc.bff.libcore.dto.web;
public class WebTransactionDto {
  public Long id; public Long accountId; public String date; public String type; public Double amount; public String description;
  public WebTransactionDto() {}
  public WebTransactionDto(Long id, Long accountId, String date, String type, Double amount, String description){
    this.id=id; this.accountId=accountId; this.date=date; this.type=type; this.amount=amount; this.description=description;
  }
}
