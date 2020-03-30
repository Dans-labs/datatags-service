package nl.knaw.dans.datatags.service.utils;

import org.apache.xerces.impl.dv.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

public class StringUtil {
    private static final Logger logger = Logger.getLogger(StringUtil.class.getCanonicalName());
    public static final Set<String> TRUE_VALUES = Collections.unmodifiableSet(new TreeSet<>( Arrays.asList("1","yes", "true","allow")));

    public static final boolean nonEmpty( String str ) {
        return ! isEmpty(str);
    }

    public static final boolean isEmpty(String str) {
        return str==null || str.trim().equals("");
    }

    public static  String nullToEmpty(String inString) {
        return inString == null ? "" : inString;
    }

    public static final boolean isAlphaNumeric(String str) {
        final char[] chars = str.toCharArray();
        for (int x = 0; x < chars.length; x++) {
            final char c = chars[x];
            if(! isAlphaNumericChar(c)) {
                return false;
            }
        }
        return true;
    }

    public static String substringIncludingLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return "";
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == (str.length() - separator.length())) {
            return "";
        }
        return str.substring(pos);
    }

    public static Optional<String> toOption(String s) {
        if ( isEmpty(s) ) {
            return Optional.empty();
        } else {
            return Optional.of(s.trim());
        }
    }


    /**
     * Checks if {@code s} contains a "truthy" value.
     * @param s
     * @return {@code true} iff {@code s} is not {@code null} and is "truthy" word.
     * @see #TRUE_VALUES
     */
    public static boolean isTrue( String s ) {
        return (s != null ) && TRUE_VALUES.contains(s.trim().toLowerCase());
    }

    public static final boolean isAlphaNumericChar(char c) {
        // TODO: consider using Character.isLetterOrDigit(c)
        return ( (c >= 'a') && (c <= 'z') ||
                (c >= 'A') && (c <= 'Z') ||
                (c >= '0') && (c <= '9') );
    }



    /**
     * Generates an AES-encrypted version of the string. Resultant string is URL safe.
     * @param value The value to encrypt.
     * @param password The password.
     * @return encrypted string, URL-safe.
     */
    public static String encrypt(String value, String password ) {
        byte[] baseBytes = value.getBytes();
        try {
            Cipher aes = Cipher.getInstance("AES");
            final SecretKeySpec secretKeySpec = generateKeyFromString(password);
            aes.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = aes.doFinal(baseBytes);
            String base64ed = Base64.encode(encrypted);
            return base64ed.replaceAll("\\+", ".")
                    .replaceAll("=", "-")
                    .replaceAll("/", "_");

        } catch (  InvalidKeyException | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | NoSuchPaddingException | UnsupportedEncodingException ex) {
            logger.severe(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public static String decrypt(String value, String password ) {
        String base64 = value.replaceAll("\\.", "+")
                .replaceAll("-", "=")
                .replaceAll("_", "/");

        byte[] baseBytes = Base64.decode(base64);
        try {
            Cipher aes = Cipher.getInstance("AES");
            aes.init( Cipher.DECRYPT_MODE, generateKeyFromString(password));
            byte[] decrypted = aes.doFinal(baseBytes);
            return new String(decrypted);

        } catch ( InvalidKeyException | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | NoSuchPaddingException | UnsupportedEncodingException ex) {
            logger.severe(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }



    private static SecretKeySpec generateKeyFromString(final String secKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] key = (secKey).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only first 128 bits

        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}
