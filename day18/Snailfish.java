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

    private static int puzzleTwo(String[] numbers) {
        int max = 0;

        // try every combination
        for (String n1 : numbers) {
            for (String n2 : numbers) {
                max = Math.max(max, magnitude(add(n1, n2)));
            }
        }

        return max;
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
        // find beginning of 4 times nested block
        char[] cs = n.toCharArray();

        int iBlockStart = 0;
        for (int i = 0; i < cs.length; i++) {
            // found nested block
            if (iBlockStart > 4) {
                iBlockStart = i;
                break;
            }

            // bracket galore
            char c = cs[i];
            if (c == '[')
                iBlockStart++;
            if (c == ']')
                iBlockStart--;
        }

        // no explosions
        if (iBlockStart == 0)
            return n;

        iBlockStart--;

        // get numbers for explosion
        int num1 = 0;
        int iComma;
        for (iComma = iBlockStart + 1; cs[iComma] != ','; iComma++) {
            num1 *= 10;
            num1 += cs[iComma] - '0';
        }

        int num2 = 0;
        int iBlockEnd;
        for (iBlockEnd = iComma + 1; cs[iBlockEnd] != ']'; iBlockEnd++) {
            num2 *= 10;
            num2 += cs[iBlockEnd] - '0';
        }

        // propagate left
        int iLeftLow;
        for (iLeftLow = iBlockStart; iLeftLow >= 0; iLeftLow--) {
            if (Character.isDigit(cs[iLeftLow]))
                break;
        }

        // propagate right
        int iRightHigh;
        for (iRightHigh = iBlockEnd; iRightHigh < cs.length; iRightHigh++) {
            if (Character.isDigit(cs[iRightHigh]))
                break;
        }

        // builder
        StringBuilder sb = new StringBuilder();

        // left
        if (iLeftLow != -1) {
            // left hit number
            int iLeftFront;
            int left = 0;
            int offset = 1;
            for (iLeftFront = iLeftLow; Character.isDigit(cs[iLeftFront]); iLeftFront--) {
                left += offset * (cs[iLeftFront] - '0');
                offset *= 10;
            }

            sb.append(n, 0, iLeftFront + 1);
            sb.append(left + num1);
            sb.append(n, iLeftLow + 1, iBlockStart);
        } else {
            sb.append(n, 0, iBlockStart);
        }

        // middle
        sb.append('0');

        // right
        if (iRightHigh != cs.length) {
            // right hit number
            int iRightBack;
            int right = 0;
            for (iRightBack = iRightHigh; Character.isDigit(cs[iRightBack]); iRightBack++) {
                right *= 10;
                right += cs[iRightBack] - '0';
            }

            sb.append(n, iBlockEnd + 1, iRightHigh);
            sb.append(right + num2);
            sb.append(n, iRightBack, cs.length);
        } else {
            sb.append(n, iBlockEnd + 1, cs.length);
        }

        return sb.toString();
    }

    private static String split(String n) {
        char[] cs = n.toCharArray();

        // find leftmost splittable number
        int i;
        for (i = 0; i < cs.length - 1; i++) {
            if (Character.isDigit(cs[i]) && Character.isDigit(cs[i + 1]))
                break;
        }

        // no split necessary
        if (i == cs.length - 1)
            return n;

        // number to split
        int num = 0;
        int j;
        for (j = i; Character.isDigit(cs[j]); j++) {
            num *= 10;
            num += cs[j] - '0';
        }

        // split
        return n.substring(0, i) +
                '[' +
                (num >> 1) +
                ',' +
                (num + 1 >> 1) +
                ']' +
                n.substring(j);
    }

    private static int magnitude(String n) {
        if (n.length() == 1)
            return n.charAt(0) - '0';

        String subs = n.substring(1, n.length() - 1);

        // find comma
        int count = 0;
        char[] cs = subs.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
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
        System.out.println(puzzleTwo(numbers));
    }
}
