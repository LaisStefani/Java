package Singleton_Pattern;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Runtime  singletonRuntime = Runtime.getRuntime();
        singletonRuntime.gc();
        System.out.println(singletonRuntime);

        Runtime anotherInstance;
        anotherInstance = Runtime.getRuntime();
        System.out.println(anotherInstance);

        if (singletonRuntime == anotherInstance){
            System.out.println("They are the same instance");
        }

    }
}
