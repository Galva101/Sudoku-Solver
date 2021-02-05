def possible(y, x, n, s): #this method returns a boolean value to indicate if a number can be set on a field at coordinates x and y
    for i in range(0, 9): #first we check both axes
        if s[y][i] == n:
            return False
    for i in range(0, 9):
        if s[i][x] == n:
            return False

    xBlock = (x // 3) * 3 #here we calculate the block that we are probing, and check all cells within it
    yBlock = (y // 3) * 3

    for i in range(0, 3):
        for j in range(0, 3):
            if s[yBlock + i][xBlock + j] == n:
                return False

    return True


def verifySudoku(s): #this simple verify method iterates over the field to check if there are no zeroes left
    for y in range(0, 9):
        for x in range(0, 9):
            if s[y][x] == 0:
                return False
    return True


def solve(s):
    # This method works by iterating over the field, until it encounters a zero, then it tries the
    # 9 numbers that could be there, for each it attempts it, then it passes the new field to the method again, should there
    # at some point be an error, it sets the attempted cell back to zero and abandons this call by returning the unaltered field (backtracking).
    # If the verification succeeds, we return the current field, as this is the solution.
    for y in range(0, 9):
        for x in range(0, 9):
            if s[y][x] == 0:
                for n in range(1, 10):
                    if possible(y, x, n, s):
                        s[y][x] = n
                        solve(s)
                        if verifySudoku(s):
                            return s
                        else:
                            s[y][x] = 0
                return s
    return s


def printSudoku(s):
    for row in s:
        for x in row:
            print(str(x) + ", ", end='')
        print()
    print("\n\n")


sudoku1 = [
    [9, 0, 0, 1, 0, 0, 0, 0, 5],
    [0, 0, 5, 0, 9, 0, 2, 0, 1],
    [8, 0, 0, 0, 4, 0, 0, 0, 0],
    [0, 0, 0, 0, 8, 0, 0, 0, 0],
    [0, 0, 0, 7, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 2, 6, 0, 0, 9],
    [2, 0, 0, 3, 0, 0, 0, 0, 6],
    [0, 0, 0, 2, 0, 0, 9, 0, 0],
    [0, 0, 1, 9, 0, 4, 5, 7, 0],
];

sudoku2 = [
    [3, 1, 6, 5, 7, 8, 4, 9, 2],
    [5, 0, 0, 1, 3, 4, 7, 6, 8],
    [4, 0, 7, 0, 2, 9, 5, 3, 1],
    [2, 6, 3, 0, 1, 5, 9, 0, 7],
    [9, 7, 4, 8, 6, 0, 1, 2, 5],
    [8, 5, 1, 7, 0, 2, 6, 4, 3],
    [1, 0, 8, 0, 4, 7, 2, 0, 6],
    [6, 9, 2, 3, 0, 1, 8, 7, 4],
    [0, 4, 5, 0, 8, 6, 0, 1, 0]
];

sudoku3 = [
    [3, 0, 6, 5, 0, 8, 4, 0, 0],
    [5, 2, 0, 0, 0, 0, 0, 0, 0],
    [0, 8, 7, 0, 0, 0, 0, 3, 1],
    [0, 0, 3, 0, 1, 0, 0, 8, 0],
    [9, 0, 0, 8, 6, 3, 0, 0, 5],
    [0, 5, 0, 0, 9, 0, 6, 0, 0],
    [1, 3, 0, 0, 0, 0, 2, 5, 0],
    [0, 0, 0, 0, 0, 0, 0, 7, 4],
    [0, 0, 5, 2, 0, 6, 3, 0, 0]
];

sudoku4 = [
    [5, 8, 0, 2, 0, 0, 4, 7, 0],
    [0, 2, 0, 0, 0, 0, 0, 3, 0],
    [0, 3, 0, 0, 5, 4, 0, 0, 0],
    [0, 0, 0, 5, 6, 0, 0, 0, 0],
    [0, 0, 7, 0, 3, 0, 9, 0, 0],
    [0, 0, 0, 0, 9, 1, 0, 0, 0],
    [0, 0, 0, 8, 2, 0, 0, 6, 0],
    [0, 7, 0, 0, 0, 0, 0, 8, 0],
    [0, 9, 4, 0, 0, 6, 0, 1, 5]
];

sudoku5 = [
    [1, 0, 0, 6, 0, 0, 0, 0, 0],
    [0, 2, 0, 0, 0, 0, 9, 0, 0],
    [0, 0, 3, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 4, 0, 0, 0, 1, 0],
    [0, 4, 0, 0, 5, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 6, 0, 9, 0],
    [0, 0, 2, 0, 0, 0, 7, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 8, 0],
    [3, 0, 0, 5, 0, 0, 0, 0, 9]
];

sudoku6 = [
    [0, 0, 8, 1, 0, 0, 7, 0, 0],
    [0, 3, 0, 0, 8, 0, 0, 0, 0],
    [2, 0, 4, 0, 0, 0, 0, 0, 9],
    [0, 0, 0, 3, 5, 0, 0, 0, 0],
    [0, 7, 0, 0, 0, 8, 0, 0, 0],
    [0, 0, 0, 0, 0, 9, 8, 4, 0],
    [0, 0, 0, 0, 2, 0, 0, 0, 6],
    [0, 0, 0, 9, 0, 1, 0, 3, 0],
    [9, 0, 2, 0, 0, 7, 0, 0, 0],
];

sudoku7 = [
    [0, 0, 6, 8, 0, 0, 0, 2, 0],
    [0, 0, 0, 0, 0, 0, 5, 0, 0],
    [4, 0, 5, 0, 7, 0, 0, 0, 0],
    [0, 0, 0, 0, 8, 2, 9, 0, 0],
    [7, 0, 2, 1, 0, 0, 8, 0, 0],
    [0, 0, 0, 6, 0, 0, 0, 5, 3],
    [9, 0, 0, 0, 0, 1, 7, 0, 0],
    [0, 0, 0, 5, 0, 0, 0, 0, 0],
    [6, 0, 4, 0, 0, 0, 0, 0, 2],
];

printSudoku(sudoku1)
solved = solve(sudoku7)
printSudoku(solved)
