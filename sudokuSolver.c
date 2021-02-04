#include <stdio.h>

void printSudoku(int s[9][9]) {
    for (int y = 0; y < 9; y++) {
        for (int x = 0; x < 9; x++) {
            printf("%d, ", s[y][x]);
        }
        printf("\n");
    }
    printf("\n");
}

int verifySudoku(int s[9][9]) {
    for (int y = 0; y < 9; y++) {
        for (int x = 0; x < 9; x++) {
            if (s[y][x] == 0) {
                return 0;
            }
        }
    }
    return 1; //0 and 1 as boolean substitutes
}

int possible(int y, int x, int n, int s[9][9]) {
    for (int i = 0; i < 9; i++) { //first we check both axes
        if (s[y][i] == n) {
            return 0;
        }
    }
    for (int i = 0; i < 9; i++) {
        if (s[i][x] == n) {
            return 0;
        }
    }
    int yBlock = (y / 3) * 3; //here we calculate the block that we are probing, and check all cells within it
    int xBlock = (x / 3) * 3;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (s[yBlock + i][xBlock + j] == n) {
                return 0;
            }
        }
    }
    return 1;
}

int **solve(int s[9][9]) {
    /* This method works by iterating over the field, until it encounters a zero, then it tries the
        * 9 numbers that could be there, for each it attempts it, then it passes the new field to the method again, should there
        * at some point be an error, it sets the attempted cell back to zero and abandons this call by returning the unaltered field (backtracking).
        * If the verification succeeds, we return the current field, as this is the solution.*/
    for (int y = 0; y < 9; y++) {
        for (int x = 0; x < 9; x++) {
            if (s[y][x] == 0) {
                for (int n = 1; n < 10; n++) {
                    if (possible(y, x, n, s)) {
                        s[y][x] = n;
//                        printSudoku(s);
                        solve(s);
                        if (verifySudoku(s)) {
                            return s;
                        } else {
                            s[y][x] = 0;
                        }
                    }
                }
                return s;
            }
        }
    }
    return s;
}

int main() {
        int sudoku1[9][9] = {  //Here are seven sudokus to test the program on
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

    int sudoku2[9][9] = {
            {3, 1, 6, 5, 7, 8, 4, 9, 2},
            {5, 0, 0, 1, 3, 4, 7, 6, 8},
            {4, 0, 7, 0, 2, 9, 5, 3, 1},
            {2, 6, 3, 0, 1, 5, 9, 0, 7},
            {9, 7, 4, 8, 6, 0, 1, 2, 5},
            {8, 5, 1, 7, 0, 2, 6, 4, 3},
            {1, 0, 8, 0, 4, 7, 2, 0, 6},
            {6, 9, 2, 3, 0, 1, 8, 7, 4},
            {0, 4, 5, 0, 8, 6, 0, 1, 0}
	};

    int sudoku3[9][9] = {
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

    int sudoku4[9][9] = {
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

    int sudoku5[9][9] = {
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

    int sudoku6[9][9] = {
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

    int sudoku7[9][9] = {
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

    int **t;
    t = solve(sudoku1);
    printSudoku(t);
}
