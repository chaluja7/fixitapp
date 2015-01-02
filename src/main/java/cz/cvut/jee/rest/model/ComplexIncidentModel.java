package cz.cvut.jee.rest.model;

import java.util.List;

/**
 * Complex incident model.
 *
 * @author jakubchalupa
 * @since 29.12.14
 */
public class ComplexIncidentModel extends SimpleIncidentModel {

    private String description;

    private String address;

    private List<MessageModel> messages;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }
}
