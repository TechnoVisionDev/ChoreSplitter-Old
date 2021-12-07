package data;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Encrypts user passwords using a hash function and salt value.
 * Ensures safe storage of passwords in MongoDB database.
 * @author Aaron Ahmen
 */
public class PasswordEncrypter {

	private static final Random rand = new SecureRandom();
	
	/**
	 * Generates random salt value.
	 * @return salt randomly generated salt value.
	 */
	protected String getSalt() {  
    	byte[] salt = new byte[16];
    	rand.nextBytes(salt);
        return new String(salt);  
    }     
  
	/**
	 * Generates hashed value.
	 * @param password Inputed password
	 * @param salt salt value
	 * @return hash value.
	 */
    private byte[] hash(String password, String salt){  
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 10000, 256);  
        try{  
        	SecretKeyFactory fact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");  
            return fact.generateSecret(spec).getEncoded();  
        }   
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing PBKDF2WithHmacSHA1 algorithm", ex);
          }
        catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
         }    
    }  
    
	/**
	 * Generates encrypted password string.
	 * @param password inputed password
	 * @param salt salt value
	 * @return encrypted password string.
	 */
    protected String encryptPassword(String password, String salt){            
    	byte[] hashedPassword = hash(password, salt);   
        return Base64.getEncoder().encodeToString(hashedPassword);  
    }  
      
	/**
	 * Verifies if inputed password matches encrypted password.
	 * @param password password inputed by user
	 * @param password encrypted password
	 * @param salt salt value
	 * @return true if inputed password matches encrypted password.
	 */  
    protected boolean verifyPassword(String userPass, String encryptedPass, String salt){          
    	String userPassEncrypted = encryptPassword(userPass, salt);  
    	return userPassEncrypted.equalsIgnoreCase(encryptedPass);  
    } 
}
