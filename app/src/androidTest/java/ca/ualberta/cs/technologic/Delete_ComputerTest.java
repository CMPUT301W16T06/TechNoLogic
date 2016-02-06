package ca.ualberta.cs.technologic;

import android.test.ActivityInstrumentationTestCase2;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jordan on 06/02/2016.
 */
public class Delete_ComputerTest extends ActivityInstrumentationTestCase2 {
    public Delete_ComputerTest(Class activityClass) {
        super(activityClass);
    }

    public void testDelete_Computer() {
        //Add new user, because user contains the array of computers
        User testUser = new User("John", "cooljohn123", "john@gmail.com", "403-132-4567",
                "password", "123 7th Ave NE Calgary");

        ArrayList<Computer> temp_array = testUser.getCmputArray();
        ArrayList<Computer> test_array = new ArrayList<Computer>();
        Computer testcomputer = new Computer("cmput 0", "500gb", "8gb", "intel core i7",
                "surface pro 3", "microsoft", "windows", 2014);
        Computer testcomputer1 = new Computer("cmput 1", "500gb", "8gb", "intel core i7",
                "surface pro 3", "microsoft", "windows", 2014);
        Computer testcomputer2 = new Computer("cmput 2", "500gb", "8gb", "intel core i7",
                "surface pro 3", "microsoft", "windows", 2014);


        // test to make sure the right index is deleted
        temp_array.add(testcomputer);
        temp_array.add(testcomputer1);
        temp_array.add(testcomputer2);

        test_array.add(testcomputer);
        test_array.add(testcomputer2);

        temp_array.remove(1);

        assertEquals(temp_array,test_array);

        // test to make sure all the computers can be deleted
        temp_array.remove(0);
        temp_array.remove(0);

        assertEquals(0,temp_array.size());
    }
}
