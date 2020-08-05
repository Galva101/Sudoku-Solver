package PX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.JOptionPane;

class SudokuTile {

	public boolean set = false;
	public int value = 0;

	public ArrayList<Integer> numberlist = new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9}));

	public SudokuTile(boolean isSet, int valueToSet) {
		this.set= isSet;
		this.value=valueToSet;
		this.numberlist.clear();
	}

	public SudokuTile() {
		this.set= false;
		this.value=0;
	}

	public void removeValueFromList(int value) {
		this.numberlist.remove(value);
	}

	public void setValue(int value) {
		this.set = true;
		this.value=value;
		this.numberlist.clear();
	}

}

public class Sudoku {

	public static ArrayList<Integer> generateCompliment(int x){

		ArrayList<Integer> comp = new ArrayList<Integer>();
		for(int i=1; i<=9;i++) {
			if(i!=x) {
				comp.add(i);
			}
		}
		return comp;

	}

	public static void checkAndReduceNumber (SudokuTile[][] sudoku) {
		for(int y=0; y<9; y++) { 
			for(int x=0; x<9; x++) { //select start tile

				//				System.out.println("start is " +sudoku[y][x].value);

				//Horizontal check

				if(sudoku[y][x].numberlist.size()!=0) { //if tile has not yet been set

					for(int i =0; i<9; i++) {// check left/right
						if(i!=x && sudoku[y][i].set) { // if not looking at the same tile and the value is not 0
							//							System.out.println("looking at "+ sudoku[y][i].value);

							int targetvalue = sudoku[y][i].value;
							//							System.out.println("targetvalue is "+targetvalue);

							if(sudoku[y][x].numberlist.contains(targetvalue)) {

								ArrayList<Integer> compliment = generateCompliment(targetvalue);
								sudoku[y][x].numberlist.retainAll(compliment);

							}
						}
					}

					//					System.out.println("horizontal check over");


					//Vertical check


					for(int j =0; j<9; j++) {// cup/down
						if(j!=y && sudoku[j][x].set) { 							
							int targetvalue = sudoku[j][x].value;

							if(sudoku[y][x].numberlist.contains(targetvalue)) {

								ArrayList<Integer> compliment = generateCompliment(targetvalue);
								sudoku[y][x].numberlist.retainAll(compliment);
							}
						}
					} 
					//					System.out.println("vertical check over");


					//Block Check

					//select the right block to look at the 9 values and if they have been set already
					int blockX =0;
					int blockY=0;

					if(x<=2) {
						blockX=0;
					}else if (x<=5) {
						blockX=3;
					}else {
						blockX=6;
					}

					if(y<=2) {
						blockY=0;
					}else if(y<=5) {
						blockY=3;
					}else {
						blockY=6;
					}


					for(int squareY=blockY; squareY<blockY+3; squareY++) {
						for(int squareX=blockX; squareX<blockX+3; squareX++) {

							if(!( squareY==y&&squareX==x )) { //correct, only looking at other tiles in the same square

								//								System.out.println(""+y+" "+x+" square check with "+squareY+ " "+ squareX);

								if(sudoku[squareY][squareX].set) {
									int targetvalue = sudoku[squareY][squareX].value;

									if(sudoku[y][x].numberlist.contains(targetvalue)) {

										ArrayList<Integer> compliment = generateCompliment(targetvalue);
										sudoku[y][x].numberlist.retainAll(compliment);

									}

								}


							}//end if

						}
					}//end simple block check


					//more complicated test:

					boolean valueFound= false;
					for(int i = 0; i<sudoku[y][x].numberlist.size() && !valueFound;i++) { //iterate over every possible value for the x/y tile
						
						int valueToCheck = sudoku[y][x].numberlist.get(i);
						boolean otherTileSharesValue=false;

						for(int squareY=blockY; squareY<blockY+3; squareY++) {
							for(int squareX=blockX; squareX<blockX+3; squareX++) {

								if(!( squareY==y&&squareX==x )) { //if not looking at the same tile
									
									for(int j =0; j<sudoku[squareY][squareX].numberlist.size(); j++) {//look at all potential values of target in block
										if(sudoku[squareY][squareX].numberlist.get(j)==valueToCheck) {
											otherTileSharesValue=true;
										}
									}

								}

							}
						}//block check complete
						if(!otherTileSharesValue) {//no other tile can be this specific value, so this x/y tile must have it
							valueFound=true;
							sudoku[y][x].setValue(valueToCheck);
						}
					}


					//update tested tile

					if(sudoku[y][x].numberlist.size()==1){
						int valueToBeSet = sudoku[y][x].numberlist.get(0); //only one value left
						sudoku[y][x].setValue(valueToBeSet);
					}

				}//end if


			}
		}

	}

	public static void recursiveSolve(SudokuTile[][] original, SudokuTile[][] copy){
		if( verifySudoku(copy)){ //copy is a valid sudoku solution, stop recursion and overwrite the original using references
			original = copy;
			System.out.println("found solution");

		}else{
			for(int x=0; x<9; x++) {  //iterate over all position of the new copy 
				for(int y=0; y<9; y++) {

					if ( !copy[x][y].set) { //still a value missing in the new copied sudoku

						for(int i = 0; i<copy[x][y].numberlist.size(); i++) { //iterate over every possible value for the empty tile at this position
							
							int number = copy[x][y].numberlist.get(i);
							System.out.println("iterating over numberlist of "+x +" "+y +" : " + copy[x][y].numberlist +" and inserting " +number);
							
							SudokuTile[][] n =  new SudokuTile[9][9];
							initialiseSudoku(n);
							copySudoku(copy, n); //we now work on a new sudoku "n" to avoid reference conflicts
							n[x][y].setValue( number ); //set the empty slot of the new n-sudoku to the i-th value of possible numbers in the copy-sudoku
							
							printSudokuToConsole(copy);
							// System.out.println(x + " " +y +", in " + copy[x][y].numberlist);
							// System.out.println("and adding " + copy[x][y].numberlist.get(i));
							printSudokuToConsole(n);
							System.out.println("n is now a " + verifySudoku(n) + " sudoku \n\n");

							recursiveSolve(original, n); //pass that new n-sudoku into a recursive call, to continue filling the values

						}
					}				
				}
			}
		}
	}

	public static boolean verifySudoku(SudokuTile[][] s){
		boolean allSet = true;
		for(int i=0; i<9; i++) { 
			for(int j=0; j<9; j++) {
				if ( !s[i][j].set) {
					allSet = false;
				}				
			}
		}

		for( int row = 0; row<9; row ++){ //verify all rows
			HashSet<Integer> h = new HashSet<Integer>();
			for(int i=0; i<9; i++) { 
				h.add( s[row][i].value);
			}
			h.remove(0);
			if(h.size()!=9){
				allSet = false;
			}
			// System.out.println(h);
		}

		for( int col = 0; col<9; col ++){ //verify all rows
			HashSet<Integer> h = new HashSet<Integer>();
			for(int i=0; i<9; i++) { 
				h.add( s[i][col].value);
			}
			h.remove(0);
			if(h.size()!=9){
				allSet = false;
			}
			// System.out.println(h);
		}

		//verify blocks
		for( int y = 0; y<9; y+=3){
			for( int x = 0; x<9; x+=3){

				HashSet<Integer> h = new HashSet<Integer>();
				// System.out.println("checking block " +y +" "+x);
				for( int i =0; i<3; i++){
					for( int j=0; j<3; j++){
						h.add( s[i][j].value);
					}
				}
				h.remove(0);
				if(h.size()!=9){
					allSet = false;
				}

			}
		}
		return allSet;
	}

	public static void buildSudoku(int[][] template, SudokuTile[][] target) { //copy sudoku int array values if they are not zero

		for(int i=0; i<9; i++) { 
			for(int j=0; j<9; j++) {
				if(template[i][j]!=0){
					target[i][j].setValue(template[i][j]);
				}				
			}
		}
	}

	public static void initialiseSudoku( SudokuTile[][] s ){
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				s[i][j] = new SudokuTile();
			}
		}
	}
	
	public static void copySudoku( SudokuTile[][] source,  SudokuTile[][] target ) {		
		for( int i =0; i<9; i++){
			for( int j =0; j<9; j++){
				target[i][j].setValue(source[i][j].value);
			}
		}
	}

	public static void printSudokuToConsole(SudokuTile[][] sudoku) { /*print the sudoku state, if 3 columns or lines have been printed, 
															a separator is included*/
		System.out.println("\n-----------------------");
		for(int i=0; i<9; i++) { 

			for(int j=0; j<9; j++) {
				System.out.print(sudoku[i][j].value+" "); 
				if((j+1)%3==0) {
					System.out.print("| ");
				}
			}
			if((i+1)%3==0) {
				System.out.println("\n-----------------------");
			}else {
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//		Window window = new Window("Chaos", 800, 800);
		//		window.open();
		//
		//		while (window.isOpen()) {
		//
		//			window.setColor(0,0,0);//schwarz
		//			window.fillRect(0, 0, window.getWidth(), window.getHeight() );
		//
		//			window.setColor(255,255,255);// white
		//			window.drawString("00:00", window.getWidth()/3.75, window.getHeight()/1.8);
		//
		//			window.refresh();
		//		}

		//		Scanner sc = new Scanner(" ");
		//		sc.useDelimiter(":|\\s+");
		//		int h = sc.nextInt();
		//		int m = sc.nextInt();
		//		int s = sc.nextInt();

		// int[][] sudoku =  { //incomplete
		// 		{9,0,0,1,0,0,0,0,5},
		// 		{0,0,5,0,9,0,2,0,1},
		// 		{8,0,0,0,4,0,0,0,0},
		// 		{0,0,0,0,8,0,0,0,0},
		// 		{0,0,0,7,0,0,0,0,0},
		// 		{0,0,0,0,2,6,0,0,9},
		// 		{2,0,0,3,0,0,0,0,6},
		// 		{0,0,0,2,0,0,9,0,0},
		// 		{0,0,1,9,0,4,5,7,0},
		// };

		// int[][] sudoku =  { // complete
		// 		{0,0,0,5,0,6,3,0,8},
		// 		{0,0,0,0,0,0,0,5,0},
		// 		{4,0,0,3,0,0,1,0,0},
		// 		{0,1,0,0,8,5,2,0,0},
		// 		{0,5,0,0,0,7,6,3,0},
		// 		{0,9,0,0,1,0,0,7,0},
		// 		{2,0,1,0,6,0,0,0,0},
		// 		{0,0,8,0,0,0,0,0,0},
		// 		{0,7,3,8,0,0,0,0,0},
		// };
		
		int[][] sudoku =  { //incomplete
				{0,0,8,1,0,0,7,0,0},
				{0,3,0,0,8,0,0,0,0},
				{2,0,4,0,0,0,0,0,9},
				{0,0,0,3,5,0,0,0,0},
				{0,7,0,0,0,8,0,0,0},
				{0,0,0,0,0,9,8,4,0},
				{0,0,0,0,2,0,0,0,6},
				{0,0,0,9,0,1,0,3,0},
				{9,0,2,0,0,7,0,0,0},
		};
		
//		int[][] sudoku =  { //template
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//		};
		
// 		int[][] sudoku =  { // expert website, incomplete solution
// 		{0,0,6,8,0,0,0,2,0},
// 		{0,0,0,0,0,0,5,0,0},
// 		{4,0,5,0,7,0,0,0,0},
// 		{0,0,0,0,8,2,9,0,0},
// 		{7,0,2,1,0,0,8,0,0},
// 		{0,0,0,6,0,0,0,5,3},
// 		{9,0,0,0,0,1,7,0,0},
// 		{0,0,0,5,0,0,0,0,0},
// 		{6,0,4,0,0,0,0,0,2},
// };
		
//		int[][] sudoku =  { //hard website, complete
//		{0,0,7,0,0,0,0,0,4},
//		{6,0,5,0,2,0,0,0,0},
//		{0,0,4,0,0,1,3,0,9},
//		{0,0,9,0,0,5,2,0,7},
//		{3,0,0,0,9,2,0,0,0},
//		{0,0,0,0,0,7,6,0,0},
//		{0,0,6,0,0,3,0,0,0},
//		{0,0,0,5,8,0,0,0,0},
//		{4,2,0,0,0,0,0,0,0},
//};
		
//		int[][] sudoku =  { //easy website, complete
//				{0,8,1,6,0,2,0,0,0},
//				{0,2,7,0,0,0,6,8,5},
//				{0,0,0,9,0,0,0,0,2},
//				{0,8,0,8,0,0,0,2,0},
//				{7,0,0,0,9,0,0,0,4},
//				{2,6,8,4,3,0,5,9,7},
//				{0,4,0,6,0,0,0,0,0},
//				{0,0,0,0,2,4,7,0,1},
//				{5,7,6,0,0,0,2,0,9},
//		};


		SudokuTile[][] table = new SudokuTile[9][9];

		initialiseSudoku(table);

		buildSudoku(sudoku, table);		
		printSudokuToConsole(table);
		System.out.println();

		// SudokuTile[][] x = new SudokuTile[9][9];
		// initialiseSudoku(x);
		// copySudoku(table, x);
		// x[8][8].setValue(90);
		// printSudokuToConsole(x);



		for(int i =0; i<100; i++) {
			checkAndReduceNumber(table);

			//			printSudokuToConsole(table);
			//			System.out.println();

		}

		// table[8][8].setValue(9);

		System.out.println("valid sudoku: " +verifySudoku(table));

		recursiveSolve(table, table);
		printSudokuToConsole(table);
		

		// System.out.println(table[4][4].numberlist);
	}

}
