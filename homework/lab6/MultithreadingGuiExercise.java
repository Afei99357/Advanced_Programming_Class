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
                    if (input == null || input.length()==0){
                        break;
                    }
                    else{
                        try{
                            Long originalNumber = Long.parseLong(input);
                            i=1;
                            output.setText("");
                            resultList.clear();
                            beginTime = System.currentTimeMillis();
//                            Semaphore managerThreadSemaphore = new Semaphore(1);
//                            ManagerWorker managerWorker = new ManagerWorker(managerThreadSemaphore);
//                            new Thread(managerWorker).start();
                            getPrimeNumber(originalNumber);
                        } catch (NumberFormatException numberFormatException) {
                            JOptionPane.showMessageDialog(frame, "Only number is allowed");
                            i++;
                        } catch (HeadlessException | InterruptedException headlessException) {
                            headlessException.printStackTrace();
                        }
                    }
                }
                validate();
            }
        });

        panel.add(startButton);
        return panel;
    }

    private void getPrimeNumber(Long originalNumber) throws InterruptedException
    {
        Semaphore semaphore = new Semaphore(numberOfWorkers);
//        ManagerWorker managerWorker = new ManagerWorker(semaphore);
//        new Thread(managerWorker).start();

        for (long i=1; i<originalNumber; i++){
            if(i == 2 || i == 3){
                resultList.add(i);
                output.append(i + "\n");
                continue;
            }
            if (i%numberOfWorkers==0){
                semaphore.acquire();
                Worker worker = new Worker(i, semaphore);
                new Thread(worker).start();
            }
            if (i%numberOfWorkers==1){
                semaphore.acquire();
                Worker worker = new Worker(i, semaphore);
                new Thread(worker).start();
            }
        }


        long endingTime = System.currentTimeMillis();
        System.out.println("total time cost is: " + (endingTime-beginTime) / 1000f);
        output.append("total number of prime is " + resultList.size());
    }

    private class Worker implements Runnable{

        private final long number;
        private final Semaphore semaphore;

        public Worker(long number, Semaphore semaphore)
        {
            this.number = number;
            this.semaphore = semaphore;
        }

        @Override
        public void run()
        {
            Thread.yield();
            for(int i =2; i<=(int) Math.sqrt(number);i++){
                if(number%i==0){
                    break;
                }

                if(i==(int) Math.sqrt(number)){
                    resultList.add(number);
                    output.append(number + "\n");
                }
                Thread.yield();
            }

            System.out.println("release "+System.currentTimeMillis());
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                numAcquire++;
            }
            System.out.println("release "+System.currentTimeMillis());
            managerSemaphore.release();
        }
    }

    public static void main(String[] args)
    {
        new MultithreadingGuiExercise();
    }
}
