package examples;


public class AminoAcidTest
{
    public static String[] SHORT_NAMES = { "A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P",
            "S", "T", "W", "Y", "V" };
    public static String[] FULL_NAMES = {"alanine","arginine", "asparagine", "aspartic acid", "cysteine", "glutamine",
            "glutamic acid", "glycine" , "histidine","isoleucine", "leucine",  "lysine", "methionine", "phenylalanine",
            "proline", "serine", "threonine","tryptophan", "tyrosine", "valine"};

    public static void main(String[] args) throws InterruptedException
    {
        System.out.println("Quiz will start in 5 seconds!");
        Thread.sleep(5000);
        long startTime = System.currentTimeMillis();

        for (int m=0; 1==1 ; m++){
            int i = (int) (Math.random() * 20);
            long currentTime = System.currentTimeMillis();
            if ((currentTime - startTime) <= 30000){
                System.out.println(FULL_NAMES[i]);
                System.out.println("Please insert your one letter answer and click enter!");
                String aString = System.console().readLine().toUpperCase();
                if (aString.length() != 0){
                    String aChar = "" + aString.charAt(0);
                    if (aChar.equals(SHORT_NAMES[i])){
                        continue;
                    }
                    else{
                        System.out.println("The quiz has been stopped due to a wrong answer!");
                        break;
                    }
                }
                else{
                    System.out.println("Please insert a validate letter!");
                }
            }
            else{
                System.out.println("The time is out!");
                break;
            }
        }
    }
}
