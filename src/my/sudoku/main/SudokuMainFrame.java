package my.sudoku.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuMainFrame extends JFrame implements ActionListener
{
    private static final long serialVersionUID = -1138839577647075758L;
    private SudokuGenerator generator;
    private SudokuLevel level;
    //================== array of JButtons
    private JButton buttons[][];
    private JPanel sudokuPrintArea;


    public SudokuMainFrame()
    {
        // =============================== init generator
        buttons = new JButton[9][9];
        level = new SudokuLevel(SudokuLevel.EASY, true);
        generator = new SudokuGenerator(level);

        // =============================== panel that we will print
        sudokuPrintArea = new JPanel();
        sudokuPrintArea.setLayout(new GridLayout(SudokuGenerator.NUMBER_OF_CELLS,
                SudokuGenerator.NUMBER_OF_CELLS));

        setResizable(false);
        setTitle("Sudoku v0.1");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("sudoku.png")));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        centeringFrame();

        //================================ add print panel
        add(sudokuPrintArea, BorderLayout.CENTER);
        addButtons();
        new SudokuMenuBar().addMenu(this);

        setVisible(true);
    }

    /**
     * centring frame using your dimension
     */
    public void centeringFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    /**
     * add buttons with value from sudoku array
     */
    public void addButtons()
    {
        for (int i = 0; i < SudokuGenerator.NUMBER_OF_CELLS; i++)
        {
            for (int j = 0; j < SudokuGenerator.NUMBER_OF_CELLS; j++)
            {
                boolean a = generator.isCellVisible(i, j);
                JButton b = a ? new JButton(Integer.toString(generator.getCellValue(i, j))) : new JButton("");
                b.setBackground(Color.WHITE);
                b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                sudokuPrintArea.add(b);
                buttons[i][j] = b;
            }
        }
    }

    /**
     * reset text of the buttons
     */
    public void resetButtons()
    {
        for (int i = 0; i < SudokuGenerator.NUMBER_OF_CELLS; i++)
        {
            for (int j = 0; j < SudokuGenerator.NUMBER_OF_CELLS; j++)
            {
                boolean a = generator.isCellVisible(i, j);
                buttons[i][j].setText(a ? Integer.toString(generator.getCellValue(i, j)) : "");
            }
        }
    }

    public void actionPerformed(ActionEvent e)
    {

        if (e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }

        if (e.getActionCommand().equals("Print"))
        {
            PrintUtilities.printComponent(sudokuPrintArea);
        }

        if (e.getActionCommand().equals("Symmetrical"))
        {
            level.setSymmetrical(true);
            generator.generate(level);
            resetButtons();
            this.repaint();
        }

        if (e.getActionCommand().equals("Asymmetrical"))
        {
            level.setSymmetrical(false);
            generator.generate(level);
            resetButtons();
            this.repaint();
        }

        if (e.getActionCommand().equals("Easy"))
        {
            level.setLevel(SudokuLevel.EASY);
            generator.generate(level);
            resetButtons();
            this.repaint();
        }

        if (e.getActionCommand().equals("Medium"))
        {
            level.setLevel(SudokuLevel.MEDIUM);
            generator.generate(level);
            resetButtons();
            this.repaint();
        }

        if (e.getActionCommand().equals("Difficult"))
        {
            level.setLevel(SudokuLevel.DIFFICULT);
            generator.generate(level);
            resetButtons();
            this.repaint();
        }

        if (e.getActionCommand().equals("Extremely"))
        {
            level.setLevel(SudokuLevel.EXTREMELY);
            generator.generate(level);
            resetButtons();
            this.repaint();
        }
        if (e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }

    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    new SudokuMainFrame();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
