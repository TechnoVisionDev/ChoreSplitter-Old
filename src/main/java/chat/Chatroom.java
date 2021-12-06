package chat;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Chatroom {

	private ArrayBlockingQueue<ServerThread> serverThreads;

	public static void main(String [] args)
	{
		new Chatroom(6789);
	}

	public Chatroom(int port)
	{
		try
		{
			System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			serverThreads = new ArrayBlockingQueue<ServerThread>(5); // new ArrayList<>();
			while(true)
			{
				Socket s = ss.accept();   //  Accept the incoming request
				System.out.println("Connection from " + s + " at " + new Date());
				ServerThread st = new ServerThread(s, this); //connection handler
				System.out.println("Adding this client to active client list");
				serverThreads.add(st);
			}
		}
		catch (Exception ex) {
			System.out.println("Server shut down unexpectedly.");
			return;
		}

	}

	public void broadcast(String message) throws IOException
	{
		if (message != null) {
			System.out.println("broadcasting ..." + message);
			for(ServerThread threads : serverThreads)
				threads.sendMessage(message);
		}
	}

}


