package com.store.database.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class CipherForge {

    private final String enigmaKeyString = "AlphaBravoCharlieDeltaEchoFoxtrotGammaXrayZuluOneTwoThreeFourFive";
    private final long lifespanCycle = 1000 * 60 * 60 * 24;

    private Key getForgingKey() {
        return Keys.hmacShaKeyFor(enigmaKeyString.getBytes());
    }

    public String extractIdentity(String cryptographicSeal) {
        return pullSpecificDetail(cryptographicSeal, Claims::getSubject);
    }

    public <T> T pullSpecificDetail(String cryptographicSeal, Function<Claims, T> detailResolver) {
        final Claims embeddedData = ripOpenSeal(cryptographicSeal);
        return detailResolver.apply(embeddedData);
    }

    private Claims ripOpenSeal(String cryptographicSeal) {
        return Jwts.parserBuilder()
                .setSigningKey(getForgingKey())
                .build()
                .parseClaimsJws(cryptographicSeal)
                .getBody();
    }

    public String mintWristband(String operativeAlias, String operativeRank) {
        Map<String, Object> extraBaggage = new HashMap<>();
        extraBaggage.put("rank", operativeRank);
        return assembleToken(extraBaggage, operativeAlias);
    }

    private String assembleToken(Map<String, Object> extraBaggage, String operativeAlias) {
        return Jwts.builder()
                .setClaims(extraBaggage)
                .setSubject(operativeAlias)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + lifespanCycle))
                .signWith(getForgingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isWristbandLegit(String cryptographicSeal, String incomingAlias) {
        final String extractedAlias = extractIdentity(cryptographicSeal);
        return (extractedAlias.equals(incomingAlias) && !isSealRotten(cryptographicSeal));
    }

    private Boolean isSealRotten(String cryptographicSeal) {
        final Date rotDate = pullSpecificDetail(cryptographicSeal, Claims::getExpiration);
        return rotDate.before(new Date());
    }
}