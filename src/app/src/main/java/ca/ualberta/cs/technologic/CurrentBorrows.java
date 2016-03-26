package ca.ualberta.cs.technologic;

import java.util.ArrayList;

/**
 * Created by gknoblau on 3/26/16.
 */
public class CurrentBorrows {
    private ArrayList<Borrow> currentBorrows;
    private static CurrentBorrows firstInstance = null;

    private CurrentBorrows() {}


    /**
     * Makes a singleton
     * @return the instance of the Borrows
     */
    public static CurrentBorrows getInstance(){
        if(firstInstance == null){
            synchronized(CurrentUser.class){
                if(firstInstance == null){
                    firstInstance = new CurrentBorrows();
                }
            }
        }
        return firstInstance;
    }

    public ArrayList<Borrow> getCurrentBorrows() {
        return this.currentBorrows;
    }

    public void setCurrentBorrows(ArrayList<Borrow> currentBorrows) {
        this.currentBorrows = currentBorrows;
    }

    public void addCurrentComputer(Borrow newCurrentBorrow){
        this.currentBorrows.add(newCurrentBorrow);
    }
    public void deleteCurrentComputer(Borrow deleteCurrentBorrow){
        this.currentBorrows.remove(deleteCurrentBorrow);
    }
}
