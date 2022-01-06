package edu.usfca.vinh;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class HttpUrlConnection {
    public static void main(String[] args) {
        //http("https://www.theaudiodb.com/api/v1/json/2/search.php?s=coldplay");
        http();
    }
    public static void http() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("https://www.theaudiodb.com/api/v1/json/2/search.php?s=coldplay");
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);

            if (result.getStatusLine().getStatusCode() < 299) {
                String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                System.out.println(json);
            } else {
                System.out.println("Lost connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
