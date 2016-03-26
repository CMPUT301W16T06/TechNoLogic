package ca.ualberta.cs.technologic;

import java.util.ArrayList;

/**
 * Created by gknoblau on 3/26/16.
 */
public class CurrentComputers {
    private ArrayList<Computer> currentComputers;
    private static CurrentComputers firstInstance = null;

    private CurrentComputers() {}

    /**
     * Makes a singleton
     * @return the instance of the computers
     */
    public static CurrentComputers getInstance(){
        if(firstInstance == null){
            synchronized(CurrentUser.class){
                if(firstInstance == null){
                    firstInstance = new CurrentComputers();
                }
            }
        }
        return firstInstance;
    }

    public ArrayList<Computer> getCurrentComputers() {
        return currentComputers;
    }

    public void setCurrentComputers(ArrayList<Computer> currentComputers) {
        this.currentComputers = currentComputers;
    }

    public void addCurrentComputer(Computer newCurrentComputer){
        this.currentComputers.add(newCurrentComputer);
    }
    public void deleteCurrentComputer(Computer deleteCurrentComputer){
        int i = this.currentComputers.indexOf(deleteCurrentComputer);
        this.currentComputers.remove(i);
        //this.currentComputers.remove(deleteCurrentComputer);
    }
}
