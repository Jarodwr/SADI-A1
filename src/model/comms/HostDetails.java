package model.comms;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostDetails implements Serializable{

	private String hostname;
	private int port;
	
	/**
	 * Creates a HostDetails object that gets the ip where it was initialized
	 * @param port	Port number
	 */
	public HostDetails(int port) {
		try {
			this.hostname = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = port;
	}
	
	/**
	 * Creates a HostDetails object
	 * @param hostname	IP address
	 * @param port	Port number
	 */
	public HostDetails(String hostname, int port) {
		super();
		this.hostname = hostname;
		this.port = port;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public int getPort() {
		return port;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof HostDetails) {
			HostDetails host = (HostDetails) object;
			return hostname.equals(host.getHostname()) && port == host.getPort();
		}
		
		return false;
	}
}
