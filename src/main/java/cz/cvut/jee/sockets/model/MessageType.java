package cz.cvut.jee.sockets.model;

/**
 * Types of messages, which can be sent via websocket
 * 
 * CONNECTED - tells everyone person is connected
 * DISCONNECTED - tells everyone person is disconnected
 * MESSAGE - standard chat message
 * LOGGED_USERS - message with usernames of logged users
 *
 * @author Tomas Cervenka
 * @since 1.2.2015
 */
public enum MessageType {

    CONNECTED, DISCONNECTED, MESSAGE, LOGGED_USERS
    
}
