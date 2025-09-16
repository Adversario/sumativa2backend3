package com.duoc.bff.web.api;
import com.duoc.bff.web.security.JwtService; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
import java.util.Map;
record LoginRequest(String username, String password) {}
@RestController @RequestMapping("/auth")
public class AuthController {
  private final JwtService jwt; public AuthController(JwtService jwt){ this.jwt=jwt; }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest r){
    if(r==null || r.password()==null || !r.password().equals("password")) return ResponseEntity.status(401).body(Map.of("error","Credenciales inválidas"));
    String token = jwt.generate(Map.of("username", r.username(), "channel", "WEB", "role", "ROLE_WEB_USER"));
    return ResponseEntity.ok(Map.of("token", token, "channel", "WEB", "role","ROLE_WEB_USER"));
  }
}
