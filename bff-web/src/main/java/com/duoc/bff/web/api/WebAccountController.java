package com.duoc.bff.web.api;
import com.duoc.bff.libcore.repo.InMemoryDataRepository; import com.duoc.bff.libcore.mapper.ModelMappers; 
import com.duoc.bff.libcore.dto.web.*; import com.duoc.bff.libcore.model.*;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; 
import java.util.*; import java.util.stream.*;
@RestController @RequestMapping("/api/web")
public class WebAccountController {
  private final InMemoryDataRepository repo; public WebAccountController(InMemoryDataRepository r){ this.repo=r; }

  @GetMapping("/customers")
  public ResponseEntity<List<WebCustomerDto>> searchCustomers(@RequestParam(required=false) String name){
    var list = repo.searchCustomers(name).stream().map(ModelMappers::toWeb).toList();
    return ResponseEntity.ok().headers(h->h.set("X-Total-Count", String.valueOf(list.size()))).body(list);
  }

  @GetMapping("/accounts")
  public ResponseEntity<List<WebAccountDto>> accounts(
      @RequestParam Long customerId,
      @RequestParam(defaultValue="0") int page,
      @RequestParam(defaultValue="10") int size,
      @RequestParam(required=false, defaultValue="id,asc") String sort,
      @RequestParam(required=false) String type) {
    var list = repo.getAccountsByCustomer(customerId).stream()
      .filter(a -> type==null || a.getType().equalsIgnoreCase(type))
      .sorted(sort.startsWith("balance") ? Comparator.comparing(Account::getBalance) 
              : Comparator.comparing(Account::getId))
      .collect(Collectors.toList());
    if(sort.endsWith(",desc")) Collections.reverse(list);
    var pageList = InMemoryDataRepository.page(list, page, size).stream().map(ModelMappers::toWeb).toList();
    return ResponseEntity.ok().headers(h->{
      h.set("X-Total-Count", String.valueOf(list.size())); h.setCacheControl(CacheControl.noCache());
    }).body(pageList);
  }

  @GetMapping("/accounts/{id}/transactions")
  public ResponseEntity<List<WebTransactionDto>> tx(
      @PathVariable Long id,
      @RequestParam(defaultValue="0") int page,
      @RequestParam(defaultValue="10") int size,
      @RequestParam(required=false) String type,
      @RequestParam(required=false) String from,
      @RequestParam(required=false) String to,
      @RequestParam(required=false, defaultValue="date,desc") String sort) {
    var list = repo.getTransactions(id).stream()
      .filter(t -> type==null || t.getType().equalsIgnoreCase(type))
      .filter(t -> from==null || t.getDate().compareTo(from)>=0)
      .filter(t -> to==null || t.getDate().compareTo(to)<=0)
      .sorted(sort.startsWith("amount") ? Comparator.comparing(com.duoc.bff.libcore.model.Transaction::getAmount)
              : Comparator.comparing(com.duoc.bff.libcore.model.Transaction::getDate))
      .collect(Collectors.toList());
    if(sort.endsWith(",desc")) Collections.reverse(list);
    var pageList = InMemoryDataRepository.page(list, page, size).stream().map(ModelMappers::toWeb).toList();
    return ResponseEntity.ok().headers(h->h.set("X-Total-Count", String.valueOf(list.size()))).body(pageList);
  }
}
