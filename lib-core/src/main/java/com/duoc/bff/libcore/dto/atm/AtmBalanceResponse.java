package com.duoc.bff.libcore.dto.atm;
public class AtmBalanceResponse {
  public Long accountId; public Double balance;
  public AtmBalanceResponse(){}
  public AtmBalanceResponse(Long accountId, Double balance){ this.accountId=accountId; this.balance=balance; }
}
