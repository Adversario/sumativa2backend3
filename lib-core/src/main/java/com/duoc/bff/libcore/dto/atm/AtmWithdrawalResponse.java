package com.duoc.bff.libcore.dto.atm;
public class AtmWithdrawalResponse {
  public String status; public String message; public Double newBalance; public String withdrawalId;
  public AtmWithdrawalResponse(){}
  public AtmWithdrawalResponse(String status, String message, Double newBalance, String withdrawalId){
    this.status=status; this.message=message; this.newBalance=newBalance; this.withdrawalId=withdrawalId;
  }
}
