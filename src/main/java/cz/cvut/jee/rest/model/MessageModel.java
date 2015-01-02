package cz.cvut.jee.rest.model;

/**
 * Message model.
 *
 * @author jakubchalupa
 * @since 02.01.15
 */
public class MessageModel {

    private String text;

    private String timeOfCreation;

    private String author;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
