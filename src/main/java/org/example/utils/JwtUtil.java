package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for handling JSON Web Tokens (JWT)
 * using asymmetric RSA encryption (RS256).
 */
@Component
@PropertySource("classpath:application.properties")
public class JwtUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(JwtUtil.class);

    /** RSA Private Key used for signing tokens. */
    private PrivateKey privateKey;

    /** RSA Public Key used for verifying token signatures. */
    private PublicKey publicKey;

    /** Token expiration duration in seconds. */
    private long jwtExpiration;

    /** Spring Environment used to read application properties. */
    @Autowired
    private Environment environment;

    /**
     * Default constructor.
     */
    public JwtUtil() {
        this.privateKey = null;
        this.publicKey = null;
        this.jwtExpiration = 0;
    }

    /**
     * Initializes the JWT configuration.
     */
    @Autowired
    public void init() {

        String privateKeyString =
                environment.getProperty("jwt.private-key");

        String publicKeyString =
                environment.getProperty("jwt.public-key");

        String expiration =
                environment.getProperty("jwt.expiration");

        if (privateKeyString == null
                || publicKeyString == null
                || expiration == null) {

            throw new IllegalStateException(
                    "JWT configuration missing in application.properties"
            );
        }

        this.privateKey =
                loadPrivateKey(cleanKeyString(privateKeyString));

        this.publicKey =
                loadPublicKey(cleanKeyString(publicKeyString));

        this.jwtExpiration =
                Long.parseLong(expiration);
    }

    /**
     * Removes PEM headers, footers and whitespace
     * from a key string.
     */
    private String cleanKeyString(String keyString) {

        if (keyString == null) {
            throw new IllegalArgumentException(
                    "Key string cannot be null"
            );
        }

        return keyString
                .replaceAll(
                        "-----(BEGIN|END) (PRIVATE|PUBLIC) KEY-----",
                        ""
                )
                .replaceAll("\\s", "");
    }

    /**
     * Converts a Base64 encoded PKCS#8 private key
     * into a Java PrivateKey.
     */
    private PrivateKey loadPrivateKey(String keyString) {

        try {
            byte[] keyBytes =
                    Base64.getDecoder().decode(keyString.trim());

            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(spec);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load private key",
                    e
            );
        }
    }

    /**
     * Converts a Base64 encoded X.509 public key
     * into a Java PublicKey.
     */
    private PublicKey loadPublicKey(String keyString) {

        try {
            byte[] keyBytes =
                    Base64.getDecoder().decode(keyString);

            X509EncodedKeySpec spec =
                    new X509EncodedKeySpec(keyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(spec);

        } catch (Exception e) {

            logger.error("Failed to load public key", e);

            throw new RuntimeException(
                    "Failed to load public key",
                    e
            );
        }
    }

    /**
     * Generates a signed JWT token.
     */
    public String generateToken(
            String username,
            List<String> roles) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put("username", username);
        claims.put("roles", roles);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(
                        Date.from(
                                Instant.now()
                                        .plusSeconds(jwtExpiration)
                        )
                )
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    /**
     * Validates a JWT token.
     */
    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            logger.warn(
                    "Invalid JWT token: {}",
                    e.getMessage()
            );

            return false;
        }
    }

    /**
     * Extracts the username from the token.
     */
    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    /**
     * Extracts the roles from the token.
     */
    public List<String> extractRoles(String token) {

        return extractClaim(
                token,
                claims -> claims.get("roles", List.class)
        );
    }

    /**
     * Extracts the expiration date from the token.
     */
    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    /**
     * Generic method for extracting a claim.
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the token
     * after verifying its signature.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Checks whether the token has expired.
     */
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    /**
     * Returns the configured RSA public key.
     */
    public PublicKey getPublicKey() {

        return publicKey;
    }

    /**
     * Returns the public key in PEM format.
     */
    public String getPublicKeyAsPEM() {

        try {

            String encoded =
                    Base64.getEncoder()
                            .encodeToString(
                                    publicKey.getEncoded()
                            );

            return "-----BEGIN PUBLIC KEY-----\n"
                    + splitKeyIntoLines(encoded)
                    + "\n-----END PUBLIC KEY-----";

        } catch (Exception e) {

            logger.error(
                    "Failed to export public key",
                    e
            );

            throw new RuntimeException(
                    "Failed to export public key",
                    e
            );
        }
    }

    /**
     * Returns the public key as a single Base64 string.
     */
    public String getPublicKeyAsSingleLine() {

        try {

            return Base64.getEncoder()
                    .encodeToString(
                            publicKey.getEncoded()
                    );

        } catch (Exception e) {

            logger.error(
                    "Failed to export public key",
                    e
            );

            throw new RuntimeException(
                    "Failed to export public key",
                    e
            );
        }
    }

    /**
     * Splits a Base64 string into lines of 64 characters.
     */
    private String splitKeyIntoLines(String key) {

        return key
                .replaceAll("(.{64})", "$1\n")
                .trim();
    }
}