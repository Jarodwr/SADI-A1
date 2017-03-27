package controller;

import model.GameEngineCallbackImpl;
import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class GameController {
	
	GameController() {
		Player[] players = new Player[]
		{ new SimplePlayer("1", "The Shark", 1000), new SimplePlayer("2", "The Loser", 500) };
	}
}
