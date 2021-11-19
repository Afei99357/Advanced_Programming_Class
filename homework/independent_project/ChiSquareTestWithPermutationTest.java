package homework.independent_project;

import java.util.*;

public class ChiSquareTestWithPermutationTest
{
    private static final double EPS = 0.01;

    public static void main(String[] args)
    {

        List<DbAndClusterValuePair> pairsList = new ArrayList<>();
        List<DbAndClusterValuePair> pairsList2 = new ArrayList<>();

        // Test input from real data
        DbAndClusterValuePair single_quadrupole = new DbAndClusterValuePair(13, 12);
        DbAndClusterValuePair gc_tof = new DbAndClusterValuePair(29, 0);
        DbAndClusterValuePair single_quadruple = new DbAndClusterValuePair(13, 2);
        DbAndClusterValuePair gc_ion_trap = new DbAndClusterValuePair(6, 0);
        DbAndClusterValuePair gc_x_gc_tof = new DbAndClusterValuePair(7, 1);
        pairsList.add(single_quadrupole);
        pairsList.add(gc_tof);
        pairsList.add(single_quadruple);
        pairsList.add(gc_ion_trap);
        pairsList.add(gc_x_gc_tof);
        double actualPvalue = 0.0002;

        // Test data 2
        DbAndClusterValuePair a3 = new DbAndClusterValuePair(38, 12);
        DbAndClusterValuePair b3 = new DbAndClusterValuePair(2, 0);
        DbAndClusterValuePair c3 = new DbAndClusterValuePair(1, 1);
        DbAndClusterValuePair d3 = new DbAndClusterValuePair(1, 0);
        DbAndClusterValuePair e3 = new DbAndClusterValuePair(1, 1);
        DbAndClusterValuePair f3 = new DbAndClusterValuePair(4, 0);
        DbAndClusterValuePair g3 = new DbAndClusterValuePair(1, 0);
        DbAndClusterValuePair h3 = new DbAndClusterValuePair(1, 0);
        DbAndClusterValuePair i3 = new DbAndClusterValuePair(1, 0);
        DbAndClusterValuePair j3 = new DbAndClusterValuePair(1, 0);
        DbAndClusterValuePair k3 = new DbAndClusterValuePair(11, 0);
        pairsList2.add(a3);
        pairsList2.add(b3);
        pairsList2.add(c3);
        pairsList2.add(d3);
        pairsList2.add(e3);
        pairsList2.add(f3);
        pairsList2.add(g3);
        pairsList2.add(h3);
        pairsList2.add(i3);
        pairsList2.add(j3);
        pairsList2.add(k3);
        double actualPvalue2 = 0.3086;

        int n =0;
        double sum = 0.0;
        int testTime = 50;
        List<Double> pvalueList = new ArrayList<>();
        for (int i=0; i<testTime; i++){
            double pValue = calculateChiSquaredPermutationStatistics(pairsList2);
            sum += pValue;
            pvalueList.add(pValue);

            System.out.println(Math.abs(pValue - actualPvalue2) < EPS);
            if (!(Math.abs(pValue - actualPvalue2) < EPS)){
                n++;
            }
        }
        double mean = sum/testTime;



        double sd =0.0;

        for (double i: pvalueList){
            sd += Math.pow(i-mean, 2);
        }

        sd = Math.sqrt(sd/testTime);


        System.out.println("total fail times are " + n);
        System.out.println("The average p-Value is " + mean);
        System.out.println("Actually p-value is " + actualPvalue2);
        System.out.println("Standard deviatoin is " + sd);

    }

    public static double calculateChiSquaredPermutationStatistics(Collection<DbAndClusterValuePair> dbAndClusterValuePairs){
        List<Integer> clusterList = new ArrayList<>();
        List<Integer> dbList = new ArrayList<>();

        int n = 0;
        for (DbAndClusterValuePair dbAndClusterValuePair : dbAndClusterValuePairs) {
            int clusterNum = dbAndClusterValuePair.getClusterValue();
            int dbNum = dbAndClusterValuePair.getDbValue();

            if(clusterNum>0){
                for(int i=0; i<clusterNum; i++){
                    clusterList.add(n);
                }
            }
            if(dbNum>0){
                for(int i=0; i<dbNum; i++){
                    dbList.add(n);
                }
            }
            n++;
        }

        double statObs = chi_square_test(clusterList, dbList);
        List<Double> chiSquareStatDistr = new ArrayList<>();

        int iterationNumber = 50000;

        for (int i=0; i <iterationNumber; i++){
            List[] shuffledResults = random_shuffle(clusterList, dbList);
            List<Integer> newCluster = new ArrayList<>(shuffledResults[0]);
            List<Integer> newDb = new ArrayList<>(shuffledResults[1]);
            chiSquareStatDistr.add(chi_square_test(newCluster, newDb));
        }

        float pvalue = (chiSquareStatDistr.stream().filter(i -> i>=statObs).count()) / (float) iterationNumber;

        return pvalue;
    }

    public static List[] random_shuffle(List<Integer> clusterList, List<Integer> dbList){
        List<Integer> newCluster = new ArrayList<>();
        List<Integer> newDb = new ArrayList<>();

        List<Integer> mergedList = new ArrayList<>(clusterList);
        mergedList.addAll(dbList);

        Random rand = new Random();

        for (int i=0; i<clusterList.size(); i++){
            int newElement = mergedList.get(rand.nextInt(mergedList.size()));
            newCluster.add(newElement);
            mergedList.remove(newElement);
        }

        for(int i=0; i<dbList.size();i++){
            Integer newElement = mergedList.get(rand.nextInt(mergedList.size()));
            newDb.add(newElement);
            mergedList.remove(newElement);
        }
        return new List[]{newCluster, newDb};
    }

    public static double chi_square_test(List<Integer> clusterList, List<Integer> dbList){
        List<Integer> mergedList = new ArrayList<>(clusterList);
        mergedList.addAll(dbList);

        Set<Integer> uniqueElementList = new HashSet<>(mergedList);

        double S = 0;
        for(Integer s: uniqueElementList){

            double observation = Collections.frequency(clusterList, s) + 0.5;
            double expectation = Collections.frequency(dbList, s) + 0.5;

            S = S + (Math.pow(observation-expectation, 2))/expectation;

        }
        return S;
    }


}
