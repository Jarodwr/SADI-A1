package model.comms.callback.operations;

import java.io.Serializable;

import model.interfaces.GameEngineCallback;

public abstract class AbstractCallbackOperation implements Serializable{
	public abstract void execute(GameEngineCallback gui);
}
