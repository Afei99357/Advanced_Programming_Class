package homework.lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomSequenceGeneratorLab3
{
    public static String generateRandomSequence(char[] alphabet, float[] weights, int length) throws Exception
    {
        Float weightsSum = 0f;
        for (int i = 0; i < weights.length; i++) {
            weightsSum += weights[i];
        }
        if (length <= 0) {
            throw new Exception("Exception: length equals to 0!\n");
        }
        if (alphabet.length != weights.length) {
            throw new Exception("Exception: alphabet and weights do not have the same length!\n");
        }
        if (Math.abs(weightsSum - 1) > 0.01) {
            throw new Exception("Exception: the sum of weights is not within round-off error of 1.\n");
        }

        // create a list to save the actual number of each nucleotide / protein
        List<Integer> actualNumberList = new ArrayList<>();

        // add the actual number of each nucleotide / protein to the list, without adding the last one
        for (int i = 0; i < alphabet.length - 1; i++) {
            actualNumberList.add((int) (length * weights[i]));
        }

        //adding the actual number of the last nucleotide / protein to the list
        Integer sum = actualNumberList.stream()
                .collect(Collectors.summingInt(Integer::intValue));
        actualNumberList.add(length - sum);

        // create a StringBuilder to store the actual number of each nucleotide / protein, asn served as
        // the random char pool for generate the certain percentage of each nucleotide / protein
        StringBuilder newAlphabet = new StringBuilder();

        // fill the StringBuilder pool
        for (int i = 0; i < alphabet.length; i++) {
            for (int m = 0; m < actualNumberList.get(i).intValue(); m++) {
                newAlphabet.append(alphabet[i]);
            }
        }

        // create a StringBuilder to store the actual output DNA / protein sequence
        StringBuilder returnAlphabetBuilder = new StringBuilder();
        Random random = new Random();
        for (int m = 0; m < length; m++) {
            int randomIndex = random.nextInt(newAlphabet.length());
            returnAlphabetBuilder.append(newAlphabet.charAt(randomIndex));
            newAlphabet = newAlphabet.deleteCharAt(randomIndex);
        }
        return returnAlphabetBuilder.toString();
    }

    public static void main(String[] args) throws Exception
    {
        float[] dnaWeights = {.3f, .3f, .2f, .2f};
        char[] dnaChars = {'A', 'C', 'G', 'T'};

        // a random DNA 30 mer
        System.out.println(generateRandomSequence(dnaChars, dnaWeights, 30));

        // background rate of residues from https://www.science.org/doi/abs/10.1126/science.286.5438.295
        float proteinBackground[] =
                {0.072658f, 0.024692f, 0.050007f, 0.061087f,
                        0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f,
                        0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f,
                        0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f,
                        0.032955f};


        char[] proteinResidues =
                new char[]{'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
                        'V', 'W', 'Y'};

        // a random protein with 30 residues
        System.out.println(generateRandomSequence(proteinResidues, proteinBackground, 30));

    }
}