package view;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

/**
 * Test harness for SADI Assignment 1, semester 1 2017 Updated by Caspar based
 * on original code by tutor Adrian Wong
 */
public class TestHarness
{
	private static final int DEAL_DELAY = 0;

	private float score = 0.0f;

	private int resultHouse = 0;
	private int resultPlayer = 0;
	private int testNumber = 0;

	private GameEngine gameEngine = new GameEngineImpl();
	private Player theShark = new SimplePlayer("1", "The Shark", 1000);
	private Player theLoser = new SimplePlayer("2", "The Loser", 500);
	private Player theCasual = new SimplePlayer("3", "The Casual", 200);

	private CountCallBack countCallBack = new CountCallBack();
	private GameEngineCallback resultCallBack = new ResultCallback();

	public static void main(String[] args)
	{
		new TestHarness();
	}

	public TestHarness()
	{
		testAddPlayers();
		testRemovePlayer();
		testInvalidBet();
		testValidBet();

		gameEngine.addGameEngineCallback(resultCallBack);
		testFirstDeal();
		testSecondDeal();

		gameEngine.addGameEngineCallback(countCallBack);
		testCallbackCalls();

		testPreCondition();

		System.out.println("\n\nThe test harness score is: " + score + "/ 8.0");
	}

	/**
	 * Test the addition of 3 players to the system. The expected size of the
	 * Collection is 3.
	 */
	private void testAddPlayers()
	{
		try
		{
			testNumber = 1;
			System.out.println("Test 01 - Adding Players (1.0 marks)");

			gameEngine.addPlayer(theShark);
			gameEngine.addPlayer(theLoser);
			gameEngine.addPlayer(theCasual);

			if (gameEngine.getAllPlayers().size() == 3)
			{
				System.out.println("You successfully added three " + "players to the game (1.0 marks)");
				score += 1;
			} else
			{
				System.out.println("One or more of your players is " + "incorrectly missing from the game (0.0 marks)");
			}
		} catch (Exception e)
		{
			handleException(e, testNumber, 0);
		}
	}

	/**
	 * Test the removal of 1 player from the system. The removePlayer() method
	 * should return false and the size of the player collection should be equal
	 * to 2.
	 */
	private void testRemovePlayer()
	{
		try
		{
			testNumber = 2;
			System.out.println("\n\nTest 02 - Removing a Player (0.5 marks)");

			boolean isRemoved = gameEngine.removePlayer(theCasual);

			if (isRemoved == true && gameEngine.getAllPlayers().size() == 2)
			{
				System.out.println("You successfully removed a player " + "from the game (0.5 marks)");
				score += 0.5;
			} else
			{
				System.out.println("Player was not removed from the game " + "(0.0 marks)");
			}
		} catch (Exception e)
		{
			handleException(e, testNumber, 0);
		}
	}

	/**
	 * Test for a bet that exceeds a player's available points. The placeBet
	 * method should return false.
	 */
	private void testInvalidBet()
	{
		try
		{
			testNumber = 3;
			System.out.println("\n\nTest 03 - Handling an Invalid Bet (0.5 marks)");

			boolean isValid = theShark.placeBet(1100);

			if (isValid == true)
			{
				System.out.println("The gamber's invalid bet was " + "not handled (0.0 marks)");
			} else
			{
				System.out.println("You successfully detected an " + "invalid bet for insufficent points (0.5 marks)");
				score += 0.5;
			}
		} catch (Exception e)
		{
			handleException(e, testNumber, 0);
		}
	}

	/**
	 * Test for a valid bet. Checks if the game engine's placeBet() method
	 * forwards the call to the appropriate player.
	 */
	private void testValidBet()
	{
		try
		{
			testNumber = 4;
			System.out.println("\n\nTest 04 - Handling a Valid Bet (0.5 marks)");

			boolean isLoserBetValid = gameEngine.placeBet(theLoser, 400);

			if (isLoserBetValid && theLoser.getBet() == 400)
			{
				System.out.println(theLoser.getPlayerName() + " successfully made a bet (0.5 marks)");
				score += 0.5;
			} else
			{
				System.out.println(theLoser.getPlayerName()
						+ "'s bet either returned false or getBet() was incorrect (0.0 marks)");
			}
		} catch (Exception e)
		{
			handleException(e, testNumber, 0);
		}
	}

	/**
	 * Deal once and test results.
	 */
	private void testFirstDeal()
	{
		try
		{
			testNumber = 5;
			System.out.println("\n\nTest 05 - Test First Deal (1.5 marks)");

			testDealImpl(theShark, 1.5f);
		} catch (Exception e)
		{
			handleException(e, testNumber, 0);
		}
	}

	/**
	 * Deal a second time and test results.
	 */
	private void testSecondDeal()
	{
		try
		{
			testNumber = 6;
			System.out.println("\n\nTest 06 - Test Second Deal (1.0 marks)");

			testDealImpl(theLoser, 1.0f);
		} catch (Exception e)
		{
			handleException(e, testNumber, 0);
		}
	}

	private void testDealImpl(Player player, float marks)
	{
		int currentPoints = player.getPoints();

		theShark.placeBet(200);
		theLoser.placeBet(200);

		for (Player p : gameEngine.getAllPlayers())
		{
			gameEngine.dealPlayer(p, DEAL_DELAY);
		}

		gameEngine.calculateResult();

		int result = player.getResult();

		if (player.getPoints() == (currentPoints + 200) || player.getPoints() == (currentPoints - 200)
				|| (result == resultHouse && player.getPoints() == currentPoints))
		{
			System.out.println("You completed a successful deal with " + player.getPlayerName()
					+ "'s point balances updated correctly (" + marks + " marks)");

			score += marks;
		} else
		{
			System.out.println(player.getPlayerName() + "'s balance not updated correctly (0.0 marks)");
		}
	}

	/**
	 * Test the number of the various callback calls when for a deal and result
	 * calculation.
	 */
	private void testCallbackCalls()
	{
		float localScore = 0.0f;

		try
		{
			testNumber = 7;
			System.out.println("\n\nTest 07 - Test Number of Callback Calls (2.5 marks)");

			theShark.placeBet(100);

			gameEngine.dealPlayer(theShark, DEAL_DELAY);
			gameEngine.calculateResult();

			// must be dealt at least 2 cards (is not a very strict check)
			if ((countCallBack.countNextPlayer >= 2) && (countCallBack.countNextHouse) >= 2)
			{
				System.out.println("At least two cards dealt for player and house: (1.0 marks)");

				localScore += 1.0;
			} else
			{
				System.out.println("Incorrect number of cards (< 2): " + "player=" + countCallBack.countNextPlayer
						+ ", house=" + countCallBack.countNextHouse + " (0.0 marks)");
			}

			// either 1 bust card or no bust card and a score of 21
			if ((countCallBack.countBustPlayer == 1 || (countCallBack.countBustPlayer == 0 && resultPlayer == 21))
					&& (countCallBack.countBustHouse == 1 || (countCallBack.countBustHouse == 0 && resultHouse == 21)))
			{
				System.out.println("Bust called correctly for player and house: (1.0 marks)");

				localScore += 1.0;
			} else
			{
				System.out.println("Incorrect number of bust cards: " + "player=" + countCallBack.countBustPlayer
						+ ", house=" + countCallBack.countBustHouse + " (0.0 marks)");
			}

			// result should be called only once
			if ((countCallBack.countResultPlayer == 1) && (countCallBack.countResultHouse) == 1)
			{
				System.out.println("Result called once each for player and house: (0.5 marks)");

				localScore += 0.5;
			} else
			{
				System.out.println("Incorrect number of results: " + "player=" + countCallBack.countResultPlayer
						+ ", house=" + countCallBack.countResultHouse + " (0.0 marks)");
			}
			System.out.println("Callback Total Score: " + String.format("(%.1f marks)", localScore));
			score += localScore;
		} catch (Exception e)
		{
			handleException(e, testNumber, localScore);
		}
	}

	/**
	 * Tests that there is an assertion to capture illegal input whereby
	 * initialDelay > finalDelay.
	 */
	private void testPreCondition()
	{
		testNumber = 8;
		System.out.println("\n\nTest 08 - Test dealPlayer() Precondition (0.5 marks)");

		try
		{
			theShark.placeBet(-1);
			gameEngine.dealPlayer(theShark, -1);
		} catch (AssertionError e)
		{
			System.out.println("Succesfully detected illegal input with assertion (0.5 marks)");

			score += 0.5;
			return;
		} catch (IllegalArgumentException e)
		{
			System.out.println("Succesfully detected illegal input with IllegalArgumentException (0.5 marks)");

			score += 0.5;
			return;
		} catch (Exception e)
		{
			System.out.println("TO DO: Caught Unknown Exception for illegal argument .. Marker to check code");

			return;
		}
		System.out.println("Did not capture illegal input (0.0 marks)");
	}

	/**
	 * Overrides the callback to capture results. Called before testing deal
	 * results.
	 */
	class ResultCallback implements GameEngineCallback
	{
		@Override
		public void nextCard(Player player, PlayingCard card, GameEngine engine)
		{
		}

		@Override
		public void bustCard(Player player, PlayingCard card, GameEngine engine)
		{
		}

		@Override
		public void result(Player player, int result, GameEngine engine)
		{
			System.out.println("CALLBACK: Player result() called for test #" + testNumber + ", result=" + result);
		}

		@Override
		public void nextHouseCard(PlayingCard card, GameEngine engine)
		{
		}

		@Override
		public void houseBustCard(PlayingCard card, GameEngine engine)
		{
		}

		@Override
		public void houseResult(int result, GameEngine engine)
		{
			System.out.println("CALLBACK: houseResult() called for test #" + testNumber + ", result=" + result);
			resultHouse = result;
		}

	}

	class CountCallBack implements GameEngineCallback
	{
		int countNextPlayer = 0;
		int countNextHouse = 0;
		int countBustPlayer = 0;
		int countBustHouse = 0;
		int countResultPlayer = 0;
		int countResultHouse = 0;

		@Override
		public void nextCard(Player player, PlayingCard card, GameEngine engine)
		{
			countNextPlayer++;
		}

		@Override
		public void bustCard(Player player, PlayingCard card, GameEngine engine)
		{
			countBustPlayer++;
		}

		@Override
		public void result(Player player, int result, GameEngine engine)
		{
			countResultPlayer++;
			resultPlayer = result;
		}

		@Override
		public void nextHouseCard(PlayingCard card, GameEngine engine)
		{
			countNextHouse++;
		}

		@Override
		public void houseBustCard(PlayingCard card, GameEngine engine)
		{
			countBustHouse++;
		}

		@Override
		public void houseResult(int result, GameEngine engine)
		{
			countResultHouse++;
			resultHouse = result;
		}
	}

	private void handleException(Exception e, int testNumber, float partialScore)
	{
		System.out.println("*EXCEPTION* in test #" + testNumber + ", " + e.getMessage());
		System.out.println("Partial Score: " + String.format("(%.1f marks)", partialScore));
	}
}
