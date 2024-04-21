package org.example.serverinfoproyectofinalfranciscodasilva.spring.config;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.example.serverinfoproyectofinalfranciscodasilva.common.Constantes;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.PublicKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {


    @Value("${application.claveKeyStore}")
    private String claveKeyStore;

    @Value("${application.nombreKeyStore}")
    private String nombreKeyStore;

    @Value("${application.nombreServer}")
    private String nombreServer;


    //Hago throw porque es necesario para poder enviar la excepcion de token caducado en el filter
    public boolean validate(String token) throws ExpiredJwtException, SignatureException {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getPublicKey())
                    .build()
                    .parseClaimsJws(token);

            long expirationMillis = claimsJws.getBody().getExpiration().getTime();
            return System.currentTimeMillis() < expirationMillis;
    }

    public String getUsername(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    public String getRole(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().get(Constantes.ROLES, String.class);
    }


    private PublicKey getPublicKey() {
        try {
            KeyStore ks = KeyStore.getInstance(Constantes.PKCS_12);
            try (FileInputStream fis = new FileInputStream(nombreKeyStore)) {
                ks.load(fis, claveKeyStore.toCharArray());
            }
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(claveKeyStore.toCharArray());
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(nombreServer, pt);
            if (pkEntry != null) {
                return pkEntry.getCertificate().getPublicKey();
            } else {
                throw new PublicKeyException(Constantes.NO_SE_ENCONTRO_LA_ENTRADA_DE_CLAVE_PRIVADA_EN_EL_KEYSTORE);
            }
        } catch (UnrecoverableEntryException | CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException e) {
            throw new PublicKeyException(Constantes.ERROR_INESPERADO_AL_VALIDAR_TOKEN);
        }
    }
}