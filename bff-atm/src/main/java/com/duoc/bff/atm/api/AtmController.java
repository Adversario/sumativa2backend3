package com.duoc.bff.atm.api;

import com.duoc.bff.libcore.repo.InMemoryDataRepository;
import com.duoc.bff.libcore.dto.atm.AtmBalanceResponse;
import com.duoc.bff.libcore.dto.atm.AtmWithdrawalRequest;
import com.duoc.bff.libcore.dto.atm.AtmWithdrawalResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atm")
public class AtmController {
  private final InMemoryDataRepository repo;

  public AtmController(InMemoryDataRepository repo) {
    this.repo = repo;
  }

  @GetMapping("/accounts/{id}/balance")
  public ResponseEntity<AtmBalanceResponse> balance(@PathVariable Long id) {
    var a = repo.getAccount(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    return ResponseEntity.ok(new AtmBalanceResponse(a.getId(), a.getBalance()));
  }

  @PostMapping("/accounts/{id}/withdrawals")
  public ResponseEntity<AtmWithdrawalResponse> withdraw(
      @PathVariable Long id,
      @RequestHeader("Idempotency-Key") String idemKey,
      @RequestBody AtmWithdrawalRequest req) {
    try {
      String wid = repo.withdraw(id, req.amount, idemKey);
      if ("DUPLICATE".equals(wid)) {
        var a = repo.getAccount(id).orElseThrow();
        return ResponseEntity.ok(
            new AtmWithdrawalResponse("OK", "Solicitud repetida (idempotente)", a.getBalance(), null));
      }
      var a = repo.getAccount(id).orElseThrow();
      return ResponseEntity.ok(new AtmWithdrawalResponse("OK", "Retiro realizado", a.getBalance(), wid));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(new AtmWithdrawalResponse("ERROR", e.getMessage(), null, null));
    } catch (IllegalStateException e) {
      return ResponseEntity.status(409).body(new AtmWithdrawalResponse("ERROR", e.getMessage(), null, null));
    } catch (Exception e) {
      return ResponseEntity.status(404).body(new AtmWithdrawalResponse("ERROR", e.getMessage(), null, null));
    }
  }

@GetMapping("/accounts/{id}/transactions")
public List<Map<String, Object>> last(@PathVariable Long id,
                                      @RequestParam(defaultValue = "5") int limit) {
  return repo.getTransactions(id).stream()
      .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
      .limit(limit)
      .map(t -> {
        Map<String, Object> m = new java.util.LinkedHashMap<>();
        m.put("date", t.getDate());
        m.put("type", t.getType());
        m.put("amount", t.getAmount());
        return m;
      })
      .collect(java.util.stream.Collectors.toList());
}
}