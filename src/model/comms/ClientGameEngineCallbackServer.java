package model.comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.comms.callback.operations.AbstractCallbackOperation;
import model.interfaces.GameEngineCallback;

/**
 * Server on the client that receives GEC operations and
 * parses executes them on the appropriate client-side GEC
 * @author Jarod
 *
 */
public class ClientGameEngineCallbackServer {
	private GameEngineCallback gec;
	private ServerSocket server;
	private Socket socket;
	private ObjectInputStream ois;
	private HostDetails host;
	
	private Thread pollThread = new Thread() {
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					AbstractCallbackOperation operation = (AbstractCallbackOperation) ois.readObject();
					operation.execute(gec);
				} catch (ClassNotFoundException | IOException e) {
					return;
				}
			}
		}
	};
	
	/**
	 * Initialize the server
	 * @param gec	the GUI's callback
	 */
	public ClientGameEngineCallbackServer(GameEngineCallback gec) {
		this.gec = gec;
		try {
			server = new ServerSocket(0);
			host = new HostDetails(server.getInetAddress().getHostAddress(), server.getLocalPort());
			new Thread() {
				public void run() {
					try {
						socket = server.accept();
						ois = new ObjectInputStream(socket.getInputStream());
						pollThread.start();	//	poll without blocking
						
					} catch (IOException e) {
						return;
						
					}
					
				}
			}.start();
			
		} catch(IOException e) {
			return;
			
		}
		
	}
	
	/**
	 * Getter method
	 * @return	HostDetails
	 */
	public HostDetails getHostDetails() {
		return host;
	}
}
