package Singleton_Pattern;

public class DbSingleton {

    private static  DbSingleton instance = null;

    private  DbSingleton(){
    }

    public static DbSingleton getInstance(){
        if (instance == null){
            instance = new DbSingleton();
        }
        return instance;
    }

}
