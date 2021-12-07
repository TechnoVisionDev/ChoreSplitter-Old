package servlets.chat;

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
    private ConcurrentHashMap<String, List<String>> chatHistory = new ConcurrentHashMap<String, List<String>>();
    
    private String group;
    
    @OnOpen
    public void onOpen(Session curSession) {
    	// Get group code from user session
    	group = curSession.getRequestParameterMap().get("group").get(0);
    	
    	// Add user to group session
        if (!groupSessions.containsKey(group)) {
        	groupSessions.put(group, Collections.synchronizedSet(new HashSet<Session>()));
        }
        groupSessions.get(group).add(curSession);
        
        // Start chat history if doesn't exist for this group
        if (!chatHistory.containsKey(group)) {
        	chatHistory.put(group, Collections.synchronizedList(new ArrayList<String>()));
        } else {
        	// Send old messages to client
        	chatHistory.get(group).forEach(m -> curSession.getAsyncRemote().sendText(m));
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
    	chatHistory.get(group).add(message);
        for (Session ses : groupSessions.get(group)) {
        	ses.getAsyncRemote().sendText(message);
        }
    }
}
