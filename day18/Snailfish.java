import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

public class Snailfish {
    private static int puzzleOne(String[] numbers) {
        // add all numbers
        String res = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            res = add(res, numbers[i]);
        }

        return magnitude(res);
    }

    private static String add(String n1, String n2) {
        // concat
        return reduce(MessageFormat.format("[{0},{1}]", n1, n2));
    }

    private static String reduce(String n) {
        String oldN = "";
        String newN = n;

        while (!oldN.equals(newN)) {
            oldN = newN;
            // explode
            newN = explode(newN);
            // split if no explosion
            if (oldN.equals(newN)) {
                newN = split(newN);
            }
        }

        // no explosion/split possible
        return newN;
    }

    private static String explode(String n) { // TODO
        return "todo";
    }

    private static String split(String n) { // TODO
        return "todo";
    }

    private static int magnitude(String n) {
        if (n.length() == 1)
            return n.charAt(0) - '0';

        String subs = n.substring(1, n.length() - 1);

        // find comma
        int count = 0;
        char[] chars = subs.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            // found comma that is not in nested number
            if (c == ',' && count == 0) {
                count = i;
                break;
            }
            // bracket galore
            if (c == '[')
                count++;
            if (c == ']')
                count--;
        }

        // recurse
        int magLeft = magnitude(subs.substring(0, count));
        int magRight = magnitude(subs.substring(count + 1));

        return 3 * magLeft + 2 * magRight;
    }

    public static void main(String[] args) throws IOException {
        String file = Files.readString(Path.of("input"));

        String[] numbers = file.split("\r\n");

        System.out.println(puzzleOne(numbers));
    }
}
