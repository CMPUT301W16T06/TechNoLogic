package ca.ualberta.cs.technologic;

/**
 * Created by gknoblau on 2016-03-11.
 */
public class CurrentUser {
    private String currentUser;
    private static CurrentUser firstInstance = null;

    private CurrentUser() {};

    /**
     * Makes a singleton
     * @return
     */
    public static CurrentUser getInstance(){
        if(firstInstance == null){
            synchronized(CurrentUser.class){
                if(firstInstance == null){
                    firstInstance = new CurrentUser();
                }
            }
        }
        return firstInstance;
    }

    /**
     * Sets the current logged in user
     * @param username
     */
    public void setCurrentUser(String username) {
        currentUser = username;
    }

    /**
     * Returns the current logged in user
     * @return
     */
    public String getCurrentUser() {
        return currentUser;
    }
}
