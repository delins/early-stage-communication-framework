package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener {	
	private static int port = 1234, maxConnections = 0;

	int i = 0; 
	
	public void startListening() {
		try {
			ServerSocket listener = new ServerSocket(port);
			Socket server;
			System.out.println("Server started listening on port " + port);

			while((i++ < maxConnections) || (maxConnections == 0)) {
				Connection connection;
				server = listener.accept(); // check if we need a security manager (a part of accept() )
				Connection conn_c= new Connection(server);
				Thread t = new Thread(conn_c);
				t.start();
			}
		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}

	}
}