package Builder_Pattern;

public class Main {

    public static void main(String[] args) {
        // write your code here
        StringBuilder builder = new StringBuilder();
        builder.append("This is an example ");
        builder.append("of the builder pattern. ");
        builder.append("It has methods to append ");
        builder.append("almost anything we want to a String. ");
        builder.append(42);

        System.out.println(builder.toString());


        StringBuffer s = new StringBuffer("GeeksforGeeks");
        int p = s.length();
        int q = s.capacity();
        System.out.println("Length of string GeeksforGeeks=" + p);
        System.out.println("Capacity of string GeeksforGeeks=" + q);
    }
}

