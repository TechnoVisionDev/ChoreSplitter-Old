package servlets.chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * Handles group chat networking.
 * @author Thomas Peters
 */
@ServerEndpoint("/message")
public class ChatServerEndpoint {
	
    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    
    @OnOpen
    public void onOpen(Session curSession) {
        userSessions.add(curSession);
    }
            
    @OnClose
    public void onClose(Session curSession) {
        userSessions.remove(curSession);
    }
    
    @OnMessage
    public void onMessage(String message, Session userSession)
    {
        for(Session ses : userSessions) {
            ses.getAsyncRemote().sendText(message);
        }
    }
}
