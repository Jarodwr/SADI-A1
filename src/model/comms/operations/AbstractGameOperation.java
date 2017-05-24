package model.comms.operations;

import java.io.Serializable;

import model.interfaces.GameEngine;

public abstract class AbstractGameOperation implements Serializable{
	public abstract void execute(GameEngine ge);
}
