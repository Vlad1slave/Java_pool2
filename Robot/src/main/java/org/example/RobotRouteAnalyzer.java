package org.example;

import java.util.*;

public class RobotRouteAnalyzer {


    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        int numThreads = 1000;
        Thread[] threads = new Thread[numThreads];


        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = countChar(route, 'R');
                updateFrequencyMap(countR);
            });
            threads[i].start();
        }


        for (Thread thread : threads) {
            thread.join();
        }


        printStatistics();
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }


    public static int countChar(String route, char ch) {
        int count = 0;
        for (char c : route.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }


    public static synchronized void updateFrequencyMap(int countR) {
        sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
    }


    public static void printStatistics() {

        Map.Entry<Integer, Integer> maxEntry = null;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

        
        if (maxEntry != null) {
            System.out.println("Самое частое количество повторений " + maxEntry.getKey() + " (встретилось " + maxEntry.getValue() + " раз)");
            System.out.println("Другие размеры:");
            for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                if (!entry.getKey().equals(maxEntry.getKey())) {
                    System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
                }
            }
        }
    }
}