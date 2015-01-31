package cz.cvut.jee.sockets;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/admin/websocket")
public class WebSocket {
    @Inject
    MessageEncoder messageEncoder;
    
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(String message, Session session){
        
        try {
            sendMessageToAll(
                messageEncoder.encode(
                    new Message(
                        session.getUserPrincipal().getName(), StringEscapeUtils.escapeHtml4(message)
                    )
                )
            );
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session){
        sendMessageToAll("User has connected");
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
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