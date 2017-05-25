package model.comms.operations;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import model.comms.HostDetails;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class PlaceBetOperation extends AbstractGameOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7969807789259913473L;
	HostDetails host;
	Player player;
	int bet;
	
	public PlaceBetOperation(HostDetails host, Player player, int bet) {
		this.host = host;
		this.player = player;
		this.bet = bet;
	}
	
	@Override
	public void execute(GameEngine ge) {
		try {

			Socket socket = new Socket(host.getHostname(), host.getPort());
			DataOutputStream dis = new DataOutputStream(socket.getOutputStream());
			dis.writeBoolean(ge.placeBet(player, bet));
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
