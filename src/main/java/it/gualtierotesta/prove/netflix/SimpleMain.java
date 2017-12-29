package it.gualtierotesta.prove.netflix;

import java.util.Date;

public class SimpleMain {

    public static void main(String[] args) throws Exception {

        // boolean result =
        // SimpleMain.doAction("http://localhost:8080/metrics");
        // System.out.println("Result: " + result);

        for (int i = 0; i <= 100; i += 1) {
            System.out.println("\n\n--------------------- Loop : " + i + " at " + new Date().toString());
            boolean result = SimpleMain.doAction("http://localhost:8080/metrics");
            System.out.println("Result: " + result);
            Thread.sleep(500);
        }
    }

    private static boolean doAction(String s) {
        return new SimpleClient(s).sendGet();
    }

}
