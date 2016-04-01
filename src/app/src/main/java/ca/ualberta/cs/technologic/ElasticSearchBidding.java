package ca.ualberta.cs.technologic;

import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * A class for interaction between Bid class and Elastic Search
 *
 * Adapted from https://github.com/earthiverse/lonelyTwitter/blob/complete/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java 2016-03-10
 */
public class ElasticSearchBidding {
    private static JestDroidClient client;

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
            query ="{\"query\":{\"match\":{\"username\":\"" + username + "\"}}}";
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
     * gets all bids for the computer matching the passed in id
     * @param id computer ID
     * @return arraylist of MyBids
     */
    public static ArrayList<Bid> getAllBids(UUID id) {
        verifyClient();

        ArrayList<Bid> bids = new ArrayList<Bid>();

        String query ="{\"query\":{\"match\":{\"computerID\":\"" + id + "\"}}}";

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
     * Get computers
     * returns all computers taht the owner has that have been bid on
     * @param owner
     * @return Arraylist of computers
     */
    public static ArrayList<Computer> getMyItemBids(String owner) {
        verifyClient();

        return ElasticSearchComputer.getComputersBidded(owner);
    }

    public static void placeBid(Bid bid){
        addBid(bid);
        Computer c = ElasticSearchComputer.getComputersById(bid.getComputerID());
        if (c.getStatus() != "bidded"){

            ElasticSearchComputer.updateComputerStatus(bid.getComputerID(), "bidded");
        }
    }

    /**
     * adds a bid into the system and notification
     * @param bid bid to be added
     */
    private static void addBid(Bid bid) {
        verifyClient();

        //add bid to the database
        Index index = new Index.Builder(bid).index("bids").type("bid").build();

        try {
            DocumentResult execute = client.execute(index);
            if(execute.isSucceeded()) {
                //TODO: something
            } else {
                Log.i("what", execute.getJsonString());
                Log.i("what", Integer.toString(execute.getResponseCode()));
            }
        } catch (IOException e) {
            // TODO: Something more useful
            e.printStackTrace();
        }

        //add notification to the database
        Notification n = new Notification(bid.getOwner());
        addNotification(n);
    }

    public static void addNotification(Notification n){
        verifyClient();

        ArrayList<Notification> notifications = new ArrayList<Notification>();
        List<SearchResult.Hit<Map,Void>> hits = null;
        String elasticSearchID;
        Integer previousCount = 0;

        String q ="{\"query\":{\"match\":{\"username\":\"" + n.getUsername() + "\"}}}";
        Search search = new Search.Builder(q).addIndex("notifications").addType("notification").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Notification> found = result.getSourceAsObjectList(Notification.class);
                hits = result.getHits(Map.class);
                notifications.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (notifications.size() > 0 ) {
            previousCount = notifications.get(0).getCount();
            SearchResult.Hit hit = hits.get(0);
            Map source = (Map) hit.source;
            elasticSearchID = (String) source.get(JestResult.ES_METADATA_ID);

            if (elasticSearchID != "") {
                Delete delete = new Delete.Builder(elasticSearchID).index("notifications").type("notification").build();
                try {
                    DocumentResult execute = client.execute(delete);
                    if (execute.isSucceeded()) {
                        //TODO: something
                    } else {
                        Log.i("what", execute.getJsonString());
                        Log.i("what", Integer.toString(execute.getResponseCode()));
                    }
                } catch (IOException e) {
                    // TODO: Something more useful
                    e.printStackTrace();
                }
            }
        }

        //update the notification count
        n.setCount(previousCount + 1);

        Index indexNot = new Index.Builder(n).index("notifications").type("notification").build();
        try {
            DocumentResult execute = client.execute(indexNot);
            if(execute.isSucceeded()) {
                //TODO: something
            } else {
                Log.i("what", execute.getJsonString());
                Log.i("what", Integer.toString(execute.getResponseCode()));
            }
        } catch (IOException e) {
            // TODO: Something more useful
            e.printStackTrace();
        }
    }

    public static Integer getNotifications(String owner){
        verifyClient();

        ArrayList<Notification> notifications = new ArrayList<Notification>();
        List<SearchResult.Hit<Map,Void>> hits = null;
        String elasticSearchID;

        String q ="{\"query\":{\"match\":{\"username\":\"" + owner + "\"}}}";
        Search search = new Search.Builder(q).addIndex("notifications").addType("notification").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Notification> found = result.getSourceAsObjectList(Notification.class);
                hits = result.getHits(Map.class);
                notifications.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (notifications.size() > 0 ) {
            SearchResult.Hit hit = hits.get(0);
            Map source = (Map) hit.source;
            elasticSearchID = (String) source.get(JestResult.ES_METADATA_ID);

            if (elasticSearchID != "") {
                Delete delete = new Delete.Builder(elasticSearchID).index("notifications").type("notification").build();
                try {
                    DocumentResult execute = client.execute(delete);
                    if (execute.isSucceeded()) {
                        //TODO: something
                    } else {
                        Log.i("what", execute.getJsonString());
                        Log.i("what", Integer.toString(execute.getResponseCode()));
                    }
                } catch (IOException e) {
                    // TODO: Something more useful
                    e.printStackTrace();
                }
            }
        }

        if (notifications.size() > 0){
            return notifications.get(0).getCount();
        } else {
            return 0;
        }

    }


    /**
     * for an accepted bid, make a new borrow object
     * remove all bids that correspond to the computer
     * change the staus of the computer from bidded to borrowed
     * @param bidAccepted the bid that was accepted
     * @param allBids all bids that correspond to the computer
     */
    public static void acceptBid(Bid bidAccepted, ArrayList<Bid> allBids, Double longitude, Double latitude){
        //add new entry into borrowing for accpeted bid
        Borrow borrow = new Borrow(bidAccepted.getComputerID(),
                bidAccepted.getUsername(),bidAccepted.getOwner(), longitude, latitude);
        ElasticSearchBorrowing.addBorrow(borrow);

        //change the status of the computer
        ElasticSearchComputer.updateComputerStatus(bidAccepted.getComputerID(), "borrowed");

        //delete all entries from bidding
        for (Bid b : allBids){
            deleteBid(b.getBidID());
        }
    }

    /**
     * delete the bid when it is declined
     * @param declinedBid bid taht was declined
     */
    public static void declineBid(Bid declinedBid, Integer bidCount){
        deleteBid(declinedBid.getBidID());
        if (bidCount == 1){
            ElasticSearchComputer.updateComputerStatus(declinedBid.getComputerID(), "available");
        }
    }

    /**
     * Removes the Bid given the id
     * @param bidID id of the bid to be delete
     */
    private static void deleteBid(UUID bidID) {
        verifyClient();

        ArrayList<Bid> bids = new ArrayList<Bid>();
        List<SearchResult.Hit<Map,Void>> hits = null;
        String elasticSearchID;

        String q ="{\"query\":{\"match\":{\"bidID\":\"" + bidID + "\"}}}";
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

        SearchResult.Hit hit = hits.get(0);
        Map source = (Map)hit.source;
        elasticSearchID = (String) source.get(JestResult.ES_METADATA_ID);

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
            } catch (IOException e) {
                // TODO: Something more useful
                e.printStackTrace();
            }
        }
    }

    /**
     * Verifies the elastic search DB
     */
    private static void verifyClient() {
        if(client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://test-technologic.rhcloud.com");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
