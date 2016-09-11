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
package telegram.polling;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import telegram.api.BotApi;
import java.lang.reflect.Proxy;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author user
 */
public class BotApiFactory {

    private static final Logger LOG = Logger.getLogger(BotApiFactory.class.getName());

    public static BotApi getFromConfig(String botConfigName) {
        return getInstance(getBotToken(botConfigName));
    }

    public static BotApi getInstance(String botToken) {
        BotApiProxy proxy = new BotApiProxy(botToken);
        return (BotApi) Proxy.newProxyInstance(BotApi.class.getClassLoader(), new Class[]{BotApi.class}, proxy);
    }

    public static String getBotToken(String botName, String secureKey) {
        Properties bot_config = new Properties();
        try {
            bot_config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(botName + ".properties"));
            String token = bot_config.getProperty("token");
            if (secureKey != null) {
                ObjectInputStream ois = new ObjectInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(secureKey));
                SecretKey key = (SecretKey) ois.readObject();
                ois.close();
                Cipher dcipher = Cipher.getInstance("DES");
                dcipher.init(Cipher.DECRYPT_MODE, key);
                // Decode base64 to get bytes
                byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(token);
                // Decrypt
                byte[] utf8 = dcipher.doFinal(dec);
                // Decode using utf-8
                return new String(utf8, "UTF8");
            }
            return token;
        } catch (ClassNotFoundException | BadPaddingException | IllegalBlockSizeException | IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public static String getBotToken(String botName) {
        return getBotToken(botName, null);
    }

}
