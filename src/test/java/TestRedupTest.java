
import com.astech.lexredupbot.Demo;
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
public class TestRedupTest {

    public static void testProcessText() {
        try {
            Demo demo = new Demo();
            System.out.println(demo.processText("best boy in the world"));
        } catch (Exception ex) {
            Logger.getLogger(TestRedupTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String arg[]) {
        
        testProcessText();
    }

}
