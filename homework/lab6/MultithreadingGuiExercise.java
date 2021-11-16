package homework.lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public class MultithreadingGuiExercise extends JFrame
{
    private volatile static JTextArea output = new JTextArea(10,25);
    public static final List<Long> resultList = new CopyOnWriteArrayList<>();
    private static final int numberOfWorkers = 4;


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

    private class Worker implements Runnable{

        private final long bottomNumber;
        private final long topNumber;
        private final Semaphore semaphore;

        public Worker(long bottomNumber, long topNumber, Semaphore semaphore)
        {
            this.bottomNumber = bottomNumber;
            this.topNumber = topNumber;
            this.semaphore = semaphore;
        }

        @Override
        public void run()
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long threadBeginTime = System.currentTimeMillis();
            long m = bottomNumber + 1;
            while(m<=topNumber){
                if(m==2 || m==3){
                    resultList.add(m);
                    m++;
                    continue;
                }

                for(int i =2; i<=(int) Math.sqrt(m);i++){
                    if(m%i==0){
                        break;
                    }
                    if(i==(int) Math.sqrt(m)){
                        resultList.add(m);
//                        output.append(m + "\n");
                    }
                }
                m++;
                continue;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("release "+System.currentTimeMillis());
            semaphore.release();
            long threadEndTime = System.currentTimeMillis();
            System.out.println("Thread time cost is: " + (threadEndTime - threadBeginTime)/1000f);
        }
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
                            getPrimeNumber(originalNumber);
                        } catch (NumberFormatException numberFormatException) {
                            JOptionPane.showMessageDialog(frame, "Only number is allowed");
                            i++;
                        } catch (HeadlessException headlessException) {
                            headlessException.printStackTrace();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
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
        long interval = originalNumber % numberOfWorkers;
        Semaphore semaphore = new Semaphore(numberOfWorkers);
        for(int i=0; i<numberOfWorkers;i++){
            if (i!=numberOfWorkers){
                semaphore.acquire();
                long bottom = interval * (i-1);
                long top = interval * i;
                Worker worker = new Worker(bottom, top, semaphore);
                new Thread(worker).start();
            }
            if(i==numberOfWorkers){
//                semaphore.acquire();
//                long bottom = interval * (i-1);
//                long newInterval = (originalNumber-bottom) % 5;
                semaphore.acquire();
                long newBottom = interval * (i-1);
                long newTop = originalNumber;
                Worker worker = new Worker(newBottom, newTop, semaphore);
                new Thread(worker).start();
//                for (int m=1; m<=5; m++){
//                    if(m!=5){
//                        .acquire();
//                        long newBottom = bottom + newInterval * (m-1);
//                        long newTop = bottom + newInterval * m;
//                        Worker worker = new Worsemaphoreker(newBottom, newTop, semaphore);
//                        new Thread(worker).start();
//                    }
//                    if(m==5){
//                        semaphore.acquire();
//                        long newBottom = bottom + newInterval * (m-1);
//                        long newTop = originalNumber;
//                        Worker worker = new Worker(newBottom, newTop, semaphore);
//                        new Thread(worker).start();
//                    }
//                }
            }
        }

        int numAcquire = 0;
        while(numAcquire < numberOfWorkers){
            semaphore.acquire();
            numAcquire++;
        }

        for(int n=0; n< resultList.size(); n++ ){
            output.append(resultList.get(n) + "\n");
        }
        output.append("total number of prime is " + resultList.size());
    }

    public static void main(String[] args)
    {
        new MultithreadingGuiExercise();
    }
}
