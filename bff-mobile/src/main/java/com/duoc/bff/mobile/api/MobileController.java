package com.duoc.bff.mobile.api;
import com.duoc.bff.libcore.repo.InMemoryDataRepository; import com.duoc.bff.libcore.mapper.ModelMappers; 
import com.duoc.bff.libcore.dto.mobile.*; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
import java.util.*; 
@RestController @RequestMapping("/api/mobile") 
public class MobileController {
  private final InMemoryDataRepository repo; public MobileController(InMemoryDataRepository r){ this.repo=r; }

  @GetMapping("/accounts")
  public ResponseEntity<List<MobileAccountDto>> accounts(@RequestParam Long customerId){
    var list = repo.getAccountsByCustomer(customerId).stream().map(ModelMappers::toMobile).toList();

    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(java.time.Duration.ofMinutes(1)).cachePublic())
        .body(list);
  }

  @GetMapping("/accounts/{id}/transactions")
  public List<MobileTransactionDto> recent(@PathVariable Long id, @RequestParam(defaultValue="5") int limit){
    return repo.getTransactions(id).stream()
      .sorted((a,b)->b.getDate().compareTo(a.getDate()))
      .limit(limit).map(ModelMappers::toMobile).toList();
  }

  @GetMapping("/summary")
  public MobileSummaryDto summary(@RequestParam Long customerId){
    var accounts = repo.getAccountsByCustomer(customerId);
    double total = accounts.stream().mapToDouble(a->a.getBalance()).sum();
    return new MobileSummaryDto(customerId, accounts.size(), total);
  }
}
