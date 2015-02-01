package cz.cvut.jee.sockets;

import cz.cvut.jee.utils.dateTime.JEEDateTimeUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.joda.time.LocalDateTime;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * Helper class used to prepare message, which will be sent via websocket. 
 *
 * @author Tomas Cervenka
 * @since 1.2.2015
 */
public class MessageUtil {

    /**
     * Creates JSON object from message 
     * @param message Message we want to send
     * @return Created JSON object
     * @throws EncodeException
     */
    private String encode(Message message) throws EncodeException {
        return Json.createObjectBuilder()
                .add("type", String.valueOf(message.getType()))
                .add("username", message.getUsername())
                .add("text", message.getText())
                .add("time", message.getTime())
                .add("type", String.valueOf(message.getType()))
                .build().toString();
    }

    /**
     * Creates chat message with chat text, time and username 
     * @param messageInput Text of message
     * @param session Session from which is the message sent
     * @return JSON object in string format
     */
    public String getChatMessage(String messageInput, Session session){
        Message preparedMessage = new Message(StringEscapeUtils.escapeHtml4(messageInput), MessageType.MESSAGE);
        preparedMessage.setTime(new LocalDateTime().toString(JEEDateTimeUtils.timePattern));
        preparedMessage.setUsername(session.getUserPrincipal().getName());

        try {
            return encode(preparedMessage);
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * Creates chat message with chat text, time, username and custom MessageType
     * @param messageInput Text of message
     * @param messageType Type of message
     * @param session Session from which is the message sent
     * @return JSON object in string format
     */
    public String getGeneralMessage(String messageInput, MessageType messageType, Session session){
        Message preparedMessage = new Message(messageInput, messageType);
        preparedMessage.setTime(new LocalDateTime().toString(JEEDateTimeUtils.timePattern));
        preparedMessage.setUsername(session.getUserPrincipal().getName());

        try {
            return encode(preparedMessage);
        } catch (EncodeException e) {
            e.printStackTrace();
        }

        return null;
    }
}
