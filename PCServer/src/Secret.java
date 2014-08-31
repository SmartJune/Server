import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Secret {

	public static String enCrypto(String txt, String key)  
            throws InvalidKeySpecException, InvalidKeyException,  
            NoSuchPaddingException, IllegalBlockSizeException,  
            BadPaddingException {  
        StringBuffer sb = new StringBuffer();  
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
        cipher.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] cipherText = cipher.doFinal(txt.getBytes());  
        for (int n = 0; n < cipherText.length; n++) {  
            String stmp = (java.lang.Integer.toHexString(cipherText[n] & 0XFF));  
  
            if (stmp.length() == 1) {  
                sb.append("0" + stmp);  
            } else {  
                sb.append(stmp);  
            }  
        }  
        return sb.toString().toUpperCase();  
    }  
	
	public static int password(int seed){
		return (seed*seed+10000000+seed+seed+seed);
	}
}
