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
import java.util.UUID;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Jessica on 2016-03-10.
 *
 * Adapted from https://github.com/earthiverse/lonelyTwitter/blob/complete/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java 2016-03-10
 */
public class ElasticSearchBorrowing {
    private static JestDroidClient client;
    private static Gson gson;

    /**
     * adds a borrow object into the system
     * @param borrow bid to be added
     */
    public static void addBorrow(Borrow borrow) {
        verifyClient();

        Index index = new Index.Builder(borrow).index("borrows").type("borrow").build();

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
     * Get computers that the user is currently borrowing from other users
     * @param username
     * @return Arraylist of borrows
     */
    public static ArrayList<Borrow> getMyBorrows(String username) {
        verifyClient();

        ArrayList<Borrow> borrows = new ArrayList<Borrow>();

        String query = "";
        if (username != ""){
            query ="{\"query\":{\"match\":{\"username\":\"" + username + "\"}}}";
        }

        Search search = new Search.Builder(query).addIndex("borrows").addType("borrow").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Borrow> found = result.getSourceAsObjectList(Borrow.class);

                borrows.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return borrows;
    }

    /**
     * Get computers that the user owns that are lent out to other users
     * @param owner
     * @return Arraylist of borrows
     */
    public static ArrayList<Borrow> getLentOut(String owner) {
        verifyClient();

        ArrayList<Borrow> borrows = new ArrayList<Borrow>();

        String query = "";
        if (owner != ""){
            query ="{\"query\":{\"match\":{\"owner\":\"" + owner + "\"}}}";
        }
        Search search = new Search.Builder(query).addIndex("borrows").addType("borrow").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Borrow> found = result.getSourceAsObjectList(Borrow.class);

                borrows.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    /**
     * when a computer is returned the borrow entry is removed
     * and the computer status is changed to available
     * @param borrow entry to be removed
     */
    public static void returnComputer(Borrow borrow){
        removeBorrow(borrow.getBorrowID());
        ElasticSearchComputer.updateComputerStatus(borrow.getComputerID(),"available");
    }

    /**
     * remove the borrow entry from the system
     * @param borrowID
     */
    public static void removeBorrow(UUID borrowID){
        verifyClient();

        ArrayList<Borrow> borrows = new ArrayList<Borrow>();
        List<SearchResult.Hit<Map,Void>> hits = null;
        String elasticSearchID = "";

        String q ="{\"query\":{\"match\":{\"borrowID\":\"" + borrowID + "\"}}}";
        Search search = new Search.Builder(q).addIndex("borrows").addType("borrow").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Borrow> found = result.getSourceAsObjectList(Borrow.class);
                hits = result.getHits(Map.class);
                borrows.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchResult.Hit hit = hits.get(0);
        Map source = (Map)hit.source;
        elasticSearchID = (String) source.get(JestResult.ES_METADATA_ID);

        if (elasticSearchID != "") {
            Delete delete = new Delete.Builder(elasticSearchID).index("borrows").type("borrow").build();
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
