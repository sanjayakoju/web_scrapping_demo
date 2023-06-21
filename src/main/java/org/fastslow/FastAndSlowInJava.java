package org.fastslow;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Optimize "fast" Method to act the same as "slow" method but for less than 50 ms
 * Algorithm and what need method to do is explained in comment at the end.
 */
public class FastAndSlowInJava {

    public static void main(String[] args) {
        int numN = 50000;

        List<Integer> list = new ArrayList<>(numN);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numN; i++) {
            sb.append((int) (Math.random() * numN)).append(" ");
        }

        String inputString = sb.toString();
        System.out.println("Input: " + inputString.substring(0, 50));

        List<Integer> resultSlow;
        List<Integer> resultFast;

        resultSlow = slow(inputString);

        long startTime = System.currentTimeMillis();
        resultFast = fast(inputString);
        long endTime = System.currentTimeMillis();

        if (!resultSlow.equals(resultFast)) {
            System.out.println("Error!!! Not equal results!");
        }

        System.out.println("Max Jumps: " + resultSlow.stream().mapToInt(Integer::intValue).max().orElse(0));
        System.out.println("Max Jumps Fast: " + resultFast.stream().mapToInt(Integer::intValue).max().orElse(0));
        System.out.println("Jumps: " + resultSlow.subList(0, Math.min(resultSlow.size(), 20)));

        System.out.println("Execution Time: " + (endTime - startTime) + " ms");
    }

    private static List<Integer> slow(String inputString) {
        return new ArrayList<>();
    }


    /**
     * Mine Simple Solution
     *
     * @param inputNumbers
     * @return
     */
//    static List<Integer> fast(String inputNumbers) {
//        String[] numberStrings = inputNumbers.trim().split(" ");
//        int numN = numberStrings.length;
//
//        int[] numbers = new int[numN];
//        for (int i = 0; i < numN; i++) {
//            numbers[i] = Integer.parseInt(numberStrings[i]);
//        }
//
//        int initialJump = 0;
//        int next = 0;
//
//        List<Integer> list = new ArrayList<>(numN);
//        Map<Integer, Integer> map = new HashMap<>();
//        Stack<Integer> stack = new Stack<>();
//
//        int counter = 0;
//        int max = Arrays.stream(numbers).max().orElse(0);
//        for (int i = 0; i < numN; i++) {
//            initialJump = numbers[i];
//
//            // Partial optimization
//            if (initialJump == max) {
//                list.add(0);
//                continue;
//            }
//            if (map.containsKey(numbers[i])) {
//                list.add(map.get(numbers[i]));
//                map.remove(numbers[i]);
//                continue;
//            } else {
//                for (int j = i + 1; j < numN; j++) {
//                    next = numbers[j];
//                    if (initialJump < next) {
//                        counter++;
//                        initialJump = next;
//                        stack.add(next);
//                    }
//                }
//            }
//
//
//            for (int a = 0; a < counter-1; a++) {
//                map.put(stack.pop(), a);
//            }
//
//            list.add(counter);
//            counter = 0;
//        }
//
//        return list;
//    }


    // Optimized one
    static List<Integer> fast(String inputNumbers) {
        String[] numberStrings = inputNumbers.trim().split(" ");
        int numN = numberStrings.length;

        int[] numbers = new int[numN];
        for (int i = 0; i < numN; i++) {
            numbers[i] = Integer.parseInt(numberStrings[i]);
        }

        int initialJump = 0;
        int next = 0;
        int lastValue;

        List<Integer> list = new ArrayList<>(numN);
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();

        int counter;
        int max = Arrays.stream(numbers).max().orElse(0);
        for (int i = 0; i < numN; i++) {
            initialJump = numbers[i];
            counter = 0;

            if (map.containsKey(numbers[i])) {
                if (initialJump == max) {
                    list.add(0);
                    continue;
                }
                lastValue = i;
                int addValue = 0;
                set.clear();
                set.add(lastValue);
                for (int j = i + 1; j < numN; j++) {
                    next = numbers[j];
                    if (initialJump < next) {
                        counter++;
                        if (map.containsKey(j)) {
                            addValue = map.get(j);
                            break;
                        } else {
                            initialJump = next;
                            set.add(j);
                        }
                    }
                }

                // Store Jump value into Map
                for (int s : set) {
                    map.put(s, counter + addValue);
                    counter--;
                }
                Map<Integer, Integer> sortedMap = map.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, HashMap::new));
                list = (List<Integer>) map.values();
                list.add(counter);


            }

        }
        return list;
    }

//        static List<Integer> slow (String inputNumbers){
//            String[] numberStrings = inputNumbers.trim().split(" ");
//            int numN = numberStrings.length;
//
//            int[] numbers = new int[numN];
//            for (int i = 0; i < numN; i++) {
//                numbers[i] = Integer.parseInt(numberStrings[i]);
//            }
//
//            int initialJump = 0;
//            int next = 0;
//
//            List<Integer> list = new ArrayList<>(numN);
//
//            int counter = 0;
//            int max = Arrays.stream(numbers).max().orElse(0);
//            for (int i = 0; i < numN; i++) {
//                initialJump = numbers[i];
//
//                // Partial optimization
//                if (initialJump == max) {
//                    list.add(0);
//                    continue;
//                }
//
//                for (int j = i + 1; j < numN; j++) {
//                    next = numbers[j];
//                    if (initialJump < next) {
//                        counter++;
//                        initialJump = next;
//                    }
//                }
//
//                list.add(counter);
//                counter = 0;
//            }
//
//            return list;
//        }


    }

/**
 * Jumps
 * <p>
 * Given a sequence of elements(numbers), calculate the longest possible sequence of 'jumps' from each number.
 * <p>
 * Each 'jump' must be made according to the following rules:
 * <p>
 * You can only 'jump' on a number that is greater than the current one;
 * You can 'jump' on a number, only if there isn't one with a greater value between;
 * You can 'jump' only from left to right;
 * <p>
 * Input
 * <p>
 * Read from the standard input
 * <p>
 * On the first line, you will find the number N
 * The number of elements
 * On the second line you will find N numbers, separated by a space
 * The elements themselves
 * <p>
 * The input will be correct and in the described format, so there is no need to check it explicitly.
 * Output
 * <p>
 * Print to the standard output
 * <p>
 * On the first line, print the length of the longest sequence of jumps
 * On the second line, print the lengths of the sequences, starting from each element
 * <p>
 * Constraints
 * <p>
 * The N will always be less than 50000
 * <p>
 * Sample Tests
 * Input
 * <p>
 * 1 4 2 6 3 4
 * <p>
 * Output
 * <p>
 * 2 1 1 0 1 0
 * <p>
 * Explanation
 * <p>
 * Element 1:
 * 1 -> 4 -> 6 (2 jumps)
 * Element 2:
 * 4 -> 6 (1 jump)
 * Element 3:
 * 2 -> 6 (1 jump)
 * Element 4:
 * 6 (0 jumps)
 * Element 5:
 * 3 -> 4 (1 jump)
 * Element 6:
 * 4 -> (0 jumps)
 * <p>
 * Input
 * <p>
 * 1 1 1 1 1
 * <p>
 * Output
 * <p>
 * 0 0 0 0 0
 */


