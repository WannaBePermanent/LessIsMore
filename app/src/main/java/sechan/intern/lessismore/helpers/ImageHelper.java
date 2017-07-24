package sechan.intern.lessismore.helpers;


public class ImageHelper {
    private static ImageHelper instance = null;
    public static ImageHelper getInstance() {
        if (instance == null) {
            instance = new ImageHelper();
        }
        return instance;
    }
    String path;
    String getPath(){

        return null;
    }
}
