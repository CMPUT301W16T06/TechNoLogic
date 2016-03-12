package ca.ualberta.cs.technologic;

/**
 * Created by gknoblau on 2016-03-11.
 */
public class CurrentUser {
    private String currentUser;
    private static CurrentUser firstInstance = null;

    private CurrentUser() {};

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

    public void setCurrentUser(String username) {
        currentUser = username;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
