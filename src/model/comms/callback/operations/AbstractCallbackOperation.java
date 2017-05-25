package model.comms.callback.operations;

import java.io.Serializable;

import model.interfaces.GameEngineCallback;

public abstract class AbstractCallbackOperation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 893038077827829815L;

	public abstract void execute(GameEngineCallback gui);
}
