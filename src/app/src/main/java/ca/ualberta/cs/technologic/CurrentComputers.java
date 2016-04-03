package ca.ualberta.cs.technologic;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A singleton class that gets all the computers
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

    /**
     * gets the singleton of computers for a user
     * @return arraylist of computers
     */
    public ArrayList<Computer> getCurrentComputers() {
        return currentComputers;
    }

    /**
     * set the singleton to the given computers
     * @param currentComputers computers for the singleton
     */
    public void setCurrentComputers(ArrayList<Computer> currentComputers) {
        this.currentComputers = currentComputers;
    }

    /**
     * adds computers to the singleton
     * @param newCurrentComputer computer to add
     */
    public void addCurrentComputer(Computer newCurrentComputer){
        this.currentComputers.add(newCurrentComputer);
    }

    /**
     * delete a computer from the singleton
     * @param deleteCurrentComputer computer to delete
     */
    public void deleteCurrentComputer(Computer deleteCurrentComputer){
        int index = -1;
        for (int i = 0 ; i < this.currentComputers.size() ; i++){
            UUID id1 = (this.currentComputers.get(i)).getId();
            UUID id2 = deleteCurrentComputer.getId();
            if (id1.equals(id2)){
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.currentComputers.remove(index);
        }
    }

    /**
     * updates the singleton when a computers status changes
     * @param compID computer that has changed its status
     * @param status current status of the computer
     */
    public void updateComputerStatus(UUID compID, String status){
        int index = -1;
        for (int i = 0 ; i < this.currentComputers.size() ; i++){
            UUID id1 = (this.currentComputers.get(i)).getId();
            UUID id2 = compID;
            if (id1.equals(id2)){
                index = i;
                break;
            }
        }
        if (index >= 0) {
            Computer c = this.currentComputers.get(index);
            c.setStatus(status);
            this.currentComputers.remove(index);
            this.currentComputers.add(c);
            //this.currentComputers.get(index).setStatus(status);
        }
    }

    public void clear() {
        currentComputers.clear();
    }
}
