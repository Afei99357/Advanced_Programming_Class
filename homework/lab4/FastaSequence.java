package homework.lab4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FastaSequence {
    private String sequence;

    public FastaSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    // returns the header of this sequence without the “>”
    public String getHeader() {
        return this.sequence.split("\n", 2)[0];
    }

    // returns the Dna sequence of this FastaSequence
    public String getSequence() {
        return this.sequence.split("\n", 2)[1];
    }

    // returns the number of G’s and C’s divided by the length of this sequence
    public float getGCRatio() {
        String sequence = getSequence();
        int numberOfGC = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char temp = sequence.charAt(i);
            if (temp == 'A' || temp == 'G') {
                numberOfGC++;
            }
        }
        return (float) numberOfGC / sequence.length();
    }

    private static List<FastaSequence> GetFastaSequences(String s) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(s));
        int numberLine = 0;
        List<FastaSequence> fastaSequenceList = new ArrayList<>();
        StringBuilder sequenceBuilder = new StringBuilder();
        for (String nextline = reader.readLine(); nextline != null; nextline = reader.readLine()) {
            if (nextline.startsWith(">")) {
                if (numberLine > 0) {
                    FastaSequence fastaSequence = new FastaSequence(sequenceBuilder.toString());
                    fastaSequenceList.add(fastaSequence);
                    // initiate the string builder for next sequence
                    sequenceBuilder.setLength(0);
                    numberLine = 0;
                    sequenceBuilder.append(nextline.substring(1));
                    sequenceBuilder.append("\n");
                    numberLine++;
                } else {
                    sequenceBuilder.append(nextline.substring(1));
                    sequenceBuilder.append("\n");
                    numberLine++;
                }
            } else {
                sequenceBuilder.append(nextline);
                numberLine++;
            }
        }
        reader.close();
        return fastaSequenceList;
    }

    public static void writeTableSummary(List<FastaSequence> list, File outputFile) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

//        StringBuilder outputStringBuilder = new StringBuilder();
        String firstLine = "sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n";
        writer.write(firstLine);

//        outputStringBuilder.append(firstLine);
        for(int i=0; i<list.size(); i++){
            String header = list.get(i).getHeader();
            String sequence = list.get(i).getSequence();
            int numA=0 , numC=0, numG=0, numT=0;
            for (int m=0; m<sequence.length(); m++) {
                char temp = sequence.charAt(m);
                if (temp == 'A') {
                    numA++;
                }
                if (temp == 'C'){
                    numC++;
                }
                if (temp == 'G'){
                    numG++;
                }
                if (temp == 'T'){
                    numT++;
                }
            }
            StringBuilder newLineBuilder = new StringBuilder();
            newLineBuilder.append(header);
            newLineBuilder.append("\t");
            newLineBuilder.append(numA);
            newLineBuilder.append("\t");
            newLineBuilder.append(numC);
            newLineBuilder.append("\t");
            newLineBuilder.append(numG);
            newLineBuilder.append("\t");
            newLineBuilder.append(numT);
            newLineBuilder.append("\t");
            newLineBuilder.append(sequence);
            newLineBuilder.append("\n");
            writer.write(newLineBuilder.toString());
//            outputStringBuilder.append(newLineBuilder);
        }
        writer.flush();
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        List<FastaSequence> fastaList =
                FastaSequence.GetFastaSequences("homework/lab4/MDomestica_first100.fa");

        for (FastaSequence fs : fastaList) {
            System.out.println(fs.getHeader());
            System.out.println(fs.getSequence());
            System.out.println(fs.getGCRatio());
        }

        File myFile = new File("homework/lab4/sequence_summary.txt");
        writeTableSummary(fastaList, myFile);
    }


}
