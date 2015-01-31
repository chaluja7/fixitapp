package cz.cvut.jee.sockets;

import javax.json.Json;
import javax.websocket.EncodeException;

public class MessageEncoder {
    
    public String encode(Message message) throws EncodeException {
        return Json.createObjectBuilder()
                .add("username", message.getUsername())
                .add("text", message.getText())
                .build().toString();
    }
}
