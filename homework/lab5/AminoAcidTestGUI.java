package homework.lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AminoAcidTestGUI extends JFrame
{
    public static String[] SHORT_NAMES = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P",
            "S", "T", "W", "Y", "V"};
    public static String[] FULL_NAMES = {"alanine", "arginine", "asparagine", "aspartic acid", "cysteine", "glutamine",
            "glutamic acid", "glycine", "histidine", "isoleucine", "leucine", "lysine", "methionine", "phenylalanine",
            "proline", "serine", "threonine", "tryptophan", "tyrosine", "valine"};

    public static Timer timer = new Timer();
    public static int totalTime;
    public static JTextArea timerDisplay = new JTextArea();
    public static JTextArea questionArea = new JTextArea();
    public static JTextField inputField = new JFormattedTextField();
    public static JTextArea scoreResult = new JTextArea();
    public static int questionIndex = 0;
    public static int correctNumber = 0;
    public static int wrongNumber = 0;


    private JPanel getTopPanel()
    {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(2, 0));

        scoreResult.setEditable(false);
        scoreResult.setWrapStyleWord(true);
        updateResult();
        scoreResult.setText("");

        questionArea.setEditable(false);
        questionArea.setWrapStyleWord(true);
        questionArea.setText("Please start the quiz!");

        panel.add(scoreResult);
        panel.add(questionArea);


        return panel;
    }

    private JPanel getCenterPanel()
    {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(1, 2));

        inputField = new JTextField(20);

        inputField.setEditable(false);

        inputField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (inputField.getText().toUpperCase().equals(SHORT_NAMES[questionIndex])) {
                    correctNumber++;
                } else {
                    wrongNumber++;
                }
                updateResult();
            }
        });

        timerDisplay = new JTextArea("\n\n\n30 Seconds left!");
        timerDisplay.setEditable(false);

        panel.add(inputField);
        panel.add(timerDisplay);
//        panel.add(enterButton);

        return panel;
    }

    private JPanel getBottomPanel()
    {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 2));

        JButton startButton = new JButton("Start The Quiz");
        startButton.setMnemonic('S');
        startButton.setToolTipText("Click here to start");

        JButton quitButton = new JButton("Cancel");
        quitButton.setEnabled(false);

        startButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateQuestion();
                inputField.setText("");
                inputField.setEditable(true);
                correctNumber = 0;
                wrongNumber = 0;
                scoreResult.setText("Right answer: " + correctNumber + ", Wrong answer: " + wrongNumber);
                totalTime=30;
                startButton.setEnabled(false);
                quitButton.setEnabled(true);

                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask()
                {

                    public void run()
                    {
                        timerDisplay.setText("\n\n\nTime left: " + totalTime);
                        totalTime--;

                        if (totalTime < 0) {
                            timer.cancel();
                            timerDisplay.setText("\n\n\nTime is over!");
                            questionArea.setText("Quiz is over! Please restart the quiz.");
                            inputField.setEditable(false);
                            startButton.setEnabled(true);
                        }
                    }
                }, 0, 1000);
                validate();
            }
        });

        quitButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                questionArea.setText("Quiz is over! Please restart the quiz.");
                timerDisplay.setText("\n\n\nTime left: " + totalTime);
                timer.cancel();
                inputField.setEditable(false);
                startButton.setEnabled(true);
                quitButton.setEnabled(false);
                validate();
            }
        });

        panel.add(startButton);
        panel.add(quitButton);
        return panel;
    }


    public AminoAcidTestGUI()
    {
        super("Amino Acid Test");
        setLocationRelativeTo(null);
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getTopPanel(), BorderLayout.NORTH);
        getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateResult()
    {
        scoreResult.setText("Right answer: " + correctNumber + ", Wrong answer: " + wrongNumber);
        updateQuestion();
        inputField.setText("");
        validate();
    }

    public void updateQuestion()
    {
        Map<Integer, String> indexAnwserMap = new HashMap<>();
        questionIndex = (int) (Math.random() * 20);
        String question = FULL_NAMES[questionIndex];
        StringBuilder sb = new StringBuilder("Provide answer for the following amino acid: " + question);
        indexAnwserMap.put(questionIndex, sb.toString());
        questionArea.setText("Please provide the correct answer for this: " + question);
        validate();
    }

    public static void main(String[] args)
    {
        new AminoAcidTestGUI();
    }
}
