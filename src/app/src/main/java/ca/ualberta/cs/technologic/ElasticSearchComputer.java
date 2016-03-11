package ca.ualberta.cs.technologic;

import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.core.search.aggregation.MetricAggregation;

/**
 * Created by Jessica on 2016-03-10.
 */

public class ElasticSearchComputer {
    private static JestDroidClient client;
    private static Gson gson;

    /**
     * Get computers
     * Can specify the username, gets all the computers belonging to that user
     * If username is "" then all computers will be return
     * @param username
     * @return Arraylist of computers
     */
    public static ArrayList<Computer> getComputers(String username) {
        verifyClient();

        ArrayList<Computer> computers = new ArrayList<Computer>();

        String query = "";
        if (username != ""){
            query = "{\n" +
                    "\"query\": {\n" +
                    "\"term\": { \"username\" : \"" + username + "\" }\n" +
                    "}\n" +
                    "}\n";
        }
        Search search = new Search.Builder(query).addIndex("computers").addType("computer").build();
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


    /**
     * adds a computer into the system
     * @param computer computer to be added
     */
    public static void addComputer(Computer computer) {
        verifyClient();

        Index index = new Index.Builder(computer).index("computers").type("computer").build();

        try {
            DocumentResult execute = client.execute(index);
            if(execute.isSucceeded()) {
                //TODO: something
            } else {
                Log.i("what", execute.getJsonString());
                Log.i("what", Integer.toString(execute.getResponseCode()));
            }
            return;
        } catch (IOException e) {
            // TODO: Something more useful
            e.printStackTrace();
        }

    }

    //DOESN'T WORK
    public static void updateComputer(String id) {
        verifyClient();

        String query = "";
        query = "{\n" +
                "    \"script\" : \"ctx._source.tags += tag\",\n" +
                "    \"params\" : {\n" +
                "        \"tag\" : \"1\"\n" +
                "    }\n" +
                "}";

        Update update = new Update.Builder(query).index("computers").type("computers").id(id).build();
        try {
            DocumentResult execute = client.execute(update);
            if(execute.isSucceeded()) {
                //TODO: something
            } else {
                Log.i("what", execute.getJsonString());
                Log.i("what", Integer.toString(execute.getResponseCode()));
            }
            return;
        } catch (IOException e) {
            // TODO: Something more useful
            e.printStackTrace();
        }
    }

    /**
     * Delete the computer given the id
     * @param id id of the computer to be delete
     */
    public static void deleteComputer(String id) {
        verifyClient();

        Delete delete = new Delete.Builder(id).index("computers").type("computer").build();
        try {
            DocumentResult execute = client.execute(delete);
            if(execute.isSucceeded()) {
                //TODO: something
            } else {
                Log.i("what", execute.getJsonString());
                Log.i("what", Integer.toString(execute.getResponseCode()));
            }
            return;
        } catch (IOException e) {
            // TODO: Something more useful
            e.printStackTrace();
        }
    }


    public static void verifyClient() {
        if(client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://test-technologic.rhcloud.com");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}
