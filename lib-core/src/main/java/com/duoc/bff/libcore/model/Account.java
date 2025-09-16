package com.duoc.bff.libcore.model;
public class Account {
  private Long id; private Long customerId; private String type; private Double balance;
  public Account() {}
  public Account(Long id, Long customerId, String type, Double balance){ this.id=id; this.customerId=customerId; this.type=type; this.balance=balance; }
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Long getCustomerId(){return customerId;} public void setCustomerId(Long c){this.customerId=c;}
  public String getType(){return type;} public void setType(String t){this.type=t;}
  public Double getBalance(){return balance;} public void setBalance(Double b){this.balance=b;}
}
