package com.duoc.bff.libcore.model;
public class Transaction {
  private Long id; private Long accountId; private String date; private String type; private Double amount; private String description;
  public Transaction() {}
  public Transaction(Long id, Long accountId, String date, String type, Double amount, String description){
    this.id=id; this.accountId=accountId; this.date=date; this.type=type; this.amount=amount; this.description=description;
  }
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Long getAccountId(){return accountId;} public void setAccountId(Long a){this.accountId=a;}
  public String getDate(){return date;} public void setDate(String d){this.date=d;}
  public String getType(){return type;} public void setType(String t){this.type=t;}
  public Double getAmount(){return amount;} public void setAmount(Double a){this.amount=a;}
  public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
}
