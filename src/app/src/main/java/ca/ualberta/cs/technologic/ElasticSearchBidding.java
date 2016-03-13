package ca.ualberta.cs.technologic;

import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Jessica on 2016-03-10.
 */
public class ElasticSearchBidding {
    private static JestDroidClient client;
    private static Gson gson;

    /**
     * Get bids
     * Can specify the username, gets all the bids that a user has made on other computers
     * If username is "" then all bids will be return
     * @param username
     * @return Arraylist of bids
     */
    public static ArrayList<Bid> getMyBids(String username) {
        verifyClient();

        ArrayList<Bid> bids = new ArrayList<Bid>();

        String query = "{\n" +
                "\"size\" : 1000\n" +
                "}";
        if (username != ""){
            query = "{\n" +
                    "\"size\" : 1000,\n" +
                    "\"query\": {\n" +
                    "\"term\": { \"username\" : \"" + username + "\" }\n" +
                    "}\n" +
                    "}\n";
        }
        Search search = new Search.Builder(query).addIndex("bids").addType("bid").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Bid> found = result.getSourceAsObjectList(Bid.class);

                bids.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bids;
    }

    /**
     * Get bids
     * Can specify the username, gets all the bids on the users computers
     * that other users have made
     * If username is "" then all bids will be return
     * @param owner
     * @return Arraylist of bids
     */
    public static ArrayList<Bid> getMyItemBids(String owner) {
        verifyClient();

        ArrayList<Bid> bids = new ArrayList<Bid>();

        String query = "{\n" +
                "\"size\" : 1000\n" +
                "}";
        if (owner != ""){
            query = "{\n" +
                    "\"size\" : 1000,\n" +
                    "\"query\": {\n" +
                    "\"term\": { \"owner\" : \"" + owner + "\" }\n" +
                    "}\n" +
                    "}\n";
        }
        Search search = new Search.Builder(query).addIndex("bids").addType("bid").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Bid> found = result.getSourceAsObjectList(Bid.class);

                bids.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bids;
    }

    public static void placeBid(Bid bid){
        addBid(bid);
        if (ElasticSearchComputer.getComputersById(bid.getComputerID()).getStatus() != "bidded"){
            ElasticSearchComputer.updateComputerStatus(bid.getComputerID(), "bidded");
        }
    }

    /**
     * adds a bid into the system
     * @param bid bid to be added
     */
    public static void addBid(Bid bid) {
        verifyClient();

        Index index = new Index.Builder(bid).index("bids").type("bid").build();

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

    /**
     * Removes the Bid given the id
     * @param bidID id of the bid to be delete
     */
    public static void deleteBid(String bidID) {
        verifyClient();

        ArrayList<Bid> bids = new ArrayList<Bid>();
        List<SearchResult.Hit<Map,Void>> hits = null;
        String elasticSearchID = "";
        String q = "{\n" +
                "\"size\" : 1000\n" +
                "}";

        Search search = new Search.Builder(q).addIndex("bids").addType("bid").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Bid> found = result.getSourceAsObjectList(Bid.class);
                hits = result.getHits(Map.class);
                bids.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < bids.size(); i++){
            SearchResult.Hit hit = hits.get(i);
            Map source = (Map)hit.source;
            Set<Map.Entry> s =  source.entrySet();


            for (Map.Entry entry : s) {
                if (entry.getKey().equals("bidID")) {
                    if (entry.getValue().equals(bidID)) {
                        elasticSearchID = (String) source.get(JestResult.ES_METADATA_ID);

                    }
                }
            }
        }

        if (elasticSearchID != "") {
            Delete delete = new Delete.Builder(elasticSearchID).index("bids").type("bid").build();
            try {
                DocumentResult execute = client.execute(delete);
                if (execute.isSucceeded()) {
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
    }

    /**
     * Verifies the elastic search DB
     */
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
