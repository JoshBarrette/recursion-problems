package edu.uwp.cs.csci242.assignments.ec1.recursion;

/**
 * This assignment is meant to show the uses of recursion.
 *<p>
 *   We use recursion to "clean" a string so that there are no more instances
 *   of 2 of the same characters in a row. We count digits in a number and give
 *   back how many times that number appears. We use an array of ints to figure out
 *   if the array can be split up into 2 groups that have the same sum. We check a
 *   string of parentheses, brackets, and curly braces to see if it would be legal
 *   in something like a math equation. We also do the towers of hanoi in a way
 *   that uses the B pillar in every move.
 *</p>
 *
 * @author Josh Barrette
 * @edu.uwp.cs.242.course CSCI 242 - Computer Science II
 * @edu.uwp.cs.242.section 001
 * @edu.uwp.cs.242.assignment EC1
 * @bugs none
 */


public class MainDriver {
    /**
     * Were we run our spicy methods.
     * @param args Classic args.
     */
    public static void main(String[] args) {
        testStringClean();

        testCountDigit();

        testIsBalanced();

        testSplitArray();

        trickyHanoi(2);
    }

    /**
     * Cleans a string so that two characters do not appear twice in a row.
     * @param str Takes in the string to be cleaned.
     * @return The cleaned string.
     */
    public static String stringClean(String str) {
        // Only continues to run of the length is greater than one.
        if (str.length() > 1) {
            if (str.charAt(0) == str.charAt(1)) {
                // If the starting char is the same as the next char then we "forget"
                // the starting char by sending on a substring that does not include it.
                return stringClean(str.substring(1));
            } else {
                // If the starting char does not match the next one then it sends on the
                // substring without the starting char but we don't "forget" it, we append it
                // to the return.
                return str.charAt(0) + stringClean(str.substring(1));
            }
        } else {
            // At this point the string is only one char long, so we are done.
            return str;
        }
    }

    /**
     * Runs some tests on stringClean() and prints out the results.
     */
    public static void testStringClean() {
        System.out.println("Testing stringClean()");
        System.out.println("xyyzzzaaa -> " + stringClean("xyyzzzaaa"));
        System.out.println("yza -> " + stringClean("yza"));
        System.out.println("a -> " + stringClean("a"));
        System.out.println("aaaaaa -> " + stringClean("aaaaaaa"));
        System.out.println("-> " + stringClean("") + "\n");
    }

    /**
     * Takes in an int and counts how many times a specified digit appears in that string.
     * @param num The number we are searching through.
     * @param digit The number we are searching for.
     * @return The number of times digit appears in num.
     */
    public static int countDigit(int num, int digit) {
        // Turn num into a string so we can use substring and charAt.
        String numString = "" + num;

        if (((int)numString.charAt(0) - '0' == digit) && numString.length() > 1) {
            // If the char is the digit and there is more to parse, we add 1 to the count
            // and then call the method again on a the int form of a substring that does
            // not include the char we just parsed.
            return 1 + countDigit(Integer.parseInt(numString.substring(1)), digit);
        } else if (((int)numString.charAt(0) - '0' == digit) && numString.length() <= 1) {
            // Only returns 1 because there is nothing else to parse.
            return 1;
        } else if (numString.length() <= 1) {
            // Nothing matched and nothing else to parse.
            return 0;
        } else {
            // Nothing matched but more to parse.
            return countDigit(Integer.parseInt(numString.substring(1)), digit);
        }
    }

    /**
     * Runs some tests on countDigit() and prints out the results.
     */
    public static void testCountDigit() {
        System.out.println("Testing countDigit()");
        System.out.println("222, 2  -> " + countDigit(222, 2));
        System.out.println("123414, 1 -> " + countDigit(123414, 1));
        System.out.println("123414, 5 -> " + countDigit(123414, 5));
        System.out.println("1, 1  -> " + countDigit(1, 1) + "\n");
    }

    /**
     * Takes a string of parentheses, brackets, and curly braces and checks to see if they match up correctly.
     * @param str The string we are checking for correctness.
     * @return If the string is correct.
     */
    public static boolean isBalanced(String str) {
        // System.out.println(str);
        try {
            if (str.length() == 0) {
                // If the string is empty then it works.
                return true;
            } else if (str.length() % 2 == 1) {
                // If the string is of odd length then it is impossible for it to work.
                return false;
            } else if ((str.length() == 2) && ((str.charAt(0) == str.charAt(1) - 1) || (str.charAt(0) == str.charAt(1) - 2))) {
                // If the length is 2 and does logic on the chars based on the ascii chart based on
                // the ascii values of (, [, abd {.
                return true;
            } else if (str.charAt(0) == '(') {
                // Massive mess that checks the substrings of the next possible bracket and the last
                // possible bracket. Same for the next two if's.
                return (isBalanced(str.substring(1, str.lastIndexOf(')'))) ||  isBalanced(str.substring(1, str.indexOf(')')))) &&
                        (isBalanced(str.substring(str.lastIndexOf(')') + 1)) || isBalanced(str.substring(str.indexOf(')') + 1)));
            } else if (str.charAt(0) == '{') {
                return isBalanced(str.substring(1, str.lastIndexOf('}'))) ||  isBalanced(str.substring(1, str.indexOf('}')))  &&
                        (isBalanced(str.substring(str.lastIndexOf('}') + 1)) || isBalanced(str.substring(str.indexOf('}') + 1)));
            } else if (str.charAt(0) == '[') {
                return isBalanced(str.substring(1, str.lastIndexOf(']'))) ||  isBalanced(str.substring(1, str.indexOf(']')))  &&
                        (isBalanced(str.substring(str.lastIndexOf(']') + 1)) || isBalanced(str.substring(str.indexOf(']') + 1)));
            } else {
                // If nothing matches up, then nothing works.
                return false;
            }
        } catch (Exception e) {
            // If an exception gets thrown, usually by an index out of bounds exceptions, then it's false.
            return false;
        }
    }

    /**
     * Runs some tests on isBalanced() and prints out the results.
     */
    public static void testIsBalanced() {
        System.out.println("Testing isBalanced()");
        System.out.println("{([])([()])} -> " + isBalanced("{([])([()])}"));
        System.out.println("(([]) -> " + isBalanced("(([])"));
        System.out.println(")( -> " + isBalanced(")("));
        System.out.println("{(})-> " + isBalanced("{(})"));
        System.out.println("[(){}] -> " + isBalanced("[(){}]"));
        System.out.println("({[[]]}) -> " + isBalanced("({[[]]})") + "\n");
    }

    /**
     * Takes in an array of ints and calls a helper method to determine if the array can be split into 2 groups
     * that have the same sum.
     * @param array The array we are going to check.
     * @return If the array works.
     */
    public static boolean splitArray(int[] array) {
        return splitArrayHelper(array, 0, 0, 0);
    }

    /**
     * The recursive helper method that does all the heavy lifting.
     * @param array The array we are checking.
     * @param index The index of the current int we are working on.
     * @param sum1 The first possible sum.
     * @param sum2 The second possible sum.
     * @return If everything checks out.
     */
    public static boolean splitArrayHelper(int[] array, int index, int sum1, int sum2) {
        // I just made what you outlined in the instructions, the only thing that
        // I actually had to think about was the base cases.
        if (index == array.length && sum1 == sum2) {
            return true;
        } else if (index == array.length || array.length == 0) {
            return false;
        } else {
            return splitArrayHelper(array, index + 1, sum1 + array[index], sum2) ||
                    splitArrayHelper(array, index + 1, sum1, sum2 + array[index]);
        }
    }

    /**
     * Runs some tests on splitArray() and prints out the results.
     */
    public static void testSplitArray() {
        System.out.println("Testing splitArray()");

        int[] x = {2, 2};
        System.out.println(splitArray(x));

        int[] y = {2, 3};
        System.out.println(splitArray(y));

        int[] z  = {5, 2, 3};
        System.out.println(splitArray(z));

        int[] w = {2, 5, 3};
        System.out.println(splitArray(w));

        int[] p = {1, 1, 1, 6, 1, 1, 1};
        System.out.println(splitArray(p) + "\n");
    }

    /**
     * Calls the helper method that does all the work.
     * @param disks The number of disks we will be using.
     */
    public static void trickyHanoi(int disks) {
        trickyHanoiHelper(disks, 'A', 'C', 'B');
    }

    /**
     * The helper method that does all the recursion to figure out the moves.
     * @param disks The number of disks we will be using.
     * @param from The pillar we are moving from.
     * @param to The pillar we are moving to.
     * @param via The pillar we are using to get from from to to.
     */
    public static void trickyHanoiHelper(int disks, char from, char to, char via) {
        // If the number of disks is 0 then there are no disks to move, so we get out.
        if (disks <= 0) {
            return;
        }

        // We need to take the stack-1 from A to C so that when we move the base it can still use B on in every move.
        trickyHanoiHelper(disks - 1, 'A', 'C', 'B');
        // Move the base to B.
        System.out.println("Moved disk from " + from + " to " + via);
        // Move everything out of C so the base can get there.
        trickyHanoiHelper(disks - 1, 'C', 'A', 'B');
        // Get the base on C.
        System.out.println("Moved disk from " + via + " to " + to);
        // Get everything else to C.
        trickyHanoiHelper(disks - 1, 'A', 'C', 'B');
    }
}
