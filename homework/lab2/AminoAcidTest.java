package homework.lab2;


public class AminoAcidTest
{
    public static String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P",
            "S", "T", "W", "Y", "V" };
    public static String[] FULL_NAMES = {"alanine","arginine", "asparagine", "aspartic acid", "cysteine", "glutamine",
            "glutamic acid", "glycine" , "histidine","isoleucine", "leucine",  "lysine", "methionine", "phenylalanine",
            "proline", "serine", "threonine","tryptophan", "tyrosine", "valine"};

    public static void main(String[] args) throws InterruptedException
    {
        System.out.println("Get Ready! The quiz will start in 3 seconds!");
        Thread.sleep(3000);
        long startTime = System.currentTimeMillis();

        int score = 0;
        for (int m=0; 1==1 ; m++){
            int i = (int) (Math.random() * 20);
            long currentTime = System.currentTimeMillis();
            if ((currentTime - startTime) <= 30000){
                System.out.println(FULL_NAMES[i]);
                System.out.println("Please insert your one letter answer and click enter!");
                String aString = System.console().readLine().toUpperCase();
                if (aString.length() != 0){
                    String aChar = "" + aString.charAt(0);
                    if (aString.equals("QUIT")){
                        long currentTimeFinish = System.currentTimeMillis();
                        System.out.println("\nThe quiz has been stopped manually and your score for this test is: "
                                + score + "， Time cost: " + (currentTimeFinish - startTime)/ 1000 + " seconds.");
                        break;
                    }
                    if (aChar.equals(SHORT_NAMES[i])){
                        score++;
                        continue;
                    }else{
                        long currentTimeFinish = System.currentTimeMillis();
                        System.out.println("\nThe quiz is finished due to a wrong answer!");
                        System.out.println("Your score for this test is: " + score +
                                "， Time cost: " + (currentTimeFinish - startTime)/ 1000 + " seconds.");
                        break;
                    }
                }else{
                    System.out.println("Please insert a validate letter!");
                }
            }else{
                System.out.println("\nThe time is out!");
                System.out.println("Your score for this test is: " + score);
                break;
            }
        }
    }
}
