package servlets.chat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * Handles group chat networking using thread-safe datastructures.
 * @author Thomas Peters
 */
@ServerEndpoint("/message")
public class ChatServerEndpoint {
	
    private static ConcurrentHashMap<String, Set<Session>> groupSessions = new ConcurrentHashMap<String, Set<Session>>();
    private String group;
    
    @OnOpen
    public void onOpen(Session curSession) {
    	group = curSession.getRequestParameterMap().get("group").get(0);
        if (!groupSessions.containsKey(group)) {
        	groupSessions.put(group, Collections.synchronizedSet(new HashSet<Session>()));
        }
        groupSessions.get(group).add(curSession);
    }
            
    @OnClose
    public void onClose(Session curSession) {
        if (groupSessions.get(group).size() == 1) {
        	groupSessions.remove(group);
        } else {
        	groupSessions.get(group).remove(curSession);
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session userSession) {
        for (Session ses : groupSessions.get(group)) {
        	ses.getAsyncRemote().sendText(message);
        }
    }
}
