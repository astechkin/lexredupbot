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
package org.telegram.api;

import java.util.List;

/**
 *
 * @author user
 */
public class UpdateResponse extends Response {
    List<Update> result; //to receive incoming updates using long polling (wiki). An Array of Update objects is returned.

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }
    
    
}
