package my.sudoku.main;

import java.util.Random;

public class SudokuLevel {
	
	final static int EASY = 1;
	final static int MEDIUM = 2;
	final static int DIFFICULT = 3;
	final static int EXTREMELY = 4;
	
	private boolean isSymmetrical;
	private int level;
	
	public SudokuLevel(int level, boolean isSymmetrical) {
		this.setLevel(level);
		this.setSymmetrical(isSymmetrical);
	}
	
	/**
	 * @return num
	 * 			  number of erase cells
	 */
	
	public int getNumberOfEraseCells(){
		switch (getLevel()){
			case EASY: 
				return new Random().nextInt(6) + 40;
			case MEDIUM:
				return new Random().nextInt(4) + 46;
			case DIFFICULT:
				return new Random().nextInt(4) + 50;
			case EXTREMELY:
				return new Random().nextInt(5) + 54;
			default:
				return new Random().nextInt(4) + 46;
		}	
	}
	
	public boolean isOneSolutionMustBe(){
		if (getLevel() == EXTREMELY){
			return false;
		}
		
		return true;
	}
	
	public static int getNumberOfEraseCells(int level){
		switch (level){
			case EASY: 
				return new Random().nextInt(6) + 40;
			case MEDIUM:
				return new Random().nextInt(4) + 46;
			case DIFFICULT:
				return new Random().nextInt(4) + 50;
			case EXTREMELY:
				return new Random().nextInt(5) + 54;
			default:
				return new Random().nextInt(4) + 46;
		}	
	}
	
	public static boolean isOneSolutionMustBe(int level){
		if (level == EXTREMELY){
			return false;
		}
		
		return true;
	}

	public boolean isSymmetrical() {
		return isSymmetrical;
	}

	public void setSymmetrical(boolean isSymmetrical) {
		this.isSymmetrical = isSymmetrical;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
