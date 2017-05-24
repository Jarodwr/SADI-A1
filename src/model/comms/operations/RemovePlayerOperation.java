package model.comms.operations;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import model.comms.HostDetails;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class RemovePlayerOperation extends AbstractGameOperation{

	HostDetails host;
	Player player;
	
	public RemovePlayerOperation(HostDetails host, Player player) {
		this.player = player;
		this.host = host;
	}
	
	@Override
	public void execute(GameEngine ge) {
		try {
			Socket socket = new Socket(host.getHostname(), host.getPort());
			DataOutputStream dis = new DataOutputStream(socket.getOutputStream());
			dis.writeBoolean(ge.removePlayer(player));
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
