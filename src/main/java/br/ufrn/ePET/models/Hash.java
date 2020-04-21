package br.ufrn.ePET.models;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private final MessageDigest md;

    public Hash() throws NoSuchAlgorithmException {
        this.md = MessageDigest.getInstance("SHA-1");
    }

    public String novaHash(String password){
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%032x", new BigInteger(1, digest));
        return hex;
    }
}
