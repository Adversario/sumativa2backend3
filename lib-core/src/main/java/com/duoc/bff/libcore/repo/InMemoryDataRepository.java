package com.duoc.bff.libcore.repo;
import java.util.*; import java.util.concurrent.*; import java.util.stream.*; 
import com.duoc.bff.libcore.model.*;
public class InMemoryDataRepository {
  private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
  private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
  private final Map<Long, List<Transaction>> txByAccount = new ConcurrentHashMap<>();
  private final Set<String> processedIdempotencyKeys = ConcurrentHashMap.newKeySet();

  public void load(List<Customer> cs, List<Account> as, List<Transaction> ts){
    cs.forEach(c -> customers.put(c.getId(), c));
    as.forEach(a -> accounts.put(a.getId(), a));
    ts.forEach(t -> txByAccount.computeIfAbsent(t.getAccountId(), k -> new CopyOnWriteArrayList<>()).add(t));
  }
  public Optional<Customer> getCustomer(Long id){ return Optional.ofNullable(customers.get(id)); }
  public List<Customer> searchCustomers(String nameLike){
    return customers.values().stream()
      .filter(c -> nameLike==null || c.getName().toLowerCase().contains(nameLike.toLowerCase()))
      .sorted(Comparator.comparing(Customer::getName)).toList();
  }
  public List<Account> getAccountsByCustomer(Long customerId){
    return accounts.values().stream().filter(a -> a.getCustomerId().equals(customerId)).toList();
  }
  public Optional<Account> getAccount(Long id){ return Optional.ofNullable(accounts.get(id)); }
  public List<Transaction> getTransactions(Long accountId){ return txByAccount.getOrDefault(accountId, List.of()); }
  public static <T> List<T> page(List<T> list, int page, int size){
    int from = Math.max(0, page*size), to = Math.min(list.size(), from+size);
    if(from>=to) return List.of(); return list.subList(from, to);
  }
  public synchronized String withdraw(Long accountId, double amount, String idemKey){
    if(idemKey==null || idemKey.isBlank()) throw new IllegalArgumentException("Idempotency-Key requerida");
    if(processedIdempotencyKeys.contains(idemKey)) return "DUPLICATE";
    Account a = accounts.get(accountId);
    if(a==null) throw new NoSuchElementException("Cuenta no encontrada");
    if(amount<=0) throw new IllegalArgumentException("Monto inválido");
    if(a.getBalance() < amount) throw new IllegalStateException("Fondos insuficientes");
    a.setBalance(a.getBalance()-amount);
    long newId = System.currentTimeMillis();
    txByAccount.computeIfAbsent(accountId, k -> new CopyOnWriteArrayList<>())
      .add(new Transaction(newId, accountId, java.time.LocalDate.now().toString(), "retiro", -amount, "Retiro ATM"));
    processedIdempotencyKeys.add(idemKey);
    return String.valueOf(newId);
  }
}
