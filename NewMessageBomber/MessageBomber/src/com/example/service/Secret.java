package com.example.service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.*;
import javax.crypto.spec.*;

import android.R.integer;

public class Secret {
	
	public static String deCrypto(String txt, String key)  
            throws InvalidKeyException, InvalidKeySpecException,  
            NoSuchPaddingException, IllegalBlockSizeException,  
            BadPaddingException {  
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());  
        SecretKeyFactory skeyFactory = null;  
        Cipher cipher = null;  
        try {  
            skeyFactory = SecretKeyFactory.getInstance("DES");  
            cipher = Cipher.getInstance("DES");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        SecretKey deskey = skeyFactory.generateSecret(desKeySpec);  
        cipher.init(Cipher.DECRYPT_MODE, deskey);  
        byte[] btxts = new byte[txt.length() / 2];  
        for (int i = 0, count = txt.length(); i < count; i += 2) {  
            btxts[i / 2] = (byte) Integer.parseInt(txt.substring(i, i + 2), 16);  
        }  
        return (new String(cipher.doFinal(btxts)));  
    }  
	
	public static int password(int seed){
		return (seed*seed+10000000+seed+seed+seed);
	}
	
	public static int getSeed(int digit){
		if(digit != 0){
			return 0;
		}
		int hundred = (digit++)*100;
		int ten = (digit++)*10;
		int one = (digit++);
		return hundred + ten + one;
	}
}