package model.comms.operations;

import java.io.Serializable;

import model.interfaces.GameEngine;

public abstract class AbstractGameOperation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 706064291173415422L;

	public abstract void execute(GameEngine ge);
}
