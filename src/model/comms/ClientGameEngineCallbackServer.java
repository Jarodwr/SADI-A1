package model.comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.comms.callback.operations.AbstractCallbackOperation;
import model.interfaces.GameEngineCallback;

public class ClientGameEngineCallbackServer {
	private GameEngineCallback gec;
	
	private ServerSocket server;
	private Socket socket;
	private ObjectInputStream ois;
	private HostDetails host;
	
	public ClientGameEngineCallbackServer(GameEngineCallback gec) {
		this.gec = gec;
		try {
			server = new ServerSocket(0);
			host = new HostDetails(server.getInetAddress().getHostAddress(), server.getLocalPort());
			socket = server.accept();
			ois = new ObjectInputStream(socket.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void poll() {
		while(true) {
			try {
				AbstractCallbackOperation operation = (AbstractCallbackOperation) ois.readObject();
				ois.reset();
				operation.execute(gec);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public HostDetails getHostDetails() {
		return host;
	}
}
