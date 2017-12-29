package it.gualtierotesta.prove.netflix;

import java.util.Date;

public class HystrixMain {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 10000; i += 1) {
            System.out.println("\n\n--------------------- Loop : " + i + " at " + new Date().toString());
            boolean result = HystrixMain.doAction("http://localhost:8080/metrics");
            System.out.println("Result: " + result);
            Thread.sleep(1000);
        }
    }

    private static boolean doAction(String s) {
        return new HystrixClient(s).execute();
    }

}
