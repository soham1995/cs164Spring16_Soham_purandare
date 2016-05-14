package com.tictactoe;

import java.util.ArrayList;
//import java.util.Scanner;
import java.util.Scanner;

public class TicTacToe {

	// board is the game
	static String[] board = new String[9];
	// sdepth is used to control the depth
	static int sdepth;
	static int counter = 0;
	static int counter1 = 0;

	/**
	 * THIS METHOD IS COMMENTED BECAUSE --> GUI PART IS ACTING AS MAIN CLASS IF
	 * SOMEONE NEEDS ONLY FUNCTIONALITY W/O GUI THEN UNCOMMENT THIS MAIN CLASS.
	 * 
	 * main - Main method where the execution of the method starts. In this
	 * method we are giving alternate chances to user and computer. Whenever
	 * there is a win, lose or draw condition meets the program terminates.
	 * 
	 * @param args
	 *            Variadic arguements taken by main
	 */
/*
	// This method is uncommmented when we want to run from TicTacToe class.
	public static void main(String[] args) {
		TicTacToe control = new TicTacToe();
		Scanner input = new Scanner(System.in);
		System.out.println("You are first and will be represented by X");
		System.out.println("The Game is on lets see if you can beat Computer:D");
		while (!control.gameStatus(board) || !control.isGameDraw(board)) {
			System.out.println("Enter your choice:");

			int result = control.makeMove(input.nextInt());

			if (result == 1) {
				System.out.println("O Wins !!");
				System.exit(0);
			} else if (result == 0) {
				System.out.println("Game Draw !!");
				System.exit(0);
			} else if (result == -1) {
				System.out.println("X Wins !!");
				System.exit(0);
			}
		}
		input.close();
	}
*/
	/**
	 * makeMove - Method performs move for user and computer and checks for the
	 * game status Steps performed in this method:<br>
	 * 1) Making move for the user.<br>
	 * 2) Checking the game status if won or drawn by the user.<br>
	 * 3) Then calling algorithm to get maximum result for computer.<br>
	 * 4) Making move for the computer.<br>
	 * 5) Checking the game status if won or drawn by the computer.
	 * 
	 * @param index
	 *            Index where user placed X
	 * @return return X to know that player X wins<br>
	 *         return O to know that computer wins<br>
	 *         return DRAW to know that game is drawn<br>
	 *         else return empty string
	 */
	public int makeMove(int index) {
		// ------------------ User turn ------------------
		board[index] = "X";
		displayBoard(board);
		if (gameStatus(board)) {
			return -101;
		}
		if (isGameDraw(board)) {
			return 100;
		}
		// ------------------ Now turn for computer ------------------
		Node res = MinMax(board, "MAX", 0, 0);
		int i = res.getBoardIndex();

		board[i] = "O";
		System.out.println("Game is on computer made at position: " + i);
		displayBoard(board);
		if (gameStatus(board)) {
			return 101;
		}
		if (isGameDraw(board)) {
			return 100;
		}

		return i;
	}

	/**
	 * MinMax - <br>
	 * 1) generate successor<br>
	 * 2) if no successor or game is finished return score<br>
	 * 3) if there is successor Apply MinMax for each successor or else after
	 * recursive call, i return the good score
	 * 
	 * @param gameBoard
	 * @param MINMAX
	 * @param fils
	 * @param depth
	 * @return
	 */
	public Node MinMax(String[] gameBoard, String MINMAX, int fils, int depth) {
		counter1++;
		System.out.println(counter1);
		// ------------------ Generating Successors ------------------
		ArrayList<String[]> successors = generateSuccessors(gameBoard, MINMAX);
		if (successors == null && sdepth != -1) {
			depth = depth + 1;
			sdepth = -1;
		}
		// ------------------ Check for Game Finish or not ------------------
		if (successors == null || gameStatus(gameBoard)) {
			return new Node(gameBoard, isGameWinOrLose(gameBoard), depth);
		} else {
			// ----- Case of continue applying minmax to each successor -----
			if (sdepth > successors.size()) {
				sdepth = successors.size();
				depth = depth + 1;
			}

			ArrayList<Node> scoreUpdate = new ArrayList<Node>();
			// ----- Recursively calling minimax to each successor -----
			for (int i = 0; i < successors.size(); i++) {
				counter++;
				scoreUpdate.add(MinMax(successors.get(i), alternateMINMAX(MINMAX), 1, depth + 1));
			}
			// ----- Returning the score -----
			Node res = getScore(scoreUpdate, MINMAX);
			if (fils == 1)
				res.updateMatrix(gameBoard);
			return res;
		}
	}

	/**
	 * getScore - Get best score in the current level.<br>
	 * If the level is MAX then MAX value if returned else MIN value is returned
	 * 
	 * @param scoreUpdate
	 * @param MINMAX
	 * @return int Best score for current level
	 */
	public Node getScore(ArrayList<Node> scoreUpdate, String MINMAX) {
		Node result = scoreUpdate.get(0);
		if (MINMAX.equals("MAX")) {
			for (int i = 1; i < scoreUpdate.size(); i++) {
				if ((scoreUpdate.get(i).getScore() > result.getScore())
						|| (scoreUpdate.get(i).getScore() == result.getScore()
								&& scoreUpdate.get(i).depth < result.depth))
					result = scoreUpdate.get(i);
			}
		} else {
			for (int i = 1; i < scoreUpdate.size(); i++) {
				if ((scoreUpdate.get(i).getScore() < result.getScore())
						|| (scoreUpdate.get(i).getScore() == result.getScore()
								&& scoreUpdate.get(i).depth < result.depth))
					result = scoreUpdate.get(i);
			}
		}
		return result;
	}

	/**
	 * generateSuccessors - Get all successors from current state of the board
	 * 
	 * @param gameBoard
	 * @param MINMAX
	 * @return List Possible successors of current state of the board
	 */
	public ArrayList<String[]> generateSuccessors(String[] gameBoard, String MINMAX) {
		ArrayList<String[]> successors = new ArrayList<String[]>();
		for (int i = 0; i < gameBoard.length; i++) {
			if (gameBoard[i].equals(" ")) {
				String[] child = new String[9];
				for (int j = 0; j < 9; j++)
					child[j] = gameBoard[j];

				if (MINMAX.equals("MAX"))
					child[i] = "o";
				else
					child[i] = "x";
				successors.add(child);
			}
		}
		return (successors.size() == 0) ? null : successors;
	}

	/**
	 * TicTacToe - Default constructor for intializing the game board
	 */
	public TicTacToe() {
		for (int i = 0; i < 9; i++)
			board[i] = " ";
	}

	/**
	 * alternateMINMAX - Toggle between MIN MAX alternately
	 * 
	 * @param MINMAX
	 *            Current state value of MINMAX
	 * @return String Returns MIN or MAX new state value
	 */
	public String alternateMINMAX(String MINMAX) {
		return (MINMAX.equals("MIN")) ? "MAX" : "MIN";
	}

	/**
	 * isGameWinOrLose - checks game status whether the last played player has
	 * won or lose the game or no effect on game
	 * 
	 * @param gameBoard
	 *            The gameBoard on which game is played
	 * @return score Returns 1 if computer wins, -1 if user wins and 0 if no one
	 *         wins.
	 */
	public int isGameWinOrLose(String[] gameBoard) {
		if ((gameBoard[0].equalsIgnoreCase("x") && gameBoard[1].equalsIgnoreCase("x")
				&& gameBoard[2].equalsIgnoreCase("x"))
				|| (gameBoard[3].equalsIgnoreCase("x") && gameBoard[4].equalsIgnoreCase("x")
						&& gameBoard[5].equalsIgnoreCase("x"))
				|| (gameBoard[6].equalsIgnoreCase("x") && gameBoard[7].equalsIgnoreCase("x")
						&& gameBoard[8].equalsIgnoreCase("x"))
				|| (gameBoard[0].equalsIgnoreCase("x") && gameBoard[3].equalsIgnoreCase("x")
						&& gameBoard[6].equalsIgnoreCase("x"))
				|| (gameBoard[1].equalsIgnoreCase("x") && gameBoard[4].equalsIgnoreCase("x")
						&& gameBoard[7].equalsIgnoreCase("x"))
				|| (gameBoard[2].equalsIgnoreCase("x") && gameBoard[5].equalsIgnoreCase("x")
						&& gameBoard[8].equalsIgnoreCase("x"))
				|| (gameBoard[0].equalsIgnoreCase("x") && gameBoard[4].equalsIgnoreCase("x")
						&& gameBoard[8].equalsIgnoreCase("x"))
				|| (gameBoard[2].equalsIgnoreCase("x") && gameBoard[4].equalsIgnoreCase("x")
						&& gameBoard[6].equalsIgnoreCase("x")))
			return -1;

		if ((gameBoard[0].equalsIgnoreCase("o") && gameBoard[1].equalsIgnoreCase("o")
				&& gameBoard[2].equalsIgnoreCase("o"))
				|| (gameBoard[3].equalsIgnoreCase("o") && gameBoard[4].equalsIgnoreCase("o")
						&& gameBoard[5].equalsIgnoreCase("o"))
				|| (gameBoard[6].equalsIgnoreCase("o") && gameBoard[7].equalsIgnoreCase("o")
						&& gameBoard[8].equalsIgnoreCase("o"))
				|| (gameBoard[0].equalsIgnoreCase("o") && gameBoard[3].equalsIgnoreCase("o")
						&& gameBoard[6].equalsIgnoreCase("o"))
				|| (gameBoard[1].equalsIgnoreCase("o") && gameBoard[4].equalsIgnoreCase("o")
						&& gameBoard[7].equalsIgnoreCase("o"))
				|| (gameBoard[2].equalsIgnoreCase("o") && gameBoard[5].equalsIgnoreCase("o")
						&& gameBoard[8].equalsIgnoreCase("o"))
				|| (gameBoard[0].equalsIgnoreCase("o") && gameBoard[4].equalsIgnoreCase("o")
						&& gameBoard[8].equalsIgnoreCase("o"))
				|| (gameBoard[2].equalsIgnoreCase("o") && gameBoard[4].equalsIgnoreCase("o")
						&& gameBoard[6].equalsIgnoreCase("o")))
			return 1;

		return 0;
	}

	/**
	 * gameStatus - Checks the game status and returns if the game is wonby user
	 * or computer or no effect
	 * 
	 * @param gameBoard
	 * @return Boolean Returns true if game is won by user or computer and false
	 *         otherwise
	 */
	public boolean gameStatus(String[] gameBoard) {
		return (isGameWinOrLose(gameBoard) != 0) ? true : false;
	}

	/**
	 * gameDraw - Tests if the game is drawn or not.<br>
	 * If the board is full that means game is drawn<br>
	 * If gameBoard still has empty square that means the game isn't finished
	 * 
	 * @param gameBoard
	 * @return Returns true if game is finished otherwise false.
	 */
	public boolean isGameDraw(String[] gameBoard) {
		for (int i = 0; i < 9; i++)
			if (gameBoard[i].equals(" "))
				return false;
		return true;
	}

	/**
	 * displayBoard - Prints out the board current status.
	 * 
	 * @param gameBoard
	 */
	public static void displayBoard(String[] gameBoard) {
		for (int i = 0; i < gameBoard.length; i++) {
			if (!gameBoard[i].equals(""))
				System.out.print(gameBoard[i] + "  |  ");
			else
				System.out.print("   |  ");
			if ((i + 1) % 3 == 0) {
				System.out.println("\r\n-------------------\r");
			}
		}
	}

}