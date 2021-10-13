package homework.midtermPrepration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    }

    public static void reverseArray(float[] a){
        int lengthArray = a.length;
        float[] b = new float[lengthArray];
        int n = lengthArray;

        for(int i=0; i<lengthArray; i++){
            b[n-1] = a[i];
            n = n-1;
        }
        System.out.println(b);
    }

    public static List<Integer> getEvenNumbers(List<Integer> inList){
        List<Integer> evenList = new ArrayList<>();

        for (int i=0; i<inList.size(); i++){
            if (inList.get(i)%2==0){
                evenList.add(inList.get(i));
            }
        }
        return evenList;
    }

    public static int aMethod(int anInt){
        return anInt;
    }


     //Question 10
    public static void main(String[] args)
    {
        Circle circle = new Circle(5);
//        Shape circle = new Circle(5);
//        System.out.println(1003430/ 1000f);
//        System.out.println(BigDecimal.valueOf(1.00).subtract(BigDecimal.valueOf(9 * .10)));
        List<Integer> a = new ArrayList<>();
        for (int i =0; i< 12; i++){
            a.add(i);
        }
//        System.out.println(getEvenNumbers(a));
//        System.out.println(aMethod(5));
        String num = String.format("%.2f", 1.00-9*.10);
        System.out.println(num);

//        String m1 = "Hello";
//        Integer m = 34;
//        String m2 = "Hello";
//        System.out.println(m1 == m2);
//
//        byte abc = (byte) 383;
//
//        System.out.println(abc);

        String s = "Here is a string";
        Map<Character, Integer> map = new TreeMap<Character, Integer>();
        for(char c : s.toCharArray()){
            if(map.get(c) != null){
                int val = map.get(c);
                val++;
                map.put(c, val);
            }else{
                map.put(c, 1);
            }
        }
        for(Character c : map.keySet()){
            System.out.println(c + " " + map.get(c));
        }
        System.out.println("B".compareTo("A"));

        
        
//        System.out.println(circle.getRadius());
//        Shape2 shape2 = new Circle(5);
//        System.out.println(shape2.getArea());

    }

//    public static void main(String[] args)
//    {
//        int aRadius = 5;
//        Shape shape = new Circle(aRadius);
//        System.out.println(shape.getShapeName());
//    }



//    public static void main(String[] args)
//    {
//        System.out.println("prepare midterm");
//        System.out.println(numGCs("AAAATTTTTGGGGGCCCCCTCTCTCAGAGAATCG"));
//        System.out.println(bothPositive(1, 3));
//        System.out.println(bothPositive(-1, 3));
//        System.out.println(bothPositive(0, 2));
//        skipEveryOther("ADBECIDOEJFLG");
//
//        midtermPrep midtermPrepClass = new midtermPrep();
//        midtermPrepClass.swap(4, 2);
//        Shape shape = new Circle(5);
//    }



}
