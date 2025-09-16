package com.duoc.bff.atm.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.*; 
import org.springframework.security.core.*; import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.List;
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwt; public JwtAuthFilter(JwtService jwt){ this.jwt=jwt; }
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
      throws ServletException, IOException {
    String h = req.getHeader("Authorization");
    if(h!=null && h.startsWith("Bearer ")){
      try{
        var claims = jwt.parse(h.substring(7)).getBody();
        String role = (String)claims.get("role");
        Authentication auth = new UsernamePasswordAuthenticationToken(
          claims.get("terminalId"), null, List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }catch(Exception e){ res.sendError(401, "Token inválido"); return; }
    }
    chain.doFilter(req, res);
  }
}
