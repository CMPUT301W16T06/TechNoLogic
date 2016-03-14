package ca.ualberta.cs.technologic;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jordan on 08/02/2016.
 */
public class ComputerTest extends ActivityInstrumentationTestCase2 {

    public ComputerTest() {
        super(Computer.class);
    }

    //Test to make sure the status of a Computer can only be Available, Bidded or Borrowed
    //Test for corresponding US 2.01.01 (View_Status)
    //***EDITED
    public void testsetStatus(){
        User user1 = new User("Tom");


        Computer testcomputer1 = new Computer(UUID.randomUUID(),"cooljohn123", "Microsoft",
                "surface",2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available");

        //Computer testcomputer2 = new Computer("bob", "Microsoft", "surface", 2014, "intel i7", 8,
              //  500, "windows", Float.parseFloat("34.2"), "this is a cool computer");

        testcomputer1.setStatus("bidded");
        assertEquals("bidded",testcomputer1.getStatus());

        try {
            testcomputer1.setStatus("Hello");
        } catch (IllegalArgumentException e){
            assertTrue(Boolean.TRUE);
        }
    }

    //Test to make sure a user can attach a photograph to a computer object
    //Test for corresponding US 09.01.01 (Attach_photo)
    //***EDIT
    public void testAttachPhoto(){
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",
                2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available");

        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).takePhoto();

        //Photos not implemented in the computer class yet
        assertNotSame(testcomputer1.getPhoto(),null);

    }

    //Test to make sure the user can delete any attached photo for a thing of theirs
    //Test for corresponding US 09.02.01 (Delete_photo)
    //***EDIT
    public void testDeletePhoto(){
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",
                2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available");

        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).takePhoto();
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).deletePhoto();
        assertEquals(testcomputer1, ElasticSearchComputer.getComputers(testcomputer1.getUsername()));
    }

    //Test to make sure the user can view any attached photograph for a computer object
    //Test for corresponding US 09.03.01 (View_photo)
    //***EDIT
    public void testViewPhoto(){
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",
                2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available");

        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).takePhoto();

        //assertEquals photo view, photo taken
    }

    //Test to make sure the size of all photos are under 65536 bytes
    //Test for corresponding US 09.04.01 (Check_Photo_Size)
    //***EDIT
    public void testCheckPhotoSize(){
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",
                2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available");

        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).takePhoto();
        assertTrue(ElasticSearchComputer.getComputersById(testcomputer1.getId()).getPhotoSize() > 65536);
    }

    //Test to make sure the user can set a location
    //Test for corresponding US 10.01.01 (Attach_Location)
    //***EDIT
    public void testAttachLocation(){
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",
                2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available");

        //setting status to bidded
        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).setStatus("bidded");

        //set location
        Location location = new Location("T6R 0M7");
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).setLocation(location);
        assertEquals("T6R 0M7", ElasticSearchComputer.getComputersById(testcomputer1.getId()).getLocation());
    }

    //Test to make sure the location of the computer that will be borrowed can be seen
    //Test for corresponding US 10.02.01 (View_Location)
    //***EDIT
    public void testViewLocation(){
        //initial setup of test variables
        ElasticSearchComputer ElasticSearchComputer = new ElasticSearchComputer();
        User testUser = new User("cooljohn123");
        Computer testcomputer1 = null;
        testcomputer1 = new Computer(testcomputer1.getId(), "cooljohn123", "Microsoft","surface",
                2014,"intel i7", 8, 500,"windows",Float.parseFloat("34.2"),
                "this is a cool computer", "available");
        ArrayList<Computer> testArray = new ArrayList<Computer>();

        //setting status to bidded
        ElasticSearchComputer.addComputer(testcomputer1);
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).setStatus("bidded");

        //set location
        Location location = new Location("T6R 0M7");
        ElasticSearchComputer.getComputersById(testcomputer1.getId()).setLocation(location);
        assertEquals("T6R 0M7", ElasticSearchComputer.getComputersById(testcomputer1.getId()).getLocation());
    }
}
