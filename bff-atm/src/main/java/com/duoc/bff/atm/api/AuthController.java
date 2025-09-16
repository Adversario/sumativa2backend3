package com.duoc.bff.atm.api;
import com.duoc.bff.atm.security.JwtService; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
import java.util.Map;
record LoginAtmRequest(String terminalId, String pin) {}
@RestController @RequestMapping("/auth")
public class AuthController {
  private final JwtService jwt; public AuthController(JwtService jwt){ this.jwt=jwt; }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginAtmRequest r){
    if(r==null || !"1234".equals(r.pin())) return ResponseEntity.status(401).body(Map.of("error","PIN inválido"));
    String token = jwt.generate(Map.of("terminalId", r.terminalId(), "channel", "ATM", "role", "ROLE_ATM_TERMINAL"));
    return ResponseEntity.ok(Map.of("token", token, "channel", "ATM", "role","ROLE_ATM_TERMINAL"));
  }
}
