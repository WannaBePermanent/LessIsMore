package sechan.intern.lessismore;



public class LimRepo {
//Singleton Pattern 적용, Factory Static Method
private static LimRepo instance = null;
    public static LimRepo getInstance() {
        if (instance == null) {
            instance = new LimRepo();
        }
        return instance;
    }

}
