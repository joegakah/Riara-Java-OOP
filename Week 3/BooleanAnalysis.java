// This program verifies the analysis of a complex boolean expression.

public class BooleanAnalysis {
    public static void main(String[] args) {

        // ---------- Step 5.2: First expression ----------
        // (5 > 3) && (4 < 2) || (10 == 10)

        // Evaluate the expression step by step and print intermediate results.
        boolean part1 = (5 > 3); // true
        boolean part2 = (4 < 2); // false
        boolean part3 = (10 == 10); // true

        // Print each part.
        System.out.println("5 > 3: " + part1);
        System.out.println("4 < 2: " + part2);
        System.out.println("10 == 10: " + part3);

        // Combine using && and ||.
        // Operator precedence: && before ||
        // So: (part1 && part2) || part3
        boolean intermediate = part1 && part2; // true && false = false
        System.out.println("part1 && part2: " + intermediate);

        boolean finalResult = intermediate || part3; // false || true = true
        System.out.println("Final result: " + finalResult);

        // Direct evaluation for verification.
        boolean directResult = (5 > 3) && (4 < 2) || (10 == 10);
        System.out.println("Direct result: " + directResult);

        System.out.println(); // Blank line between the two analyses.

        // ---------- Step 5.3: Second expression ----------
        // !(5 > 3) || (4 < 2) && (10 == 10)

        boolean b1 = (5 > 3); // true
        boolean notB1 = !b1; // !(true) = false
        boolean b2 = (4 < 2); // false
        boolean b3 = (10 == 10); // true

        System.out.println("!(5 > 3): " + notB1);
        System.out.println("4 < 2: " + b2);
        System.out.println("10 == 10: " + b3);

        // && binds tighter than ||, so this is: notB1 || (b2 && b3)
        boolean andPart = b2 && b3; // false && true = false
        System.out.println("(4 < 2) && (10 == 10): " + andPart);

        boolean result2 = notB1 || andPart; // false || false = false
        System.out.println("Final result2: " + result2);

        // Direct evaluation for verification.
        boolean directResult2 = !(5 > 3) || (4 < 2) && (10 == 10);
        System.out.println("Direct result2: " + directResult2);
    }
}
