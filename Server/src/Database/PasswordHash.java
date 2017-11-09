package Database;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordHash {
    private String passwordHash;
    private final byte[] SALT = DatatypeConverter.parseHexBinary("99C784F29F1DCCC045394EE3269EB600310B80F62B0A4A0F");
    private final int ITERATIONS = 1000;
    private final int KEY_LENGTH = 24;

    public PasswordHash(String password) {
            KeySpec key = new PBEKeySpec(password.toCharArray(),SALT,ITERATIONS,KEY_LENGTH*8);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            passwordHash = DatatypeConverter.printHexBinary(keyFactory.generateSecret(key).getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {return passwordHash;}
}
