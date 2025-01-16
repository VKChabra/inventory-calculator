package inventorycalculator.core;

import java.util.List;

public class Calculator {
    public int calculateTotal(List<Integer> values) {
        return values.stream().mapToInt(Integer::intValue).sum();
    }
}