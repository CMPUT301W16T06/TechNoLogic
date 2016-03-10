package ca.ualberta.cs.technologic;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Eric on 2016-03-08.
 */
public class ElasticsearchController {
    private static JestDroidClient JDC;

    public static void verifyConfig() {
        if (JDC == null) {
            DroidClientConfig.Builder bconfig = new DroidClientConfig.Builder("http://test-technologic.rhcloud.com");
            DroidClientConfig config = bconfig.build();

            JestClientFactory fact = new JestClientFactory();
            fact.setDroidClientConfig(config);
            JDC = (JestDroidClient) fact.getObject();
        }
    }
    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... strings) {
            verifyConfig();
            String search_string = strings[0];
            ArrayList<User> foundUsers = new ArrayList<User>();

            Search search = new Search.Builder(search_string).addIndex("pc4hire").addType("user").build();
            try {
                SearchResult execute = JDC.execute(search);
                if (execute.isSucceeded()) {
                    List<User> found = execute.getSourceAsObjectList(User.class);
                    foundUsers.addAll(found);
                } else {
                    Log.i("TODO", "Search failed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return foundUsers;
        }
    }
}
