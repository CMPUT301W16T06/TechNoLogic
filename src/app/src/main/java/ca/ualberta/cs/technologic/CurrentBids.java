package ca.ualberta.cs.technologic;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by gknoblau on 3/26/16.
 */
public class CurrentBids {
    private ArrayList<Computer> currentBids;
    private static CurrentBids firstInstance = null;

    private CurrentBids() {}


    /**
     * Makes a singleton
     * @return the instance of the MyBids
     */
    public static CurrentBids getInstance(){
        if(firstInstance == null){
            synchronized(CurrentUser.class){
                if(firstInstance == null){
                    firstInstance = new CurrentBids();
                }
            }
        }
        return firstInstance;
    }

    public ArrayList<Computer> getCurrentBids() {
        return this.currentBids;
    }

    public void setCurrentBids(ArrayList<Computer> currentBids) {
        this.currentBids = currentBids;
    }

    public void addCurrentComputer(Computer newCurrentBid){
        this.currentBids.add(newCurrentBid);
    }

//    public void deleteCurrentComputer(Computer deleteCurrentBid){
//        this.currentBids.remove(deleteCurrentBid);
//    }

    public void deleteCurrentComputer(UUID deleteCurrentComputerID){
        int index = -1;
        for (int i = 0 ; i < this.currentBids.size() ; i++){
            UUID id1 = (this.currentBids.get(i)).getId();
            if (id1.equals(deleteCurrentComputerID)){
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.currentBids.remove(index);
        }
    }

    public void clear() {
        currentBids.clear();
    }
}
