package com.vinhnguyen.netChargeNZ.util;

import com.vinhnguyen.netChargeNZ.constants.SecurityConstants;
import com.vinhnguyen.netChargeNZ.model.User;
import com.vinhnguyen.netChargeNZ.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@AllArgsConstructor
@Component
public class JWTTokenUtil {

    private static SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

    public String generate(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        String jwt = Jwts.builder().setIssuer("netChargeNZ").setSubject("JWT TOKEN")
                .claim("username", user.getUsername())
                .claim("authorities", populateAuthorities(authorities))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey).compact();

        return jwt;
    }

    public Claims getPayload(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority: collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
