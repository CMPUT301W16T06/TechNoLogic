package ca.ualberta.cs.technologic;

import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by Jessica on 2016-03-10.
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
