   package com.example.team11.filter;

   import jakarta.servlet.*;
   import jakarta.servlet.http.HttpServletRequest;
   import java.io.IOException;

   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;

   public class RequestLoggingFilter implements Filter {

       private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

       @Override
       public void init(FilterConfig filterConfig) throws ServletException {
           // Filter initialization logic can go here
       }

       @Override
       public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
               throws IOException, ServletException {
           HttpServletRequest req = (HttpServletRequest) request;
           logger.info("Incoming request {} : {}", req.getMethod(), req.getRequestURI());
           chain.doFilter(request, response);
           logger.info("Outgoing response for {} : {}", req.getMethod(), req.getRequestURI());
       }

       @Override
       public void destroy() {
           // Cleanup code if necessary
       }
   }