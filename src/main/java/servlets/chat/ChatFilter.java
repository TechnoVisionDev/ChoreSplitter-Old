package servlets.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * Sends data from user session to ChatSercerEndpoint
 * @author Thomas Peters
 */
@WebFilter("/message")
public class ChatFilter implements Filter {
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final Map<String, String[]> props = new HashMap<>();
        
        // Add session attributes to pass on to ChatServerEndpoint
        props.put("group", new String[] { (String) httpRequest.getSession().getAttribute("group") });
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public Map<String, String[]> getParameterMap() {
                return props;
            }
        };
        
        chain.doFilter(wrappedRequest, response);
    }
}
