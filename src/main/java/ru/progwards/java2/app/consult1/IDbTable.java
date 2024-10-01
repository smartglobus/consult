package ru.progwards.java2.app.consult1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Predicate;

public interface IDbTable<K,V> {
    String getTableName();

    List<V> getAll();
    List<V> select(Predicate<V> check);
    K getKey(V elem);
    boolean put(V elem) throws Exception;
    V remove(K key) throws Exception;
    V findKey(K key);
    boolean exists(K key);

    void readAll() throws Exception;
    void saveAll() throws Exception;

    static String hashSha256(String stringToEncrypt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(stringToEncrypt.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String bytesToHex(byte[] hash) {
        if (hash == null)
            return null;
        StringBuffer hexString = new StringBuffer();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
