package model.comms;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.comms.callback.operations.*;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class ServerStubGameEngineCallback implements GameEngineCallback{
	private HostDetails host;
	private Socket socket;
	private ObjectOutputStream oos;
	
	public ServerStubGameEngineCallback(HostDetails host) {
		this.host = host;
		try {
			this.socket = new Socket(this.host.getHostname(), this.host.getPort());
			this.oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		try {
			oos.writeObject(new NextCardOperation(player, card));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		try {
			oos.writeObject(new BustCardOperation(player, card));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		try {
			oos.writeObject(new ResultOperation(player, result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		try {
			oos.writeObject(new NextHouseCardOperation(card));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		try {
			oos.writeObject(new HouseBustCardOperation(card));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		try {
			oos.writeObject(new HouseResultOperation(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
