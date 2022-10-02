package org.cch.utils;

import io.smallrye.jwt.algorithm.SignatureAlgorithm;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.cch.definition.ERole;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.jboss.logging.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class Token {

    private static final Logger LOG = Logger.getLogger(Token.class);

    @ConfigProperty(name = "security.jwt.token.expire-length", defaultValue = "3600000")
    private static long validityInMilliseconds;

    private static final String ISSUER = "https://localhost.com/issuer";

    public static String generateToken(String username, String account, String email, String userId, Set<ERole> roles)
            throws Exception {
        PrivateKey pk = readPrivateKey("D:\\quarkus\\jwt\\src\\main\\resources\\token\\privateKey.pem");
        LOG.info("Get Private Key");
        return generateToken(pk, username, account, email, userId, roles);
    }

    public static String generateToken(PrivateKey privateKey, String username, String account, String email, String userId,Set<ERole> roles) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        var groups = roles.stream().map(Enum::name).collect(Collectors.toSet());
        var currentTime = currentTime();
        return claimsBuilder.issuer(ISSUER)
                .subject(username)
                .issuedAt(currentTime)
                .claim("account", account)
                .claim(Claims.email.name(), email)
                .claim("id", userId)
                .expiresAt(currentTime + validityInMilliseconds)
                .groups(groups)
                .jws()
                .algorithm(SignatureAlgorithm.RS256)
                .sign(privateKey);
    }

    public static long currentTime() {
        return new Date().getTime();
    }

    /**
     *
     * @param resName resource name path
     * @return the resource content as a string
     * @throws IOException on failure
     */
    public static String readResource(String resName) throws IOException {
        StringWriter sw = new StringWriter();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(resName))) {
            String line = reader.readLine();
            while (line != null) {
                sw.write(line);
                sw.write('\n');
                line = reader.readLine();
            }
        }
        return sw.toString();
    }

    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        return decodePrivateKey(readResource(pemResName));
    }

    public static PublicKey readPublicKey(final String pemResName) throws Exception {
        return decodePublicKey(readResource(pemResName));
    }

    public static KeyPair generateKeyPair(final int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }


    /**
     * Decode a PEM encoded private key string to an RSA PrivateKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    /**
     * Decode a PEM encoded public key string to an RSA PublicKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PublicKey
     * @throws Exception on decode failure
     */
    public static PublicKey decodePublicKey(String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }
}
