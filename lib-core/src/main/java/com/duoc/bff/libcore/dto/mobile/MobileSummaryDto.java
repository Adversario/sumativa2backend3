package com.duoc.bff.libcore.dto.mobile;
public class MobileSummaryDto {
  public Long customerId; public int accountCount; public Double totalBalance;
  public MobileSummaryDto(){}
  public MobileSummaryDto(Long customerId, int accountCount, Double totalBalance){
    this.customerId=customerId; this.accountCount=accountCount; this.totalBalance=totalBalance;
  }
}
