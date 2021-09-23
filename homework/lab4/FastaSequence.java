package homework.lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FastaSequence
{
    private String sequence;

    public FastaSequence(String sequence)
    {
        this.sequence = sequence;
    }

    public void setSequence(String sequence)
    {
        this.sequence = sequence;
    }

    // returns the header of this sequence without the “>”
    public String getHeader(){
        return this.sequence.split("\n", 2)[0];
    }

    // returns the Dna sequence of this FastaSequence
    public String getSequence(){
        return this.sequence.split("\n", 2)[1];
    }

    // returns the number of G’s and C’s divided by the length of this sequence
    public float getGCRatio() {
        String sequence = getSequence();
        int numberOfGC = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char temp = sequence.charAt(i);
            if (temp == 'A' || temp == 'G'){
                numberOfGC++;
            }
        }
        return (float)numberOfGC / sequence.length();
    }

    public static void main(String[] args) throws Exception
    {
        List<FastaSequence> fastaList =
                FastaSequence.GetFastaSequence("/Users/ericliao/Desktop/phD_courses/2021Fall_class/" +
                        "Advanced_Programming_Class/labs/MDomestica_first100.fa");

        for( FastaSequence fs : fastaList)
        {
            System.out.println(fs.getHeader());
            System.out.println(fs.getSequence());
            System.out.println(fs.getGCRatio());
        }
    }

    private static List<FastaSequence> GetFastaSequence(String s) throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(s)));
        int numberLine = 0;
        List<FastaSequence> fastaSequenceList = new ArrayList<>();
        StringBuilder sequenceBuilder = new StringBuilder();
        for(String nextline=reader.readLine(); nextline != null; nextline = reader.readLine()){
//            String header = "";


            if (nextline.startsWith(">")){
                if(numberLine > 0){
                    FastaSequence fastaSequence = new FastaSequence(sequenceBuilder.toString());
                    fastaSequenceList.add(fastaSequence);
                    // initiate the string builder for next sequence
                    sequenceBuilder.setLength(0);
                    numberLine = 0;
                    sequenceBuilder.append(nextline.substring(1));
                    sequenceBuilder.append("\n");
                    numberLine++;
                }else{
                    sequenceBuilder.append(nextline.substring(1));
                    sequenceBuilder.append("\n");
                    numberLine++;
                }

            }else{
                sequenceBuilder.append(nextline);
                numberLine++;
            }
        }
        reader.close();
        return fastaSequenceList;
    }


}
