
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.api.Message;

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
public class TestJsonSerializationTest {
       
    public void testDeserialization() {
        
        String mess = "{\"date\":1472321014,\"chat\":{\"last_name\":\"Stechkin\",\"id\":241062328,\"type\":\"private\",\"first_name\":\"Alexey\",\"username\":\"astechkin\"},\"photo\":[{\"file_id\":\"AgADAgADCKgxG7hRXg6nXI1tiEykXdvcgQ0ABGqbfGid_ITt2RYAAgI\",\"width\":90,\"file_size\":2031,\"height\":90},{\"file_id\":\"AgADAgADCKgxG7hRXg6nXI1tiEykXdvcgQ0ABLs7dvsjWI-v2hYAAgI\",\"width\":320,\"file_size\":30173,\"height\":320},{\"file_id\":\"AgADAgADCKgxG7hRXg6nXI1tiEykXdvcgQ0ABAwlVHw9RWr02xYAAgI\",\"width\":800,\"file_size\":141836,\"height\":800},{\"file_id\":\"AgADAgADCKgxG7hRXg6nXI1tiEykXdvcgQ0ABKWSIJu-fhhA2BYAAgI\",\"width\":1280,\"file_size\":243224,\"height\":1280}],\"message_id\":306,\"from\":{\"last_name\":\"Stechkin\",\"id\":241062328,\"first_name\":\"Alexey\",\"username\":\"astechkin\"}}";
        
        
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Message message = mapper.readValue(mess, Message.class);
            System.out.println(message);
        } catch (IOException ex) {
            Logger.getLogger(TestJsonSerializationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
      
    public static void main(String[] args) {
     
    }
    
}
