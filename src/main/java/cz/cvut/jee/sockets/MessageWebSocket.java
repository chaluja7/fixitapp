package cz.cvut.jee.sockets;

import cz.cvut.jee.sockets.model.MessageType;
import cz.cvut.jee.sockets.utils.MessageUtil;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates websocket and reacts on it's events
 *
 * @author Tomas Cervenka
 * @since 1.2.2015
 */
@ServerEndpoint("/admin/messagewebsocket")
public class MessageWebSocket {

    @Inject
    MessageUtil messageUtil;
    
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    /**
     * On chat message receive resend message to everyone 
     * @param message Text of received chat message
     * @param session Session from which we've got the message
     */
    @OnMessage
    public void onMessage(String message, Session session){
        if(message != null && message.length() > 0) {
            sendMessageToAll(messageUtil.getChatMessage(message, session) );
        }
    }

    /**
     * When user is connected, tell it to everyone <br>
     * Informs that user about connected users     
     * @param session Session from which websocket was opened
     */
    @OnOpen
    public void onOpen(Session session){
        
        // updates everybody
        sendMessageToAll(
            messageUtil.getGeneralMessage("", MessageType.CONNECTED, session)
        );

        String loggedUsers = getLogged();
        
        // tells user, who is already logged
        if(sessions.size() > 0)
            try {
                session.getBasicRemote().sendText(
                        messageUtil.getGeneralMessage(loggedUsers, MessageType.LOGGED_USERS, session)
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

        sessions.add(session);
    }
    
    /**
     * When user is disconnected, tell it to everyone <br>
     * @param session Session from which websocket was closed
     */
    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
        
        sendMessageToAll(
                messageUtil.getGeneralMessage("", MessageType.DISCONNECTED, session)
        );
    }

    /**
     * @return String with usernames of users currently using chat.
     */
    private String getLogged(){
        String result = "";
        
        for(Session s : sessions){
            result += "[" + s.getUserPrincipal().getName() + "] ";
        }
        
        return result;
        
    }

    /**
     * Sends message to all users on chat.
     * @param message Text we want to send.
     */
    private void sendMessageToAll(String message){
        for(Session s : sessions){
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}