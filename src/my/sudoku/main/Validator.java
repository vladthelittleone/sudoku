package my.sudoku.main;

import java.util.ArrayList;
import java.util.List;

public class Validator
{
    public static boolean isValid(int[][] sudokuField)
    {
        return isValid(sudokuField, 0, new int[]{0});
    }

    /**
     * Checks whether given game is valid, user should use the other isValid
     * method. There may only be one solution.
     *
     * @param sudokuField       field to check.
     * @param index             Current index to check.
     * @param numberOfSolutions Number of found solutions. Int[] instead of int because of
     *                          pass by reference.
     * @return True if game is valid, false otherwise.
     */
    private static boolean isValid(int[][] sudokuField, int index,
                                   int[] numberOfSolutions)
    {
        // if >1 solutions false
        if (index > 80)
            return ++numberOfSolutions[0] == 1;

        // ================= get next cell position
        int x = index % 9;
        int y = index / 9;
        //	============= if cell erase
        if (sudokuField[y][x] == 0)
        {
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 1; i <= 9; i++)
                numbers.add(i);

            while (numbers.size() > 0)
            {
                // get number that we must put in erase cell
                int number = getNextPossibleNumber(sudokuField, x, y, numbers);
                if (number == -1)
                    break;
                sudokuField[y][x] = number;

                // recursion: we go to next cell
                if (!isValid(sudokuField, index + 1, numberOfSolutions))
                {
                    sudokuField[y][x] = 0;
                    return false;
                }
                sudokuField[y][x] = 0;
            }
        } else if (!isValid(sudokuField, index + 1, numberOfSolutions))
            return false;

        return true;
    }

    /**
     * Returns next posible number from list for given position or -1 when list
     * is empty.
     *
     * @param sudokuField Game to check.
     * @param x           X position in game.
     * @param y           Y position in game.
     * @param numbers     List of remaining numbers.
     * @return Next possible number for position in game or -1 when list is
     * empty.
     */
    private static int getNextPossibleNumber(int[][] sudokuField, int x, int y,
                                             List<Integer> numbers)
    {
        while (numbers.size() > 0)
        {
            int number = numbers.remove(0);
            if (isPossibleX(sudokuField, y, number) && isPossibleY(sudokuField, x, number)
                    && isPossibleBlock(sudokuField, x, y, number))
                return number;
        }
        return -1;
    }

    /**
     * Check row
     *
     * @param sudokuField Game to check.
     * @param y           Position of y axis to check.
     * @param number      Number to check.
     * @return True if number is candidate on y axis, false otherwise.
     */
    private static boolean isPossibleX(int[][] sudokuField, int y, int number)
    {
        for (int x = 0; x < 9; x++)
        {
            if (sudokuField[y][x] == number)
                return false;
        }
        return true;
    }

    /**
     * Check colum
     *
     * @param sudokuField Game to check.
     * @param x           Position of y axis to check.
     * @param number      Number to check.
     * @return True if number is candidate on y axis, false otherwise.
     */
    private static boolean isPossibleY(int[][] sudokuField, int x, int number)
    {
        for (int y = 0; y < 9; y++)
        {
            if (sudokuField[y][x] == number)
                return false;
        }
        return true;
    }

    /**
     * Check block 3x3 on
     *
     * @param sudokuField sudokuField to check.
     * @param x           Position of number on x axis in game to check.
     * @param y           Position of number on y axis in game to check.
     * @param number      Number to check.
     * @return True if number is candidate in block, false otherwise.
     */
    private static boolean isPossibleBlock(int[][] sudokuField, int x, int y,
                                           int number)
    {
        int x1 = x < 3 ? 0 : x < 6 ? 3 : 6;
        int y1 = y < 3 ? 0 : y < 6 ? 3 : 6;
        for (int yy = y1; yy < y1 + 3; yy++)
        {
            for (int xx = x1; xx < x1 + 3; xx++)
            {
                if (sudokuField[yy][xx] == number)
                    return false;
            }
        }
        return true;
    }
}
