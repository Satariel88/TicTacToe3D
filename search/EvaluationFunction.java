package project.core.search;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EvaluationFunction {
	
	private static EvaluationFunction instance;
	private List<Integer> ballsInLine = new LinkedList<Integer>(Arrays.asList(0,0,0,0,0));
	
	protected EvaluationFunction() {
		
	}
	
	public static EvaluationFunction getInstance() {
		
		if (instance == null)
			instance = new EvaluationFunction();
		
		return instance;
	}
	
	public int evaluate(Node node) {
		
		List<Integer> boards = node.getMyBoards();
		checkRows(boards, node);
		checkCols(boards, node);
		checkDiags(boards, node);
		checkVerticals(boards, node);
		check3DRows(boards, node);
		check3DCols(boards, node);
		check3DDiags(boards, node);
		int result = ballsInLine.get(1) + ballsInLine.get(2)*5 + ballsInLine.get(3)*10 + ballsInLine.get(4)*100;
		ballsInLine = Arrays.asList(0,0,0,0,0);
		return result;
	}
	
	private void checkRows(List<Integer> boards, Node node) {
		
		int result = 0;
		int prevBall = 0;
		
		for (int  i = 0; i < boards.size()/4; i++) {
			for (int j = 0; j < 4; j++){
				
				int ball = boards.get(i*4 + j);
				if (ball != 0){
					
					if (prevBall == 0)
						prevBall = ball;
					
					else if (prevBall != ball) {
						result = 0;
						break;
					}

					result -= ball;
				}

				if (Math.abs(result) == 4)
					node.setIsTerminal(true);
			}
			
			ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
			prevBall = 0;
			result = 0;
		}
	}
	
	private void checkCols(List<Integer> boards, Node node) {
	
		int result = 0;
		int prevBall = 0;
		
		for (int board = 0; board < 4; board++) {
			for (int  i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {

					int ball = boards.get(board*boards.size()/4 + i + j*4);
					if (ball != 0) {
						
						if (prevBall == 0)
							prevBall = ball;
						
						else if (prevBall != ball) {
							result = 0;
							break;
						}
	
						result -= ball;
					}

					if (Math.abs(result) == 4)
						node.setIsTerminal(true);
				}
				
				ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
				prevBall = 0;
				result = 0;
			}
			
			prevBall = 0;
			result = 0;
		}
	}
	
	private void checkDiags(List<Integer> boards, Node node) {

		int result = 0;
		int prevBall = 0;
	
		for (int board = 0; board < 4; board++) {
			for (int j = 0; j < 4; j++) {
				
				int ball = boards.get(board * boards.size()/4 + j + j*4);
				if (ball != 0){
				
					if (prevBall == 0)
						prevBall = ball;
				
					else if (prevBall != ball) {
						result = 0;
						break;
					}
	
					result -= ball;
				}
			}
	
			if (Math.abs(result) == 4)
				node.setIsTerminal(true);

			ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
			prevBall = 0;
			result = 0;
		}

		for (int board = 0; board < 4; board++) {
			for (int j = 1; j < 5; j++) {

				int ball = boards.get(board * boards.size()/4 + j*4 - j);
				if (ball != 0) {
				
					if (prevBall == 0)
						prevBall = ball;
				
					else if (prevBall != ball) {
						result = 0;
						break;
					}
	
					result -= ball;
					
				}
			}
			
			if (Math.abs(result) == 4)
				node.setIsTerminal(true);

			ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
			prevBall = 0;
			result = 0;
		}
	}
	
	private void checkVerticals(List<Integer> boards, Node node) {

			int result = 0;
			int prevBall = 0;
			
			for (int i = 0; i < boards.size()/4; i++) {
				for (int j = 0; j < 4; j++) {
				
					int ball = boards.get(i + j * boards.size()/4);
					if (ball != 0){
				
						if (prevBall == 0)
							prevBall = ball;
				
						else if (prevBall != ball) {
							result = 0;
							break;
						}

						result -= ball;
					}

					if (Math.abs(result) == 4)
						node.setIsTerminal(true);
				}

				ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
				prevBall = 0;
				result = 0;
			}
		}

	private void check3DDiags(List<Integer> boards, Node node) {

		int result = 0;
		int prevBall = 0;
		
		//from [0][0][0] to [3][3][3]
		for (int j = 0; j < 4; j++) {
			
			int ball = boards.get(j + j*4 + j*boards.size()/4);
			if (ball != 0) {
			
				if (prevBall == 0)
					prevBall = ball;
			
				else if (prevBall != ball) {
					result = 0;
					break;
				}
	
				result -= ball;
			}
		}
	
		if (Math.abs(result) == 4)
			node.setIsTerminal(true);

		ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
		prevBall = 0;
		result = 0;
		
		//from [0][0][3] to [3][3][0]
		for (int j = 0; j < 4; j++){

			int ball = boards.get(3 + j*(boards.size()/4) + j*4 - j);
			if (ball != 0) {
			
				if (prevBall == 0)
					prevBall = ball;
			
				else if (prevBall != ball) {
					result = 0;
					break;
				}

				result -= ball;
			}
		}

		if (Math.abs(result) == 4)
			node.setIsTerminal(true);

		ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
		prevBall = 0;
		result = 0;
		
		//from [3][0][0] to [0][3][3]
		for (int j = 0; j < 4; j++) {

			int ball = boards.get((boards.size()/4 - 1) + j*(boards.size()/4) - j*4 - j);
			if (ball != 0){
			
				if (prevBall == 0)
					prevBall = ball;
			
				else if (prevBall != ball) {
					result = 0;
					break;
				}

				result -= ball;
			}
		}

		if (Math.abs(result) == 4)
			node.setIsTerminal(true);

		ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
		prevBall = 0;
		result = 0;
		
		//from [3][0][3] to [0][3][0]
		for (int j = 0; j < 4; j++) {

			int ball = boards.get(boards.size()/4 + j*(boards.size()/4) - j*4 + j - 4);
			if (ball != 0){
			
				if (prevBall == 0)
					prevBall = ball;
			
				else if (prevBall != ball) {
					result = 0;
					break;
				}

				result -= ball;
			}
		}

		if (Math.abs(result) == 4)
			node.setIsTerminal(true);

		ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
	}
	
	private void check3DCols(List<Integer> boards, Node node) {
		
		int result = 0;
		int prevBall = 0;
		
		//starting in board 0 and ending in board 3
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
			
				int ball = boards.get(j * (boards.size()/4) + j*4 + i);
				if (ball != 0){
			
					if (prevBall == 0)
						prevBall = ball;
			
					else if (prevBall != ball) {
						result = 0;
						break;
					}

					result -= ball;
				}

				if (Math.abs(result) == 4)
					node.setIsTerminal(true);
			}

			ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
			prevBall = 0;
			result = 0;
		}

		//starting in board 3 and ending in board 0
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
			
				int ball = boards.get(boards.size()/4 + j* boards.size()/4 - 4 - j*4 + i);
				if (ball != 0){
			
					if (prevBall == 0)
						prevBall = ball;
			
					else if (prevBall != ball) {
						result = 0;
						break;
					}

					result -= ball;
				}

				if (Math.abs(result) == 4)
					node.setIsTerminal(true);
			}

			ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
			prevBall = 0;
			result = 0;
		}
	}
	
	private void check3DRows(List<Integer> boards, Node node) {

		int result = 0;
		int prevBall = 0;
		
		//starting in board 0 and ending in board 3
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				int ball = boards.get(j * boards.size()/4 + j + i);
				if (ball != 0){
			
					if (prevBall == 0)
						prevBall = ball;
			
					else if (prevBall != ball) {
						result = 0;
						break;
					}

					result -= ball;
				}

				if (Math.abs(result) == 4)
					node.setIsTerminal(true);
			}

			ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
			prevBall = 0;
			result = 0;
		}
		
		//starting in board 3 and ending in board 0
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {

				int ball = boards.get(3 + j*((boards.size()/4)-1) );
				if (ball != 0) {
			
					if (prevBall == 0)
						prevBall = ball;
			
					else if (prevBall != ball) {
						result = 0;
						break;
					}

					result -= ball;
				}

				if (Math.abs(result) == 4)
					node.setIsTerminal(true);
			}
			
			ballsInLine.set(Math.abs(result), ballsInLine.get(Math.abs(result))+(result>0? 1 : -1));
			prevBall = 0;
			result = 0;
		}
	}
}
