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

/**
 * Created by Jessica on 2016-03-10.
 */

public class ElasticSearchUser {
    private static JestDroidClient client;
    private static Gson gson;

    /**
     * Get user
     * Can specify the username, gets user if mathces the username
     * If username is "" then all user will be return
     * @param username
     * @return Arraylist of users
     */
    public static ArrayList<User> getUsers(String username) {
        verifyClient();

        ArrayList<User> users = new ArrayList<User>();

        String query = "";
        if (!username.equals("")){
            query = "{\n" +
                    "\"query\": {\n" +
                    "\"term\": { \"username\" : \"" + username + "\" }\n" +
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
            return;
        } catch (IOException e) {
            // TODO: Something more useful
            e.printStackTrace();
        }

    }

    /**
     * Delete the user given the username
     * @param username id of the computer to be delete
     */
    public static void deleteUser(String username) {
        verifyClient();

        Delete delete = new Delete.Builder(username).index("users").type("user").build();
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