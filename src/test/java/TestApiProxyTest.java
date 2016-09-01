
import org.telegram.api.User;
import org.telegram.api.UserResponse;
import org.telegram.api.synchro.BotApi;
import org.telegram.api.synchro.BotApiFactory;

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
public class TestApiProxyTest {

    String token = TestBotConfig.getBotToken("lexredupbot");
    
    public void testApiProxy() {
        BotApi api = BotApiFactory.getInstance(token);
        UserResponse r = api.getMe();
        System.out.println(r.getResult().getId());
        System.out.println(r.getResult().getFirst_name());
        System.out.println(r.getResult().getLast_name());
        System.out.println(r.getResult().getUsername());
    }

}
