package ca.ualberta.cs.technologic;

import java.util.ArrayList;


/**
 * Created by gknoblau on 3/26/16.
 */
public class CurrentBids {
    private ArrayList<Bid> currentBids;
    private static CurrentBids firstInstance = null;

    private CurrentBids() {}


    /**
     * Makes a singleton
     * @return the instance of the Bids
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

    public ArrayList<Bid> getCurrentBids() {
        return this.currentBids;
    }

    public void setCurrentBids(ArrayList<Bid> currentBids) {
        this.currentBids = currentBids;
    }

    public void addCurrentComputer(Bid newCurrentBid){
        this.currentBids.add(newCurrentBid);
    }
    public void deleteCurrentComputer(Bid deleteCurrentBid){
        this.currentBids.remove(deleteCurrentBid);
    }
}
