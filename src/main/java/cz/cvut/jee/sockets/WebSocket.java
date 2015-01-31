package cz.cvut.jee.sockets;

import cz.cvut.jee.utils.security.SecurityUtil;

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

@ServerEndpoint("/websocket")
public class WebSocket {
    @Inject
    SecurityUtil securityUtil;
    
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message from " + securityUtil.getCurrentUser() + ": " + message);
        sendMessageToAll(session.getUserPrincipal()+ ": " + message);
    }

    @OnOpen
    public void onOpen(Session session){
        System.out.println(session.getId() + " has opened a connection");
        sendMessageToAll("User has connected");
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
        System.out.println("Session " +session.getUserPrincipal()+" has ended");
        sendMessageToAll("User has disconnected");
    }

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