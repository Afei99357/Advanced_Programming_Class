package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class randomDnaSequenceGenerator
{
    private static String alphabet = "ATGC";
    /** produced random dna sequence */
//    static void print1000DnaSequence() {
//        String alphabet = "ATGC";
//        int N = alphabet.length();
//        Random r = new Random();
//        List<String> sequenceList = new ArrayList<>();
//        for (int i=0; i<1000; i++){
//            StringBuilder dnaSequence = new StringBuilder(3);
//            for (int n = 0; n < 3; n++) {
//                dnaSequence.append(alphabet.charAt(r.nextInt(N)));
//            }
//            sequenceList.add(dnaSequence.toString());
//        }
//
//        int numberOfAAA = 0;
//        for (String i: sequenceList){
//            if (i.equals("AAA")){
//                numberOfAAA++;
//            }
//        }
//        System.out.println(sequenceList);
//        System.out.println("The frequency of AAA is: " + (float)numberOfAAA/1000);
//    }

    static void print1000DnaSequence25Percentage() {
        /****
         * ********
         * For p(A) = 0.25; p(C) = 0.25; p(G) = 0.25; p(T) = 0.25
         * the number of A=750, T=750, G=750 , C=750
         *********/

//        String alphabet = "ATGC";
        Random r = new Random();

        int numberOfA = 0;
        int numberOfT = 0;
        int numberOfG = 0;
        int numberOfC = 0;

        List<String> sequenceListNew = new ArrayList<>();
        for (int i=0; i<1000; i++){


            StringBuilder dnaSequenceNew = new StringBuilder(3);
            for (int n = 0; n < 3; n++) {
                int M = alphabet.length();
                char threeMers = alphabet.charAt(r.nextInt(M));
                dnaSequenceNew.append(threeMers);
                if(String.valueOf(threeMers).equals("A")){
                    numberOfA++;
                }
                else if(String.valueOf(threeMers).equals("T")){
                    numberOfT++;
                }
                else if(String.valueOf(threeMers).equals("G")){
                    numberOfG++;
                }
                else if(String.valueOf(threeMers).equals("C")){
                    numberOfC++;
                }
                if(numberOfA >750){
                    alphabet = "TGC";
                    if(numberOfT>750){
                        alphabet = "GC";
                        if(numberOfC > 750){
                            alphabet = "G";
                        }
                    }
                }
                if(numberOfT >750){
                    alphabet = "AGC";
                    if(numberOfA>750){
                        alphabet = "GC";
                        if(numberOfC > 750){
                            alphabet = "G";
                        }
                    }
                }
                if(numberOfC >750){
                    alphabet = "AGT";
                    if(numberOfT>750){
                        alphabet = "AG";
                        if(numberOfA > 750){
                            alphabet = "G";
                        }
                    }
                }
                if(numberOfG >750){
                    alphabet = "ACT";
                    if(numberOfT>750){
                        alphabet = "AC";
                        if(numberOfA > 750){
                            alphabet = "C";
                        }
                    }
                }
            }
            sequenceListNew.add(dnaSequenceNew.toString());
        }

        int numberOfAaaRandom = 0;
        for (String i: sequenceListNew){
            if (i.equals("AAA")){
                numberOfAaaRandom++;
            }
        }
        System.out.println("\n******Answers for question 1: ******\n");
        System.out.println("number of A is: " + numberOfA + "\n" + "number of T is: " + numberOfT + "\n"
                + "number of G is: " + numberOfG + "\n" + "number of C is: " + numberOfC);
        System.out.println("The frequency of A is: " + (float)numberOfA/3000);
        System.out.println("The frequency of T is: " + (float)numberOfT/3000);
        System.out.println("The frequency of G is: " + (float)numberOfG/3000);
        System.out.println("The frequency of C is: " + (float)numberOfC/3000);
        System.out.println("The 1000 random DNA 3-mers are: " + sequenceListNew + "\n");

        System.out.println("******Answers for question 2: ******\n");
        System.out.println("The expected frequency of AAA is: " + 0.25*0.25*0.25 );
        System.out.println("The actual java frequency of AAA is: " + (float)numberOfAaaRandom/1000 + "\n");
        System.out.println("The java's number is pretty close to my expectation. \n");
    }

    static void print1000DnaSequenceModified()
    {
        /****
         * ********
         * For p(A) = 0.12; p(C) = 0.38; p(G) = 0.39; p(T) = 0.11
         * the number of A=360, T=330, G=1170 , C=1140
         *********/

//        String alphabet = "ATGC";
        Random r = new Random();

        int numberOfA = 0;
        int numberOfT = 0;
        int numberOfG = 0;
        int numberOfC = 0;

        List<String> sequenceListNew = new ArrayList<>();
        for (int i=0; i<1000; i++){


            StringBuilder dnaSequenceNew = new StringBuilder(3);
            for (int n = 0; n < 3; n++) {
                int M = alphabet.length();
                char threeMers = alphabet.charAt(r.nextInt(M));
                dnaSequenceNew.append(threeMers);
                if(String.valueOf(threeMers).equals("A")){
                    numberOfA++;
                }
                else if(String.valueOf(threeMers).equals("T")){
                    numberOfT++;
                }
                else if(String.valueOf(threeMers).equals("G")){
                    numberOfG++;
                }
                else if(String.valueOf(threeMers).equals("C")){
                    numberOfC++;
                }
                if(numberOfA >360){
                    alphabet = "TGC";
                    if(numberOfT>330){
                        alphabet = "GC";
                        if(numberOfC > 1140){
                            alphabet = "G";
                        }
                    }
                }
                if(numberOfT >330){
                    alphabet = "AGC";
                    if(numberOfA>360){
                        alphabet = "GC";
                        if(numberOfC > 1140){
                            alphabet = "G";
                        }
                    }
                }
                if(numberOfC >1140){
                    alphabet = "AGT";
                    if(numberOfT>330){
                        alphabet = "AG";
                        if(numberOfA > 360){
                            alphabet = "G";
                        }
                    }
                }
                if(numberOfG >1170){
                    alphabet = "ACT";
                    if(numberOfT>330){
                        alphabet = "AC";
                        if(numberOfA > 360){
                            alphabet = "C";
                        }
                    }
                }
            }
            sequenceListNew.add(dnaSequenceNew.toString());
        }

        int numberOfAaaModified = 0;
        for (String i: sequenceListNew){
            if (i.equals("AAA")){
                numberOfAaaModified++;
            }
        }

        System.out.println("******Answers for question 3: ******\n");
        System.out.println("number of A is: " + numberOfA + "\n" + "number of T is: " + numberOfT + "\n" +
                "number of G is: " + numberOfG + "\n" + "number of C is: " + numberOfC);
        System.out.println("The modified frequency of A is: " + (float)numberOfA/3000);
        System.out.println("The modified frequency of T is: " + (float)numberOfT/3000);
        System.out.println("The modified frequency of G is: " + (float)numberOfG/3000);
        System.out.println("The modified frequency of C is: " + (float)numberOfC/3000);
        System.out.println("The 1000 random DNA 3-mers are: " + sequenceListNew + "\n");
        System.out.println("The expected modified frequency of AAA is: " + 0.12*0.12*0.12);
        System.out.println("The actual java modified frequency of AAA is: " + (float)numberOfAaaModified/1000);
        System.out.println("The java produced 'AAA' does not at close to the expected frequency!");
    }

    public static void main(String[] args)
    {
//        print1000DnaSequence();
        print1000DnaSequence25Percentage();
        print1000DnaSequenceModified();

//        // run the modified sequence generator 10000 times
//        for(int i=0; i<10000; i++){
//            print1000DnaSequenceModified();
//            System.out.println("\n");
//        }

    }
}
