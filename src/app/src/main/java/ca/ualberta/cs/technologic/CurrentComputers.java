package ca.ualberta.cs.technologic;

import java.util.ArrayList;
import java.util.UUID;

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
            this.currentComputers.get(index).setStatus(status);
        }
    }

    public void clear() {
        currentComputers.clear();
    }
}
