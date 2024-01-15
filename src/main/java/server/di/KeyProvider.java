package server.di;

import common.ConstantsServer;
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
    @Named(ConstantsServer.JWT)
    public Key key()
    {

        try {
            final MessageDigest digest =
                    MessageDigest.getInstance(ConstantsServer.SHA_512);
            digest.update(ConstantsServer.CLAVE.getBytes(StandardCharsets.UTF_8));
            final SecretKeySpec key2 = new SecretKeySpec(
                    digest.digest(), 0, 64, ConstantsServer.AES);

            return  Keys.hmacShaKeyFor(key2.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
