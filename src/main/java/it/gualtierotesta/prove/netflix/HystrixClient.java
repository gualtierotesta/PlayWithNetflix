package it.gualtierotesta.prove.netflix;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class HystrixClient extends HystrixCommand<Boolean> {

    private final String url;

    private static final Setter CONFIGURATION =
            Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HystrixClientGroup"))
                    // .andThreadPoolPropertiesDefaults(
                    // HystrixThreadPoolProperties.Setter()
                    // .withCoreSize(10)
                    // )
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                                    // .withExecutionIsolationStrategy(THREAD)
                                    //
                                    // // Per proteggersi da una latenza nella
                                    // risposta superiore alle attese
                                    .withExecutionTimeoutEnabled(true)
                                    .withExecutionTimeoutInMilliseconds(10000)
                                    //
                                    // // Per avere una risposta anche in caso
                                    // di failure
                                    .withFallbackEnabled(true)
                                    // Per interrompere le richieste in caso di
                                    // errori
                                    .withCircuitBreakerEnabled(true)
                                    .withCircuitBreakerRequestVolumeThreshold(2)
                                    .withCircuitBreakerSleepWindowInMilliseconds(5000)
                    // .withCircuitBreakerErrorThresholdPercentage(50)
                    // .withMetricsRollingStatisticalWindowInMilliseconds(10)
                    // // se si sono stati almeno 20 errori negli ultimi 10
                    // secondi con una
                    // percentuale del 50% allora il circuito si interrompe
                    );

    public HystrixClient(String pUrl) {
        super(CONFIGURATION);
        this.url = pUrl;
    }

    @Override
    protected Boolean run() throws Exception {
        System.out.println("*** Invoked run");
        boolean result = sendGet();
        System.out.println("*** Run result=" + result);
        return result;
    }

    @Override
    protected Boolean getFallback() {
        System.out.println("*** Invoked fallback");
        return false;
    }

    private boolean sendGet() throws Exception {

        boolean result = false;
        // try {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        // add request header
        // con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        // System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        // System.out.println(response.toString());
        result = true;
        // } catch (Exception ex) {
        // System.err.println("Failed: " + ex.getMessage());
        // throw new Exception("Failed", ex);
        // }
        return result;

    }
}
