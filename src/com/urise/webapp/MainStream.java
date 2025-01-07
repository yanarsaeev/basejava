package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        System.out.println(oddOrEven(integers));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (result, number) -> result * 10 + number);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::intValue).sum();

        return integers.stream()
                .filter(num -> (sum % 2 != 0) == (num % 2 == 0))
                .collect(Collectors.toList());
    }
}
