
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/*
 * Copyright (C) 2016 user
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 *
 * @author user
 */
public class encriptionTest {

    public void test1() {

        try {
            // Generate a temporary key. In practice, you would save this key.
            // See also Encrypting with DES Using a Pass Phrase.
            SecretKey key = KeyGenerator.getInstance("DES").generateKey();

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("key.bin"));
            oos.writeObject(key);
            oos.close();
            
            // Create encrypter/decrypter class
            DesEncrypter encrypter = new DesEncrypter(key);

            // Encrypt
            String encrypted = encrypter.encrypt("267352059:AAHWCnEJc5G4CfdmHt5T6drYDngfmzJFRq8");
            System.out.println(encrypted);
                       


            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("key.bin"));
            SecretKey key2 = (SecretKey)ois.readObject();
            ois.close();

            // Create encrypter/decrypter class
            DesEncrypter encrypter2 = new DesEncrypter(key2);
            
            // Decrypt
            String decrypted = encrypter2.decrypt(encrypted);
            System.out.println(decrypted);



        } catch (Exception e) {
        }

    }

    class DesEncrypter {

        SecretKey key;

        public DesEncrypter(SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException {
            this.key = key;
        }

        public String encrypt(String str) {
            try {
                Cipher ecipher = Cipher.getInstance("DES");
                ecipher.init(Cipher.ENCRYPT_MODE, key);
                // Encode the string into bytes using utf-8
                byte[] utf8 = str.getBytes("UTF8");
                // Encrypt
                byte[] enc = ecipher.doFinal(utf8);

                // Encode bytes to base64 to get a string
                return new sun.misc.BASE64Encoder().encode(enc);
            } catch (BadPaddingException e) {
            } catch (IllegalBlockSizeException e) {
            } catch (UnsupportedEncodingException e) {
            } catch (java.io.IOException e) {
            } catch (InvalidKeyException ex) {
                Logger.getLogger(encriptionTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(encriptionTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(encriptionTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        public String decrypt(String str) {
            try {
                Cipher dcipher = Cipher.getInstance("DES");
                dcipher.init(Cipher.DECRYPT_MODE, key);
                // Decode base64 to get bytes
                byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

                // Decrypt
                byte[] utf8 = dcipher.doFinal(dec);

                // Decode using utf-8
                return new String(utf8, "UTF8");
            } catch (BadPaddingException e) {
            } catch (IllegalBlockSizeException e) {
            } catch (UnsupportedEncodingException e) {
            } catch (java.io.IOException e) {
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(encriptionTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(encriptionTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(encriptionTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

}
