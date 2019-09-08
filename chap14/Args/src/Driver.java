import com.cleancode.args.Args;

class Driver {
    public static void main(String[] args) {
        try {
            Args arg = new Args("l,p#,d*", args);
            boolean logging = arg.getBoolean('l');
            int port = arg.getInt('p');
            String directory = arg.getString('d');
            executeApplication(logging, port, directory);
        } catch (Exception e) {
            System.out.printf("Argument error: %s\n%s\n", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executeApplication(boolean logging, int port, String directory){
        System.out.printf("Args: %b %d %s\n", logging, port, directory);
    }
}