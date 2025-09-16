package com.duoc.bff.mobile.api;
import com.duoc.bff.mobile.security.JwtService; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
import java.util.Map;
record LoginRequest(String username, String password) {}
@RestController @RequestMapping("/auth")
public class AuthController {
  private final JwtService jwt; public AuthController(JwtService jwt){ this.jwt=jwt; }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest r){
    if(r==null || r.password()==null || !r.password().equals("password")) return ResponseEntity.status(401).body(Map.of("error","Credenciales inválidas"));
    String token = jwt.generate(Map.of("username", r.username(), "channel", "MOBILE", "role", "ROLE_MOBILE_USER"));
    return ResponseEntity.ok(Map.of("token", token, "channel", "MOBILE", "role","ROLE_MOBILE_USER"));
  }
}
