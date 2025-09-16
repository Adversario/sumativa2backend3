package com.duoc.bff.mobile.filter;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.stereotype.Component;
import java.io.IOException; import java.time.*; import java.util.concurrent.*;
@Component
public class RateLimitFilter implements Filter {
  private final ConcurrentHashMap<String, Counter> map = new ConcurrentHashMap<>();
  private static final int LIMIT = 60; // 60 req/min por IP
  @Override public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) req; HttpServletResponse s = (HttpServletResponse) res;
    String ip = r.getRemoteAddr();
    Counter c = map.computeIfAbsent(ip, k -> new Counter());
    synchronized (c) {
      if(Duration.between(c.windowStart, Instant.now()).toSeconds() >= 60){ c.windowStart = Instant.now(); c.count = 0; }
      if(++c.count > LIMIT){ s.setStatus(429); s.getWriter().write("Rate limit exceeded"); return; }
    }
    chain.doFilter(req,res);
  }
  static class Counter { int count=0; Instant windowStart = Instant.now(); }
}
