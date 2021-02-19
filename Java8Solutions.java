
import java.util.Arrays;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@FunctionalInterface
interface SumInterface<T> {
    public T sum(T x, T y);
}

class Student {
    private Integer rollNum;
    private String name;
    private List<Integer> marks;

    public Student(Integer rollNum, String name, List<Integer> marks){

        this.rollNum = rollNum;
        this.name = name;
        this.marks = marks;
    }

    public Integer getRollNum() {
        return rollNum;
    }

    public List<Integer> getMarks() {
        return marks;
    }

    public String getName() {
        return name;
    }
}

class Java8Solutions {

    public static String punctuationMarksRegex = "\\?|\\'|\\-|\"|\\!|\\:|\\,|\\.|\\;|\\!";

    public static boolean sortAndCheckIfEqual(final char[] str1, final char[] str2){
            Arrays.sort(str1);
            Arrays.sort(str2);
            return Arrays.equals(str1, str2);
        }
        //1. Write a function to check whether two given strings are anagram of each other or not.
        public static boolean checkIfAnagramsUsingSorting(final String str1, final String str2){
            return sortAndCheckIfEqual(str1.toCharArray(), str2.toCharArray());
        }
        public static boolean checkIfAnagramsUsingHashingCaseInsensitive(final String str1, final String str2){
            //Using int array, Assuming the string contains only ASCII characters, else use HashMap
            int[] cache1 = new int[127];
            int[] cache2 = new int[127];

            for (char c: str1.toLowerCase().toCharArray()){
                cache1['a' - c]++;
            }

            for (char c: str2.toLowerCase().toCharArray()){
                cache2['a' - c]++;
            }

            return Arrays.equals(cache1, cache2);

        }
        //2. Reverse a string (Tips : use Java 8 Features)
        public static String reverseString(final String str){
            int len = str.length();
            return new String(IntStream.range(0, len)
                    .map(c -> str.charAt((len - 1) - c)).toArray(), 0, str.length());

        }
        //3. Find a pair with maximum product in array of Integers
        private static Integer productFirst2(Stream<Integer> sorted) {
            return sorted.limit(2).reduce(1, (first, sec) -> first * sec );
        }
        private static Integer productFirst2(Stream<Integer> sorted, int skip) {
            return sorted.skip(skip).reduce(1, (first, sec) -> first * sec );
        }

        public static List<Integer> maxProduct(List<Integer> input){
            //Below commented java 8 solution is not an efficient solution
            //Stream<Integer> sortedStream = input.stream().sorted();
            //sortedStream.toArray();
            //return Math.max(productFirst2(sortedStream), productFirst2(sortedStream, input.size() - 2));

            int min1 = Integer.MIN_VALUE, min2 = Integer.MIN_VALUE;
            int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE;


            for (int index = 0; index < input.size(); index++){
                        if (max1 <= input.get(index))
                        {
                            max2 = max1;
                            max1 = input.get(index);
                        }
                        else if (max2 <= input.get(index)) {
                            max2 = input.get(index);
                        }

                        if (input.get(index) < 0) {
                            if (Math.abs(input.get(index)) > Math.abs(min1)) {
                                min2 = min1;
                                min1 = input.get(index);
                            }
                            else if (Math.abs(input.get(index)) > Math.abs(min2)) {
                                min2 = input.get(index);

                            }
                        }
                    }
                    return (min1 * min2 > max1 * max2) ? Arrays.asList(min1,min2) : Arrays.asList(max1,max2);

        }
    //4. Reverse Integer
    public static int reverseInteger(int num){
            int result = 0;
        while(num != 0){
                int rem = num%10;
                result = result*10 + rem;
                num = num/10;
            }
            return result;
    }

    //5. Find an Average Words Length
    public static Double avgLenOfWords(String sentence){

        return Arrays.stream(sentence.replace("...", " ").split(" "))
                .map(word -> word.replaceAll(punctuationMarksRegex, ""))
                .mapToInt(String::length)
                .average()
                .orElse(0);
    }

    //6. Given a list of Strings, Write program to remove duplicate elements from the list. (Tips: Use Java 8 Features)
    private static List<String> removeDuplicateWords(List<String> words) {
        return words.stream().distinct().collect(Collectors.toList());
    }

    //7. Given a list of Integers, write program to find max, min and average numbers using java8.
    public static void printMaxMinAvgOfList(List<Integer> input){

        //Integer max = input.stream().mapToInt(Integer::intValue).max().getAsInt();
        Integer max = input.stream().reduce(Integer.MIN_VALUE, (max_val, sec) -> sec > max_val ? sec : max_val);

        //Integer min = input.stream().mapToInt(Integer::intValue).min().getAsInt();
        Integer min = input.stream().reduce(Integer.MAX_VALUE, (min_val, sec) -> sec < min_val ? sec : min_val);


        //Double avg = input.stream().mapToInt(Integer::intValue).average().orElse(0);
        Double avg = input.stream().reduce(0, (sum,sec) -> sum+sec).doubleValue() / input.size();

        System.out.println("Min : " + min + "\n" + "Max : " + max + "\n" + "Average : " + avg);

    }

    //8. Given a list of Student with rollno, name and mark(4 subjects), Write program to return Map with rollno as key and student as value of the map.
    //
    //return only students whose mark is greater than 80 in three subjects.   (Tip: use Java8)

    public static Map<Integer, Student> filterStudents(List<Student> students){
        return students.stream().filter(student -> student.getMarks().stream().filter(mark -> mark > 80).count() == 3)
                .collect(Collectors.toMap(Student::getRollNum, student -> student));
    }
    //9. Given a list of strings, write a method that returns a list of all strings that start with the letter ‘b' (lower case) and have exactly 4 letters.
    //
    //       (TIP: Use Java 8 Lambdas and Streams API’s.)
    public static List<String> filterString(List<String> input){
        return input.stream().filter(str -> str.startsWith("b") && str.length() == 4)
                .collect(Collectors.toList());
    }

    //10. Given two numbers, write program using functional Interface and get sum of those two values.
    public static SumInterface<Integer> integerSum = (num1, num2) -> num1 + num2;
    public Integer sumOfIntegers(Integer num1, Integer num2){
        //BinaryOperator<Integer> sum = (x, y) -> x + y;
        //return sum.apply(num1, num2);

        return integerSum.sum(num1, num2);
    }

    //11. Write a program for Fibonacci example using java8
    public  static List<Long> fibonacci(int limit){

        return Stream.iterate(new long[]{0, 1}, series -> new long[]{series[1], series[0] + series[1]})
                .limit(limit)
                .map(num -> num[0])
                .mapToLong(Long::longValue)
                .boxed()
                .collect(Collectors.toList());
    }

    public static void main(String args[]){
        //String str = reverseString("abcad");
        //System.out.println(reverseInteger(-395862));
        //System.out.println(avgLenOfWords("Hi all, my name is Tom...I am originally from Australia."));
        //printMaxMinAvgOfList(Arrays.asList(1,4,7));
        //System.out.printf(filterString(Arrays.asList("Nish", "Bish", "agegb")).toString());
        //System.out.println(fibonacci(18));
        //System.out.println(maxProduct(Arrays.asList(-1, -3, -4, 2, 0, -5)).toString());
        Map<Integer, Student> result = filterStudents(Arrays.asList(
                new Student(1, "nishi", Arrays.asList(23, 6, 7)),
                new Student(2, "nishita", Arrays.asList(81, 6, 97))
        ));
        result.forEach((integer, student) -> {System.out.println(integer);});
    }
}
