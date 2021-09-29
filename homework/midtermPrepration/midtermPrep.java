package homework.midtermPrepration;

public class midtermPrep
{
    public static int numGCs(String sequence){
        int numberOfGC = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char temp = sequence.charAt(i);
            if (temp == 'G' || temp == 'C') {
                numberOfGC++;
            }
        }
        return numberOfGC;
    }

    public static int bothPositive(int num1, int num2){
        if (num1 > 0 && num2 > 0){
            return 1;
        }else{
            return 0;
        }
    }

    public static void skipEveryOther(String s){
        for(int i=0; i<s.length(); i++){
            if(i % 2 == 0){
                System.out.println(s.charAt(i));
            }
        }
    }

    public void swap(float f1, float f2){
        float temp = f1;
        f1 = f2;
        f2 = temp;

        System.out.println(f1 + ", " +f2);
    }

    public static void main(String[] args)
    {
        System.out.println("prepare midterm");
        System.out.println(numGCs("AAAATTTTTGGGGGCCCCCTCTCTCAGAGAATCG"));
        System.out.println(bothPositive(1, 3));
        System.out.println(bothPositive(-1, 3));
        System.out.println(bothPositive(0, 2));
        skipEveryOther("ADBECIDOEJFLG");

        midtermPrep midtermPrepClass = new midtermPrep();
        midtermPrepClass.swap(4, 2);
    }

}
