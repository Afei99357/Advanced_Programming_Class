package homework.independent_project;

import java.util.*;

public class ChiSquareTestWithPermutationTest
{
    private static class DbAndClusterValuePair
    {
        private int dbValue;
        private int clusterValue;

        public DbAndClusterValuePair() {
            dbValue = 0;
            clusterValue = 0;
        }

        public DbAndClusterValuePair(int dbValue, int clusterValue) {
            this.dbValue = dbValue;
            this.clusterValue = clusterValue;
        }

        public int getDbValue() {
            return dbValue;
        }

        public int getClusterValue() {
            return clusterValue;
        }

        public void setDbValue(int dbValue) {
            this.dbValue = dbValue;
        }

        public void setClusterValue(int clusterValue) {
            this.clusterValue = clusterValue;
        }
    }

    private static final double EPS = 0.01;

    public static void main(String[] args)
    {
        List<DbAndClusterValuePair> pairsList = new ArrayList<>();

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
        pairsList.add(a3);
        pairsList.add(b3);
        pairsList.add(c3);
        pairsList.add(d3);
        pairsList.add(e3);
        pairsList.add(f3);
        pairsList.add(g3);
        pairsList.add(h3);
        pairsList.add(i3);
        pairsList.add(j3);
        pairsList.add(k3);
        double actualPvalue = 0.3804863995313644;

        // testing the result. In ADAP-KDB, we use junit to test result
        int n =0;
        double sum = 0.0;
        int testTime = 1;
        List<Double> pvalueList = new ArrayList<>();
        for (int i=0; i<testTime; i++){
            double pValue = calculateChiSquaredPermutationStatistics(pairsList);
            sum += pValue;
            pvalueList.add(pValue);

            System.out.println(Math.abs(pValue - actualPvalue) < EPS);
            if (!(Math.abs(pValue - actualPvalue) < EPS)){
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
        System.out.println("Actually p-value is " + actualPvalue);
        System.out.println("Standard deviatoin is " + sd);

    }

    public static double calculateChiSquaredPermutationStatistics(Collection<DbAndClusterValuePair> dbAndClusterValuePairs){
        List<Integer> clusterList = new ArrayList<>();
        List<Integer> dbList = new ArrayList<>();

        int n = 0;
        for (DbAndClusterValuePair dbAndClusterValuePair : dbAndClusterValuePairs) {
            int clusterNum = dbAndClusterValuePair.getClusterValue();
            int dbNum = dbAndClusterValuePair.getDbValue();

            if (clusterNum > 0) {
                for (int i = 0; i < clusterNum; i++) {
                    clusterList.add(n);
                }
            }
            if (dbNum > 0) {
                for (int i = 0; i < dbNum; i++) {
                    dbList.add(n);
                }
            }
            n++;
        }

        double statObs = chi_square_test(clusterList, dbList);

        int iterationNumber = 50000;

        List<Double> chiSquareStatDistr = new ArrayList<>(iterationNumber);

        for (int i = 0; i < iterationNumber; i++) {
            List<Integer> shuffledResults = random_shuffle(clusterList, dbList);
            List<Integer> newCluster = shuffledResults.subList(0, clusterList.size());
            List<Integer> newDb = shuffledResults.subList(clusterList.size(), clusterList.size() + dbList.size());
            chiSquareStatDistr.add(chi_square_test(newCluster, newDb));
        }

        float pvalue = (chiSquareStatDistr.stream().filter(i -> i >= statObs).count()) / (float) iterationNumber;

        return pvalue;
    }

    private static List<Integer> random_shuffle(List<Integer> clusterList, List<Integer> dbList) {
        List<Integer> newClusterList;
        List<Integer> newDbList;

        List<Integer> mergedList = new ArrayList<>(clusterList);
        mergedList.addAll(dbList);

        Collections.shuffle(mergedList);

        newClusterList = mergedList.subList(0,clusterList.size());
        newDbList = mergedList.subList(clusterList.size(), clusterList.size()+dbList.size());

        List<Integer> newMergedList = new ArrayList<>(newClusterList);
        newMergedList.addAll(newDbList);

        return newMergedList;
    }

    public static double chi_square_test(List<Integer> clusterList, List<Integer> dbList){
        List<Integer> mergedList = new ArrayList<>(clusterList);
        mergedList.addAll(dbList);

        Set<Integer> uniqueElementList = new HashSet<>(mergedList);

        double S = 0;
        for(Integer s: uniqueElementList){

            double observation = Collections.frequency(clusterList, s) + 0.5;
            double expectation = Collections.frequency(dbList, s) * (clusterList.size()/(float) dbList.size()) + 0.5;

            S = S + (Math.pow(observation-expectation, 2))/expectation;

        }
        return S;
    }

}
