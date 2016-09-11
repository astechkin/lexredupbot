
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class TestBotConfig {

    public static String getBotToken(String botName) {
        Properties bot_config = new Properties();
        try {
            bot_config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(botName + ".properties"));
            return bot_config.getProperty("token");
        } catch (Exception ex) {
            Logger.getLogger(TestBotConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
