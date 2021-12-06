package chat;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

	private PrintWriter pw;
	private BufferedReader br;
	private Chatroom cr;
	private Socket s;

	public ServerThread(Socket s, Chatroom cr)
	{
		this.s = s;
		this.cr = cr;
		try
		{
			pw = new PrintWriter(s.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			start();
		}
		catch (IOException ex) {ex.printStackTrace();}
	}

	public void sendMessage(String message)
	{
		pw.println(message);
	}

	public void run()
	{
		try {
			while(true)
			{
				String line = br.readLine();
				cr.broadcast(line);
			}
		}
		catch (Exception ex) {ex.printStackTrace();}
		finally {
			try {
				pw.close();
				br.close();
				s.close();
			}
			catch (Exception ex) {ex.printStackTrace();}
		}//finally
	}//run
}
