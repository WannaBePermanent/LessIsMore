package sechan.intern.lessismore.helpers;

public class DBHelper {
    // private final Post instance;
    private static DBHelper instance = null;
    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    /*List<Post> savedLists(){

        return null;
    }*/
    boolean savePost(){

        return false;
    }
    /*Post loadPost(int index){
        return null;

    }*/

}
