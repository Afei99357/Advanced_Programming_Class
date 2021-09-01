package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class randomDnaSequenceGenerator
{
    public static void main(String[] args)
    {
        String alphabet = "ATGC";
        int N = alphabet.length();
        Random r = new Random();
        List<String> sequenceList = new ArrayList<String>();
        for (int i=0; i<1000; i++){
            StringBuilder dnaSequence = new StringBuilder(3);
            for (int n = 0; n < 3; n++) {
                dnaSequence.append(alphabet.charAt(r.nextInt(N)));
            }
            sequenceList.add(dnaSequence.toString());
        }

        int numberOfAAA = 0;
        for (String i: sequenceList){
            if (i.equals("AAA")){
                numberOfAAA++;
            }
        }
        System.out.println(sequenceList);
        System.out.println("The frequency of AAA is: " + (float)numberOfAAA/1000);


//        For p(A) = 0.12; p(C) = 0.38; p(G) = 0.39; p(T) = 0.11
//        the number of A=360, T=330, G=1170 , C=1140
        int numberOfA = 0;
        int numberOfT = 0;
        int numberOfG = 0;
        int numberOfC = 0;

        List<String> sequenceListNew = new ArrayList<String>();
        for (int i=0; i<1000; i++){


            StringBuilder dnaSequenceNew = new StringBuilder(3);
            for (int n = 0; n < 3; n++) {
                int M = alphabet.length();
                char codon = alphabet.charAt(r.nextInt(M));
                dnaSequenceNew.append(codon);
                if(String.valueOf(codon).equals("A")){
                    numberOfA++;
                }
                else if(String.valueOf(codon).equals("T")){
                    numberOfT++;
                }
                else if(String.valueOf(codon).equals("G")){
                    numberOfG++;
                }
                else if(String.valueOf(codon).equals("C")){
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

        int numberOfAAANew = 0;
        for (String i: sequenceListNew){
            if (i.equals("AAA")){
                numberOfAAANew++;
            }
        }
        System.out.println("The frequency of A is: " + (float)numberOfA/3000);
        System.out.println("The frequency of T is: " + (float)numberOfT/3000);
        System.out.println("The frequency of G is: " + (float)numberOfG/3000);
        System.out.println("The frequency of C is: " + (float)numberOfC/3000);
        System.out.println("The new frequency of AAA is: " + (float)numberOfAAANew/1000);

    }
}