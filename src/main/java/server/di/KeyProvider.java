package server.di;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeyProvider {

    @Produces
    @Singleton
    @Named("JWT")
    public Key key()
    {

        try {
            final MessageDigest digest =
                    MessageDigest.getInstance("SHA-512");
            digest.update("clave".getBytes(StandardCharsets.UTF_8));
            final SecretKeySpec key2 = new SecretKeySpec(
                    digest.digest(), 0, 64, "AES");
            SecretKey keyConfig = Keys.hmacShaKeyFor(key2.getEncoded());
            return keyConfig;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
