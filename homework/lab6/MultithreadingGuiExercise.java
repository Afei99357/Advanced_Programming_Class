package homework.lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MultithreadingGuiExercise extends JFrame
{
    private static final JTextArea output = new JTextArea(10,25);
    public static final List<Long> resultList = Collections.synchronizedList(new ArrayList<>());
    private static final int numberOfWorkers = 2;
    private static long beginTime;
    private static int cancelFlag = 0;


    public MultithreadingGuiExercise(){
        super("Get Prime Number");
        setLocationRelativeTo(null);
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(getUpperPanel());
        getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
        output.setEditable(false);
        output.setWrapStyleWord(true);

        setVisible(true);
    }

    private JPanel getUpperPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setSize(50,50);
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cancelFlag=1;
                validate();
            }
        });
        JScrollPane scroller = new JScrollPane(output);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.add(scroller);
        panel.add(cancelButton);
        return panel;
    }

    private JPanel getBottomPanel(){
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1));
        JButton startButton = new JButton("Input Your Number");
        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFrame frame = new JFrame();
                for(int i=1; i!=0; i--){
                    String input = JOptionPane.showInputDialog(frame,
                            "Enter your number: ");
                    if (input != null && input.length()>0)
                    try{
                        Long originalNumber = Long.parseLong(input);
                        i=1;
                        output.setText("");
                        resultList.clear();
                        cancelFlag=0;
                        beginTime = System.currentTimeMillis();
                        calculatePrimeNumber(originalNumber);
                    } catch (NumberFormatException numberFormatException) {
                        JOptionPane.showMessageDialog(frame, "Only number is allowed");
                        i++;
                    } catch (HeadlessException | InterruptedException headlessException) {
                        headlessException.printStackTrace();
                    }
                }
                validate();
            }
        });

        panel.add(startButton);
        return panel;
    }

    private void calculatePrimeNumber(Long originalNumber) throws InterruptedException
    {
        Semaphore semaphore = new Semaphore(numberOfWorkers);

        for (int i = 0; i < numberOfWorkers; i++) {
            semaphore.acquire();
            Worker worker = new Worker(originalNumber,i, semaphore);
//            System.out.println("thread " + i + " started!");
            new Thread(worker).start();
        }

        ManagerWorker managerWorker = new ManagerWorker(semaphore);
        new Thread(managerWorker).start();
    }

    private class Worker implements Runnable{

        private final long number;
        private int reminder;
        private final Semaphore semaphore;

        public Worker(long number,int reminder, Semaphore semaphore)
        {
            this.number = number;
            this.reminder = reminder;
            this.semaphore = semaphore;
        }

        @Override
        public void run()
        {
            for(long i=0; i<number; i++){
                if(cancelFlag==1){
                    output.append("Worker " + i +" stopped!"+ "\n");
                    semaphore.release();
                    break;
                }
                if(number%2==reminder){
                    if (i == 2 || i == 3) {
                        resultList.add(i);
                        output.append(i + "\n");
                        continue;
                    }

                    for(int m =2; m<=(int) Math.sqrt(i);m++){
                        if(i%m==0){
                            break;
                        }
                        if(m==(int) Math.sqrt(i)){
                            resultList.add(i);
                            output.append(i + "\n");
                        }
                    }
                }
            }
//            System.out.println("release "+System.currentTimeMillis());
            semaphore.release();
        }
    }

    private class ManagerWorker implements Runnable{

        private final Semaphore managerSemaphore;

        public ManagerWorker(Semaphore managerSemaphore)
        {
            this.managerSemaphore = managerSemaphore;
        }

        @Override
        public void run()
        {
            int numAcquire = 0;
            while(numAcquire < (numberOfWorkers)){
                try {
                    managerSemaphore.acquire();
                    numAcquire++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("release "+System.currentTimeMillis());
            long endingTime = System.currentTimeMillis();
            output.append("total time cost is: " + (endingTime-beginTime) / 1000f + "s\n");
            managerSemaphore.release();
            output.append("total number of prime is " + resultList.size());

        }
    }

    public static void main(String[] args)
    {
        new MultithreadingGuiExercise();
    }
}
