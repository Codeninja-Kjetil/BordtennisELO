package no.uib.inf319.bordtennis.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility class used to hash strings with the SHA-256 algorithm.
 *
 * @author Kjetil
 */
public final class Sha256HashUtil {

    /**
     * A private constructor.
     */
    private Sha256HashUtil() {
    }

    /**
     * Hashes a string based on the SHA-256 algorithm.
     *
     * @param string the string to be hashed.
     * @return a string containing the hashed result.
     */
    public static String sha256hash(final String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(string.getBytes("UTF-8"));
            byte[] ba = md.digest();
            return toHex(ba);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * Creates a string from a byte-array of hex-values.
     *
     * @param bytes the byte-array.
     * @return the string representation of the bytes with radix 16
     *         (hex-values).
     */
    public static String toHex(final byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }

}
