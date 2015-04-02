package my.sudoku.main;

import java.util.*;

/**
 * This class discribes algoritm of generation for sudoku
 *
 * @author Vlad Skurishin
 */
public class SudokuGenerator
{
    // ================================ constants
    public static final int NUMBER_OF_CELLS = 9;
    private static final int RANDOM_CONST = 3;
    // ================================ array with values
    private int sudokuField[][];

    /**
     * Constructors
     */

    public SudokuGenerator(SudokuLevel level)
    {
        generate(level);
    }

    public int[][] generate(SudokuLevel level)
    {
        sudokuField = new int[NUMBER_OF_CELLS][NUMBER_OF_CELLS];

        for (int i = 0; i < sudokuField.length; i++)
        {
            for (int j = 0; j < sudokuField.length; j++)
            {
                sudokuField[i][j] = j + 1;
            }
        }

        shuffle(sudokuField[0]);

        /**
         * Divide the first row on 3 blocks ABC (ex: 123465798 - A123 B465 C798)
         * then the second row composed from CBA (ex: 798465123 - C798 B465 A123)
         * and the third from BCA (ex: B465 C798 A123)
         * */

        for (int i = 0; i < 3; i++)
        {
            // ======================= auxiliary array
            int array[][] = new int[2][];

            array[0] = Arrays.copyOfRange(sudokuField[0], NUMBER_OF_CELLS - i, NUMBER_OF_CELLS);
            array[1] = Arrays.copyOfRange(sudokuField[0], 0, NUMBER_OF_CELLS - i);
            sudokuField[i * 3] = concat(array[0], array[1]);

            array[0] = Arrays.copyOfRange(sudokuField[i * 3], 6, NUMBER_OF_CELLS);
            array[1] = Arrays.copyOfRange(sudokuField[i * 3], 0, 6);
            sudokuField[i * 3 + 1] = concat(array[0], array[1]);

            array[0] = Arrays.copyOfRange(sudokuField[i * 3], 3, NUMBER_OF_CELLS);
            array[1] = Arrays.copyOfRange(sudokuField[i * 3], 0, 3);
            sudokuField[i * 3 + 2] = concat(array[0], array[1]);
        }

        randomizeSudokuField(sudokuField);
        eraseCellsOfField(sudokuField, level.getNumberOfEraseCells(), level.isOneSolutionMustBe(), level.isSymmetrical());

        return sudokuField;
    }

    /**
     * randomize position of columns and rows
     *
     * @param sudokuField array of sudoku cells
     * @return sudokuField
     * return array of sudoku cells
     */

    private int[][] randomizeSudokuField(int sudokuField[][])
    {
        for (int i = 0; i < RANDOM_CONST; i++)
        {
            // ============================== random valumes in block of 3 rows
            int x1 = new Random().nextInt(NUMBER_OF_CELLS);
            int x2 = new Random().nextInt(3) + (x1 / 3) * 3;
            // ============================== random valumes in block of 3 cols
            int x3 = new Random().nextInt(NUMBER_OF_CELLS);
            int x4 = new Random().nextInt(3) + (x3 / 3) * 3;
            // ============================== swap rows
            int temp[];
            temp = sudokuField[x1];
            sudokuField[x1] = sudokuField[x2];
            sudokuField[x2] = temp;

            // ============================== swap cols
            for (int array[] : sudokuField)
            {
                int temp2 = array[x3];
                array[x3] = array[x4];
                array[x4] = temp2;
            }
        }

        return sudokuField;
    }

    /**
     * shuffle sudoku  row
     *
     * @param sudokuField array contains sudoku row
     * @return sudokuField
     * return sudoku row
     */
    private int[] shuffle(int sudokuField[])
    {
        for (int i = 0; i < sudokuField.length; i++)
        {

            int j = new Random(System.currentTimeMillis())
                    .nextInt(NUMBER_OF_CELLS);

            int temp = sudokuField[i];
            sudokuField[i] = sudokuField[j];
            sudokuField[j] = temp;
        }

        return sudokuField;
    }


    /**
     * @param a first array
     * @param b second array
     * @return result
     * result array
     */
    private int[] concat(int[] a, int[] b)
    {
        final int alen = a.length;
        final int blen = b.length;
        if (alen == 0)
        {
            return b;
        }
        if (blen == 0)
        {
            return a;
        }
        final int[] result = (int[]) java.lang.reflect.Array.newInstance(a
                .getClass().getComponentType(), alen + blen);
        System.arraycopy(a, 0, result, 0, alen);
        System.arraycopy(b, 0, result, alen, blen);
        return result;
    }


    /**
     * set erase cells per
     * block 3x3 with one solution on all field
     * <p/>
     * divide all field on 9 blocks 3x3
     * <p/>
     * block 3x3 may contain less then numErasePerBlock
     * invisible cells
     *
     * @param sudokuField       array of sudoku cells
     * @param num               number of erase cells
     * @param isOnlyOneSolution only one solution need or not?
     * @param isSymmetrical     symmetrical sudoku or not?
     * @return sudokuField
     * return array of sudoku cells
     */
    private int[][] eraseCellsOfField(int sudokuField[][], int num, boolean isOnlyOneSolution, boolean isSymmetrical)
    {
        int block[][] = new int[3][3];
        int numErasePerBlock = num / NUMBER_OF_CELLS + 1;
        List<Integer> positions = new ArrayList<Integer>();

        for (int i = 0; i < 81; i++)
            positions.add(i);
        Collections.shuffle(positions);
        if (!isSymmetrical)
        {
            while (positions.size() > 0 && num > 0)
            {
                int position = positions.remove(0);
                int y = position % 9;
                int x = position / 9;
                boolean isSatisfied = (sudokuField[x][y] != 0)
                        && (block[x / 3][y / 3] < numErasePerBlock);

                if (isSatisfied)
                {
                    int temp = sudokuField[x][y];
                    sudokuField[x][y] = 0;
                    if (isOnlyOneSolution && !Validator.isValid(sudokuField))
                    {
                        sudokuField[x][y] = temp;
                        continue;
                    }

                    block[x / 3][y / 3]++;
                    num--;
                }
            }
        } else
        {
            while (num > 0)
            {

                int y = new Random().nextInt(5);
                int x = new Random().nextInt(y > 3 ? 5 : 9);
                int xsym = NUMBER_OF_CELLS - x - 1;
                int ysym = NUMBER_OF_CELLS - y - 1;
                boolean isSatisfied = (sudokuField[x][y] != 0)
                        && (block[x / 3][y / 3] < numErasePerBlock);

                if (isSatisfied)
                {
                    int temp = sudokuField[x][y];
                    int tempSym = sudokuField[xsym][ysym];

                    sudokuField[x][y] = 0;
                    sudokuField[xsym][ysym] = 0;

                    if (isOnlyOneSolution && !Validator.isValid(sudokuField))
                    {
                        sudokuField[x][y] = temp;
                        sudokuField[xsym][ysym] = tempSym;
                        continue;
                    }

                    block[x / 3][y / 3]++;
                    num -= 2;
                }
            }

        }
        return sudokuField;
    }


    public int getCellValue(int i, int j)
    {
        return sudokuField[i][j];
    }

    public boolean isCellVisible(int i, int j)
    {
        return (sudokuField[i][j] == 0) ? false : true;
    }
}
