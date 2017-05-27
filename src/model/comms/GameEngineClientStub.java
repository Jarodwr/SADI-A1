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

/**
 * Clientside Game Engine
 * @author Jarod
 *
 */
public class GameEngineClientStub implements GameEngine{

	private HostDetails host = new HostDetails("192.168.1.6", 50001); //TODO: Add details
	private Socket socket;
	private ObjectOutputStream oos;
	private HostDetails callbackServer;
	
	/**
	 * Initialize connection to server
	 */
	public GameEngineClientStub() {
		try {
			this.socket = new Socket(host.getHostname(), host.getPort());
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	@Override
	public void dealPlayer(Player player, int delay) {
		try {
			oos.writeObject(new DealOperation(player, delay));
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void dealHouse(int delay) {
		try {
			oos.writeObject(new DealOperation(null, delay));
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void addPlayer(Player player) {
		try {
			oos.writeObject(new AddPlayerOperation(player));
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}

	@Override
	public Player getPlayer(String id) {
		try {
//			Generate a server to accept the return value of the operation
			ServerSocket tempReturnServer = new ServerSocket(0);
			HostDetails clientDetails = new HostDetails(tempReturnServer.getLocalPort());
			
//			Send the operation to the server
			oos.writeObject(new GetPlayerOperation(clientDetails, id));
			
//			Wait for the connection
			Socket receiverSocket = tempReturnServer.accept();
			ObjectInputStream ois = new ObjectInputStream(receiverSocket.getInputStream());
			
//			Wait for the return response
			Object obj = ois.readObject();
			
//			Close all of the temporary server related streams
			ois.close();
			receiverSocket.close();
			tempReturnServer.close();
			
			return (Player) obj;
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		try {
//			Generate a server to accept the return value of the operation
			ServerSocket tempReturnServer = new ServerSocket(0);
			HostDetails clientDetails = new HostDetails(tempReturnServer.getLocalPort());
			
//			Send the operation to the server
			oos.writeObject(new RemovePlayerOperation(clientDetails, player));
			
//			Wait for the connection
			Socket receiverSocket = tempReturnServer.accept();
			DataInputStream dis = new DataInputStream(receiverSocket.getInputStream());
			
//			Wait for the return response
			boolean value = dis.readBoolean();
			
//			Close all of the temporary server related streams
			dis.close();
			receiverSocket.close();
			tempReturnServer.close();
			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void calculateResult() {
		try {
			oos.writeObject(new CalculateResultOperation());
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		try {
//			Send the host details of the client to the server to create the necessary GUI callback
			ClientGameEngineCallbackServer cgecs = new ClientGameEngineCallbackServer(gameEngineCallback);
			callbackServer = cgecs.getHostDetails();
			oos.writeObject(new AddGameEngineCallbackOperation(cgecs.getHostDetails()));
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		try {
			oos.writeObject(new RemoveGameEngineCallbackOperation(callbackServer));
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public Collection<Player> getAllPlayers() {
		try {
//			Generate a server to accept the return value of the operation
			ServerSocket tempReturnServer = new ServerSocket(0);
			HostDetails clientDetails = new HostDetails(tempReturnServer.getLocalPort());
			
//			Send the operation to the server
			oos.writeObject(new GetAllPlayersOperation(clientDetails));
			
//			Wait for the connection
			Socket receiverSocket = tempReturnServer.accept();
			ObjectInputStream ois = new ObjectInputStream(receiverSocket.getInputStream());

//			Wait for the return response
			Object obj = ois.readObject();
			
//			Close all of the temporary server related streams
			ois.close();
			receiverSocket.close();
			tempReturnServer.close();
			
//			Safely parse the collection object
			Collection<Player> allPlayers = new ArrayDeque<Player>();
			if (obj instanceof Collection<?>) {
				Collection<?> cn = (Collection<?>) obj;
				for (Object p : cn) {
					allPlayers.add((Player) p);
				}
			}
			
			return allPlayers;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}

	@Override
	public boolean placeBet(Player player, int bet) {
		try {
//			Generate a server to accept the return value of the operation
			ServerSocket tempReturnServer = new ServerSocket(0);
			HostDetails clientDetails = new HostDetails(tempReturnServer.getLocalPort());
			
//			Send the operation to the server
			oos.writeObject(new PlaceBetOperation(clientDetails, player, bet));
			
//			Wait for the connection
			Socket receiverSocket = tempReturnServer.accept();
			DataInputStream dis = new DataInputStream(receiverSocket.getInputStream());
			
//			Wait for the return response
			boolean value = dis.readBoolean();
			
//			Close all of the temporary server related streams
			dis.close();
			receiverSocket.close();
			tempReturnServer.close();
			
			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		try {
//			Generate a server to accept the return value of the operation
			ServerSocket tempReturnServer = new ServerSocket(0);
			HostDetails clientDetails = new HostDetails(tempReturnServer.getLocalPort());
			
//			Send the operation to the server
			oos.writeObject(new GetShuffledDeckOperation(clientDetails));
			
//			Wait for the connection
			Socket receiverSocket = tempReturnServer.accept();
			ObjectInputStream ois = new ObjectInputStream(receiverSocket.getInputStream());
			
//			Wait for the return response
			Object obj = ois.readObject();
			
//			Close all of the temporary server related streams
			ois.close();
			receiverSocket.close();
			tempReturnServer.close();

			Deque<PlayingCard> sd = new ArrayDeque<PlayingCard>();
			if (obj instanceof Deque<?>) {
				Deque<?> dq = (Deque<?>) obj;
				for (Object pc : dq) {
					sd.add((PlayingCard) pc);
				}
			}
			return sd;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
}
