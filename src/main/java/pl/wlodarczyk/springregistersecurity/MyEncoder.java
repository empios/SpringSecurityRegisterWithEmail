package pl.wlodarczyk.springregistersecurity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class MyEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        String md5 = null;
        if(null == charSequence) return null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(charSequence.toString().getBytes(), 0, charSequence.length());
            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(md5);
        return md5;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String md5 = null;
        if(null == charSequence)return false;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(charSequence.toString().getBytes(), 0, charSequence.length());
            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
            return md5.equals(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
