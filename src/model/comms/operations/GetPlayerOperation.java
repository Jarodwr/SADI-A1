package model.comms.operations;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.comms.HostDetails;
import model.interfaces.GameEngine;

public class GetPlayerOperation extends AbstractGameOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4249547654591521538L;
	HostDetails host;
	String id;
	
	public GetPlayerOperation(HostDetails host, String id) {
		this.host = host;
		this.id = id;
	}
	
	@Override
	public void execute(GameEngine ge) {
		try {
			Socket socket = new Socket(host.getHostname(), host.getPort());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(ge.getPlayer(id));
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
