/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegram.api.data.response;

import telegram.api.data.Message;

/**
 *
 * @author user
 */
public class MessageResponse extends Response {
    
    Message result;

    public Message getResult() {
        return result;
    }

    public void setResult(Message result) {
        this.result = result;
    }
    
}
