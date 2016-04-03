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
 * A class for interaction between User class and Elastic Search *
 *
 * Adapted from https://github.com/earthiverse/lonelyTwitter/blob/complete/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java 2016-03-10
 *
 * This class holds methods for creating and executing an Elastic Search query
 */

public class ElasticSearchUser {
    private static JestDroidClient client;
    private static Gson gson;

    /**
     * Get user
     * Can specify the username, gets user if mathces the username
     * If username is "" then all user will be return
     * @param username username to search
     * @return Arraylist of users
     */
    public static ArrayList<User> getUsers(String username) {
        verifyClient();

        ArrayList<User> users = new ArrayList<User>();

        String query = "";
        if (!username.equals("")){
            query = "{\n" +
                    "\"query\": {\n" +
                    "\"match\": { \"username\" : \"" + username + "\" }\n" +
                    "}\n" +
                    "}\n";
        }
        Search search = new Search.Builder(query).addIndex("users").addType("user").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<User> found = result.getSourceAsObjectList(User.class);
                users.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * adds a user into the system
     * @param user the user to be added
     */
    public static void addUser(User user) {
        verifyClient();

        Index index = new Index.Builder(user).index("users").type("user").build();

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

    }

    /**
     * Delete the user given the username
     * @param id of the User to be delete
     */
    public static void deleteUser(UUID id) {
        verifyClient();


        ArrayList<User> users = new ArrayList<User>();
        List<SearchResult.Hit<Map,Void>> hits = null;
        String elasticSearchID;

        String q ="{\"query\":{\"match\":{\"id\":\"" + id + "\"}}}";
        Search search = new Search.Builder(q).addIndex("users").addType("user").build();
        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<User> found = result.getSourceAsObjectList(User.class);
                users.addAll(found);

                hits = result.getHits(Map.class);
                users.addAll(found);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchResult.Hit hit = hits.get(0);
        Map source = (Map)hit.source;
        elasticSearchID = (String) source.get(JestResult.ES_METADATA_ID);

        Delete delete = new Delete.Builder(elasticSearchID).index("users").type("user").build();
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

    public static void updateUser(User oldUser, User newUser) {
        deleteUser(oldUser.getId());
        addUser(newUser);
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