package gini;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Gini {

    public static void main(String[] args) {
        String filePath = "src\\gini\\playtennis.txt"; 
//        System.out.println(filePath);
//        System.out.println(System.getProperty("user.dir"));
        Map<String, Integer> classCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;  // Skip the header line
                }

                String[] values = line.split(",");  // Assuming values are comma-separated
                String className = values[values.length - 1].trim();  // Assuming the class label is in the last column
                classCounts.put(className, classCounts.getOrDefault(className, 0) + 1);
                //System.out.println(Arrays.toString(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Calculate class probabilities
        double[] classProbabilities = calculateClassProbabilities(classCounts);

        // Calculate and print Gini Index
        double giniIndex = calculateGiniIndex(classProbabilities);
        //System.out.println("Gini Index: " + giniIndex);
    }

    // Calculate class probabilities
    private static double[] calculateClassProbabilities(Map<String, Integer> classCounts) {
    	for (String record: classCounts.keySet()) {
    	    String key = record.toString();
    	    String value = classCounts.get(record).toString();
    	    System.out.println(key + " " + value);
    	}
        double[] classProbabilities = new double[classCounts.size()];
        int totalCount = classCounts.values().stream().mapToInt(Integer::intValue).sum();

        int index = 0;
        for (Map.Entry<String, Integer> entry : classCounts.entrySet()) {
            double probability = (double) entry.getValue() / totalCount;
            classProbabilities[index++] = probability;
        }
        System.out.println(Arrays.toString(classProbabilities));
        return classProbabilities;
    }

    // Calculate Gini Index
    private static double calculateGiniIndex(double[] classDistribution) {
        double giniIndex = 1.0;
        double sumSquaredProbabilities = 0.0;

        for (double probability : classDistribution) {
            sumSquaredProbabilities += Math.pow(probability, 2);
        }

        giniIndex -= sumSquaredProbabilities;
        return giniIndex;
    }
}
