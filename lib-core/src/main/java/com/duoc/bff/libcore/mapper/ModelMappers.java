package com.duoc.bff.libcore.mapper;
import com.duoc.bff.libcore.model.*; 
import com.duoc.bff.libcore.dto.web.*; 
import com.duoc.bff.libcore.dto.mobile.*;
public class ModelMappers {
  public static WebAccountDto toWeb(Account a){ return new WebAccountDto(a.getId(), a.getCustomerId(), a.getType(), a.getBalance()); }
  public static WebTransactionDto toWeb(Transaction t){ return new WebTransactionDto(t.getId(), t.getAccountId(), t.getDate(), t.getType(), t.getAmount(), t.getDescription()); }
  public static WebCustomerDto toWeb(Customer c){ return new WebCustomerDto(c.getId(), c.getName(), c.getAge()); }
  public static MobileAccountDto toMobile(Account a){ return new MobileAccountDto(a.getId(), a.getType(), a.getBalance()); }
  public static MobileTransactionDto toMobile(Transaction t){ return new MobileTransactionDto(t.getDate(), t.getType(), t.getAmount()); }
}
