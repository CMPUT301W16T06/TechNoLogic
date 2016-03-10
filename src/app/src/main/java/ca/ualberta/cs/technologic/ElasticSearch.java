package ca.ualberta.cs.technologic;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ElasticSearch {
    private static JestDroidClient client;
    private static Gson gson;
    private static HttpClient http;

    // TODO: Get computers
    public static ArrayList<Computer> getComputers() {
        verifyClient();

        ArrayList<Computer> computers = new ArrayList<Computer>();

        //Search search = new Search.Builder("").addIndex("technologictesting").addType("computers").build();
        Search search = new Search.Builder("").addIndex("computers").addType("computer").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Computer> found = result.getSourceAsObjectList(Computer.class);
                computers.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return computers;



    }

    public static void verifyClient() {
        if(client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://test-technologic.rhcloud.com");
            //DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");

            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();

        }
    }

}
