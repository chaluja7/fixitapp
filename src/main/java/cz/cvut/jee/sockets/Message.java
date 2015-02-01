package cz.cvut.jee.sockets;

/**
 * Class representing message sent via chat (message is parsed with JSON).
 *
 * @author Tomas Cervenka
 * @since 1.2.2015
 */
public class Message {

    private String username;
    private String text;
    private String time;
    private MessageType type;

    public Message(String text, MessageType type) {
        this.text = text;
        this.type = type;
        this.username = "";
        this.time = "";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public MessageType getType() {
        return type;
    }
}
