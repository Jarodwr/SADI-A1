package model.comms;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.comms.operations.*;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class GameEngineClientStub implements GameEngine{

	private HostDetails host = new HostDetails(null, 0); //TODO: Add details
	private Socket socket;
	private ObjectOutputStream oos;
	
	private HostDetails clientDetails;
	private ServerSocket returnReceiver;
	private Socket receiverSocket;
	private ObjectInputStream ois;
	private DataInputStream dis;
	
	private ClientGameEngineCallbackServer cgecs;
	
	public GameEngineClientStub() {
		try {
			this.socket = new Socket(host.getHostname(), host.getPort());
			this.oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			returnReceiver = new ServerSocket(0);
			clientDetails = new HostDetails(returnReceiver.getLocalPort());
			receiverSocket = returnReceiver.accept();
			ois = new ObjectInputStream(receiverSocket.getInputStream());
			dis = new DataInputStream(receiverSocket.getInputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void dealPlayer(Player player, int delay) {
		try {
			oos.writeObject(new DealOperation(player, delay));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void dealHouse(int delay) {
		try {
			oos.writeObject(new DealOperation(null, delay));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addPlayer(Player player) {
		try {
			oos.writeObject(new AddPlayerOperation(player));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public Player getPlayer(String id) {
		try {
			oos.writeObject(new GetPlayerOperation(clientDetails, id));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return (Player) getObjectReturn();
	}

	@Override
	public boolean removePlayer(Player player) {
		try {
			oos.writeObject(new RemovePlayerOperation(clientDetails, player));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return getBoolReturn();
	}

	@Override
	public void calculateResult() {
		try {
			oos.writeObject(new CalculateResultOperation());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		try {
			cgecs = new ClientGameEngineCallbackServer(gameEngineCallback);
			oos.writeObject(new AddGameEngineCallbackOperation(cgecs.getHostDetails()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		try {
			oos.writeObject(new RemoveGameEngineCallbackOperation(cgecs.getHostDetails()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Collection<Player> getAllPlayers() {
		try {
			oos.writeObject(new GetAllPlayersOperation(clientDetails));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		Collection<Player> allPlayers = new ArrayDeque<Player>();
		Object obj = getObjectReturn();
		if (obj instanceof Collection<?>) {
			Collection<?> cn = (Collection<?>) obj;
			for (Object p : cn) {
				allPlayers.add((Player) p);
			}
		}
		return allPlayers;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		try {
			oos.writeObject(new PlaceBetOperation(clientDetails, player, bet));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return getBoolReturn();
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		try {
			oos.writeObject(new GetShuffledDeckOperation(clientDetails));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		Deque<PlayingCard> sd = new ArrayDeque<PlayingCard>();
		Object obj = getObjectReturn();
		if (obj instanceof Deque<?>) {
			Deque<?> dq = (Deque<?>) obj;
			for (Object pc : dq) {
				sd.add((PlayingCard) pc);
			}
		}
		return sd;
	}
	
	private boolean getBoolReturn() {
		try {
			boolean bool = dis.readBoolean();
			dis.reset();
			return bool;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Object getObjectReturn() {
		try {
			Object obj = ois.readObject();
			ois.reset();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
