import com.cleancode.args.Args;
import com.cleancode.args.ArgsException;

import java.util.Arrays;

class Driver {
    public static void main(String[] args) {
        try {
            Args arg = new Args("l,p#,d*,a[*]", args);
            boolean logging = arg.getBoolean('l');
            int port = arg.getInt('p');
            String[] array = arg.getStringArray('a');
            String directory = arg.getString('d');
            executeApplication(logging, port, directory, array);
        } catch (ArgsException e) {
            System.out.printf("Argument error: %s\n%s\n", e.getClass(), e.getMessage());
            e.printStackTrace();
        }
    }

    private static void executeApplication(boolean logging, int port, String directory, String[] array){
        System.out.printf("Args: %b %d %s %s\n", logging, port, directory, Arrays.toString(array));
    }
}