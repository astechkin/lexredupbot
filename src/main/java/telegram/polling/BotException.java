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

/**
 *
 * @author user
 */
public class BotException extends Exception {

    static final long serialVersionUID = 88;
    
    private int code;

    public BotException(String error) {
        super(error);
    }

    public BotException(int code, String error) {
        super(error);
        this.code = code;
    }

    public BotException(String error, Throwable origin) {
        super(error, origin);
    }
    
    public int getCode() {
        return code;
    }

}
