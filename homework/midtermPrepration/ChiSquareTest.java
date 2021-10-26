package homework.midtermPrepration;

import java.util.*;

public class ChiSquareTest
{
    public static void main(String[] args)
    {
        List<String> cluster = new ArrayList<>();
        List<String> db = new ArrayList<>();

        cluster.add("dog");
        cluster.add("human");
        cluster.add("human");
        cluster.add("mouse");
        cluster.add("mouse");
        cluster.add("mouse");
        db.add("cat");
        db.add("cat");
        db.add("cat");
        db.add("human");
        db.add("cat");
        db.add("cat");

        double statobs = chi_square_test(cluster, db);
        List<Double> chiSquareStatDistr = new ArrayList<>();

        int iterationNumber = 500;

        for (int i=0; i <iterationNumber; i++){
            List[] shufffledResults = random_shuffle(cluster, db);
            List<String> newCluster = new ArrayList<>(shufffledResults[0]);
            List<String> newDb = new ArrayList<>(shufffledResults[1]);
            chiSquareStatDistr.add(chi_square_test(newCluster, newDb));
        }

        float pvalue = (chiSquareStatDistr.stream().filter(i -> i>=statobs).count()) / (float) iterationNumber;

        System.out.println(pvalue);
    }

    public static List[] random_shuffle(List<String> clusterList, List<String> dbList){
        List<String> newCluster = new ArrayList<>();
        List<String> newDb = new ArrayList<>();

        List<String> mergedList = new ArrayList<>(clusterList);
        mergedList.addAll(dbList);

        Random rand = new Random();

        for (int i=0; i<clusterList.size(); i++){
            String newElement = mergedList.get(rand.nextInt(mergedList.size()));
            newCluster.add(newElement);
            mergedList.remove(newElement);
        }

        for(int i=0; i<dbList.size();i++){
            String newElement = mergedList.get(rand.nextInt(mergedList.size()));
            newDb.add(newElement);
            mergedList.remove(newElement);
        }
        return new List[]{newCluster, newDb};
    }

    public static double chi_square_test(List<String> clusterList, List<String> dbList){
        List<String> mergedList = new ArrayList<>(clusterList);
        mergedList.addAll(dbList);

        Set<String> uniqueElementList = new HashSet<>(mergedList);

        double S = 0;
        for(String s: uniqueElementList){

            double observation = Collections.frequency(clusterList, s) + 0.5;
            double expectation = Collections.frequency(dbList, s) + 0.5;

            S = S + (Math.pow(observation-expectation, 2))/expectation;

        }
        return S;
    }


}
