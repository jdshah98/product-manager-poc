//package com.jdshah.gateway.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//public class LoginProcessingFilter extends WebFilter {
//
//    @Autowired
//    private ReactiveAuthenticationManager authenticationManager;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        Mono<String> usernameMono = extractUsername(request);
//        Mono<String> passwordMono = extractPassword(request);
//
//        return Mono.zip(usernameMono, passwordMono)
//                .map(tuple -> new UsernamePasswordAuthenticationToken(tuple.getT1(), tuple.getT2()))
//                .flatMap(authentication -> authenticationManager.authenticate(authentication))
//                .flatMap(authentication -> {
//                    SecurityContext context = new SecurityContextImpl(authentication);
//                    ReactiveSecurityContextHolder
//                            .withSecurityContext(Mono.just(context));
//                    exchange.getSession().flatMap(webSession -> webSession.getAttributes().put(SecurityContext))
//                    return chain.filter(exchange);
//                });
//    }
//
//    private Mono<String> extractUsername(ServerHttpRequest request) {
//        return Mono.just("user");
//    }
//
//    private Mono<String> extractPassword(ServerHttpRequest request) {
//        return Mono.just("password");
//    }
//}
