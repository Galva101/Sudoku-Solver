public class SudokuSolver {
    	public  static int[][] sudoku1 = { //Here are seven sudokus to test the program on
            {9, 0, 0, 1, 0, 0, 0, 0, 5}, 
            {0, 0, 5, 0, 9, 0, 2, 0, 1}, 
            {8, 0, 0, 0, 4, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 8, 0, 0, 0, 0}, 
            {0, 0, 0, 7, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 2, 6, 0, 0, 9}, 
            {2, 0, 0, 3, 0, 0, 0, 0, 6}, 
            {0, 0, 0, 2, 0, 0, 9, 0, 0}, 
            {0, 0, 1, 9, 0, 4, 5, 7, 0}, 
   };

    public static int[][] sudoku2 = {
            {3, 1, 6, 5, 7, 8, 4, 9, 2}, 
            {5, 0, 0, 1, 3, 4, 7, 6, 8}, 
            {4, 0, 7, 0, 2, 9, 5, 3, 1}, 
            {2, 6, 3, 0, 1, 5, 9, 0, 7}, 
            {9, 7, 4, 8, 6, 0, 1, 2, 5}, 
            {8, 5, 1, 7, 0, 2, 6, 4, 3}, 
            {1, 0, 8, 0, 4, 7, 2, 0, 6}, 
            {6, 9, 2, 3, 0 , 1, 8, 7, 4}, 
            {0, 4, 5, 0, 8, 6, 0, 1, 0}
	};

    public static int[][] sudoku3= {
            {3, 0, 6, 5, 0, 8, 4, 0, 0}, 
            {5, 2, 0, 0, 0, 0, 0, 0, 0}, 
            {0, 8, 7, 0, 0, 0, 0, 3, 1}, 
            {0, 0, 3, 0, 1, 0, 0, 8, 0}, 
            {9, 0, 0, 8, 6, 3, 0, 0, 5}, 
            {0, 5, 0, 0, 9, 0, 6, 0, 0}, 
            {1, 3, 0, 0, 0, 0, 2, 5, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 7, 4}, 
            {0, 0, 5, 2, 0, 6, 3, 0, 0}
   };

    public static int[][] sudoku4 = {
            {5, 8, 0, 2, 0, 0, 4, 7, 0}, 
            {0, 2, 0, 0, 0, 0, 0, 3, 0}, 
            {0, 3, 0, 0, 5, 4, 0, 0, 0}, 
            {0, 0, 0, 5, 6, 0, 0, 0, 0}, 
            {0, 0, 7, 0, 3, 0, 9, 0, 0}, 
            {0, 0, 0, 0, 9, 1, 0, 0, 0}, 
            {0, 0, 0, 8, 2, 0, 0, 6, 0}, 
            {0, 7, 0, 0, 0, 0, 0, 8, 0}, 
            {0, 9, 4, 0, 0, 6, 0, 1, 5}
	};

    public static int[][] sudoku5 = {
            {1, 0, 0, 6, 0, 0, 0, 0, 0}, 
            {0, 2, 0, 0, 0, 0, 9, 0, 0}, 
            {0, 0, 3, 0, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 4, 0, 0, 0, 1, 0}, 
            {0, 4, 0, 0, 5, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0, 6, 0, 9, 0}, 
            {0, 0, 2, 0, 0, 0, 7, 0, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 8, 0}, 
            {3, 0, 0, 5, 0, 0, 0, 0, 9}
	};

     public static int[][] sudoku6 =  {
             {0, 0, 8, 1, 0, 0, 7, 0, 0}, 
             {0, 3, 0, 0, 8, 0, 0, 0, 0}, 
             {2, 0, 4, 0, 0, 0, 0, 0, 9}, 
             {0, 0, 0, 3, 5, 0, 0, 0, 0}, 
             {0, 7, 0, 0, 0, 8, 0, 0, 0}, 
             {0, 0, 0, 0, 0, 9, 8, 4, 0}, 
             {0, 0, 0, 0, 2, 0, 0, 0, 6}, 
             {0, 0, 0, 9, 0, 1, 0, 3, 0}, 
             {9, 0, 2, 0, 0, 7, 0, 0, 0}, 
    };

     public static int[][]  sudoku7 = {
            {0, 0, 6, 8, 0, 0, 0, 2, 0}, 
            {0, 0, 0, 0, 0, 0, 5, 0, 0}, 
            {4, 0, 5, 0, 7, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 8, 2, 9, 0, 0}, 
            {7, 0, 2, 1, 0, 0, 8, 0, 0}, 
            {0, 0, 0, 6, 0, 0, 0, 5, 3}, 
            {9, 0, 0, 0, 0, 1, 7, 0, 0}, 
            {0, 0, 0, 5, 0, 0, 0, 0, 0}, 
            {6, 0, 4, 0, 0, 0, 0, 0, 2}, 
    };

    public static boolean possible (int y , int x, int n, int[][] s){ //this method returns a boolean value to indicate if a number can be set on a field at coordinates x and y
        for(int i=0; i<9; i++){ //first we check both axes
            if( s[y][i]==n){
                return false;
            }
        }
        for(int i=0; i<9; i++){
            if( s[i][x]==n){
                return false;
            }
        }

        int yBlock = (y/3)*3; //here we calculate the block that we are probing, and check all cells within it
        int xBlock = (x/3)*3;

        for(int i =0; i<3;i++){
            for(int j=0; j<3; j++){
                if(s[yBlock+i][xBlock+j]==n){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean verifySudoku(int[][] s ){ //this simple verify method iterates over the field to check if there are no zeroes left
        for(int y =0; y<9;y++){
            for( int x=0; x<9; x++){
                if(s[y][x]==0){
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] solve(int[][] s){
        /* This method works by iterating over the field, until it encounters a zero, then it tries the
        * 9 numbers that could be there, for each it attempts it, then it passes the new field to the method again, should there
        * at some point be an error, it sets the attempted cell back to zero and abandons this call by returning the unaltered field (backtracking).
        * If the verification succeeds, we return the current field, as this is the solution.*/
        for(int y =0; y<9;y++){
            for( int x=0; x<9; x++){
                if(s[y][x]==0){
                    for(int n=1; n<10; n++){
                        if(possible(y,x,n,s)){
                            s[y][x]=n;
//                            printSudoku(s);
                            solve(s);
                            if(verifySudoku(s)){
                                return s;
                            }else{
                                s[y][x]=0;
                            }
                        }
                    }
                    return s;
                }
            }
        }
    return s;
    }

    public static  void printSudoku(int[][] s){
        for(int y =0; y<9;y++){
            for( int x=0; x<9; x++){
                System.out.print(s[y][x]+", ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {

        int[][] solved = solve(sudoku1);
        printSudoku(solved);

    }
}
