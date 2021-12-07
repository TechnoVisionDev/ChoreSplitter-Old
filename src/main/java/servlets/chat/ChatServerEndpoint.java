package servlets.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    private static ConcurrentHashMap<String, List<String>> chatHistory = new ConcurrentHashMap<String, List<String>>();
    
    private String group;
    
    @OnOpen
    public void onOpen(Session curSession) {
    	// Add user to group session
    	group = curSession.getRequestParameterMap().get("group").get(0);
        if (!groupSessions.containsKey(group)) {
        	groupSessions.put(group, Collections.synchronizedSet(new HashSet<Session>()));
        }
        groupSessions.get(group).add(curSession);
        
        // Send chat history to client
        if (!chatHistory.containsKey(group)) {
        	chatHistory.put(group, Collections.synchronizedList(new ArrayList<String>()));
        } else {
        	chatHistory.get(group).forEach(m -> {
        		try { curSession.getBasicRemote().sendText(m);
        		} catch (IOException e) { e.printStackTrace(); }
        	});
        }
    }
            
    @OnClose
    public void onClose(Session curSession) {
    	// Remove user from group session
        if (groupSessions.get(group).size() == 1) {
        	groupSessions.remove(group);
        } else {
        	groupSessions.get(group).remove(curSession);
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session userSession) {
    	// Broadcast message to group members
    	chatHistory.get(group).add(message);
        for (Session ses : groupSessions.get(group)) {
        	ses.getAsyncRemote().sendText(message);
        }
    }
}
