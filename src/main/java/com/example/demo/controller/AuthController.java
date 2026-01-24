package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthController {

    @Value("${azure.b2c.tenant}")
    private String tenant;

    @Value("${azure.b2c.client-id}")
    private String clientId;

    @Value("${azure.b2c.policy}")
    private String policy;

    /**
     * Endpoint público que retorna la configuración para login
     */
    @GetMapping("/public/auth/config")
    public ResponseEntity<Map<String, String>> getAuthConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("authority", String.format("https://%s.b2clogin.com/%s.onmicrosoft.com/%s", 
            tenant, tenant, policy));
        config.put("clientId", clientId);
        config.put("redirectUri", "http://localhost:3000/callback");
        
        return ResponseEntity.ok(config);
    }

    /**
     * Endpoint protegido que retorna información del usuario autenticado
     */
    @GetMapping("/auth/user")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        log.info("Usuario autenticado: {}", jwt.getSubject());
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", jwt.getSubject());
        userInfo.put("name", jwt.getClaim("name"));
        userInfo.put("email", jwt.getClaim("emails"));
        userInfo.put("claims", jwt.getClaims());
        
        return ResponseEntity.ok(userInfo);
    }

    /**
     * Health check público
     */
    @GetMapping("/public/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}