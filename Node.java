package com.ttt;

public class Node {

	String[] state;
	int score;
	int depth;

	public Node(String[] state, int score, int depth) {
		this.state = state;
		this.score = score;
		this.depth = depth;
	}

	public void updateMatrix(String[] state) {
		this.state = state;
	}

	public int getScore() {
		return score;
	}

	public int getBoardIndex() {
		for (int i = 0; i < 16; i++)
			if (state[i].equals("o"))
				return i;
		return -1;
	}
}
