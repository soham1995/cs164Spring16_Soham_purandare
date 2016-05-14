package com.tictactoe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class for GUI for TicTacToe Game
 */
public class GUI {

	JFrame frame = new JFrame("TicTacToe");
	JButton[][] buttons = new JButton[3][3];
	JButton reset = new JButton("Reset");
	JOptionPane turn;
	int moveCounter = 9;
	boolean gameWon = false;
	TicTacToe control = new TicTacToe();

	/**
	 * Main starting of the TicTacToe Game
	 */
	public static void main(String[] args) {
		GUI game = new GUI();
		game.initialize();
	}

	/**
	 * Initializing the frame
	 */
	public GUI() {
		super();
		frame.setSize(350, 450);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Initializing all components that would be added inside the frame
	 */
	public void initialize() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel menu = new JPanel(new BorderLayout());
		JPanel game = new JPanel(new GridLayout(3, 3));

		frame.add(mainPanel);

		mainPanel.setPreferredSize(new Dimension(325, 425));

		menu.setPreferredSize(new Dimension(300, 50));

		game.setPreferredSize(new Dimension(300, 300));

		mainPanel.add(menu, BorderLayout.NORTH);
		mainPanel.add(game, BorderLayout.SOUTH);

		menu.add(reset, BorderLayout.EAST);
		reset.addActionListener(new myActionListener());

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setText("");
				buttons[i][j].setVisible(true);

				game.add(buttons[i][j]);
				buttons[i][j].addActionListener(new myActionListener());
			}
		}
	}

	/**
	 * Listeners for Buttons to perform action
	 */
	private class myActionListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			// Display X's or O's on the buttons
			if (a.getSource() == buttons[0][0]) {
				makeMove(0, 0, 0);
			} else if (a.getSource() == buttons[0][1]) {
				makeMove(0, 1, 1);
			} else if (a.getSource() == buttons[0][2]) {
				makeMove(0, 2, 2);
			} else if (a.getSource() == buttons[1][0]) {
				makeMove(1, 0, 3);
			} else if (a.getSource() == buttons[1][1]) {
				makeMove(1, 1, 4);
			} else if (a.getSource() == buttons[1][2]) {
				makeMove(1, 2, 5);
			} else if (a.getSource() == buttons[2][0]) {
				makeMove(2, 0, 6);
			} else if (a.getSource() == buttons[2][1]) {
				makeMove(2, 1, 7);
			} else if (a.getSource() == buttons[2][2]) {
				makeMove(2, 2, 8);
			} else if (a.getSource() == reset) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						new TicTacToe();
						buttons[i][j].setText("");
						buttons[i][j].setEnabled(true);
					}
				}
			}
		}

		/**
		 * Method used to make move in Tic Tac Toe game
		 */
		private void makeMove(int i, int j, int k) {
			buttons[i][j].setText("X");
			buttons[i][j].setEnabled(false);
			moveCounter--;
			int result = control.makeMove(k);
			JOptionPane.showMessageDialog(frame, "Total number of nodes evaluated by computer: " + TicTacToe.counter);
			TicTacToe.counter = 0;
			if (result == -101) {
				JOptionPane.showMessageDialog(frame, "Result: " + result + "\nYou win !!");
			} else if (result == 100) {
				JOptionPane.showMessageDialog(frame, "Result: " + result + "\nGame Drawn !!");
			} else if (result == 101) {
				JOptionPane.showMessageDialog(frame, "Result: " + result + "\nComputer Wins !!");
			} else {
				int indexes[] = getBoardindex(result);
				buttons[indexes[0]][indexes[1]].setText("O");
				buttons[indexes[0]][indexes[1]].setEnabled(false);
				moveCounter--;
			}
		}

		/**
		 * Method used to get the double index from single index
		 */
		private int[] getBoardindex(int result) {
			if (result == 0) {
				return new int[] { 0, 0 };
			} else if (result == 1) {
				return new int[] { 0, 1 };
			} else if (result == 2) {
				return new int[] { 0, 2 };
			} else if (result == 3) {
				return new int[] { 1, 0 };
			} else if (result == 4) {
				return new int[] { 1, 1 };
			} else if (result == 5) {
				return new int[] { 1, 2 };
			} else if (result == 6) {
				return new int[] { 2, 0 };
			} else if (result == 7) {
				return new int[] { 2, 1 };
			} else if (result == 8) {
				return new int[] { 2, 2 };
			}
			return null;
		}
	}

}