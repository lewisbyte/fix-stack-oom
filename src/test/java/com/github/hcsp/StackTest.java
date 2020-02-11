package com.github.hcsp;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StackTest {
    private Stack stack = new Stack();

    @Test
    public void basicTest() {
        String[] before = {"a", "b", "c", "d"};
        String[] after = {"d", "c", "b", "a"};

        Stream.of(before).forEach(stack::push);

        Assertions.assertArrayEquals(
                after,
                IntStream.range(0, before.length)
                        .mapToObj(i -> stack.pop())
                        .toArray(Object[]::new));
    }

    @Test
    public void memoryLeakTest() {
        IntStream.range(0, 10).forEach(stack::push);
        for (int i = 0; i < 10; i++) {
            stack.pop();
            stack.push(new HugeObject());
            stack.pop();
        }
    }

    private static class HugeObject {
        private byte[] data = new byte[10 * 1024 * 1024];
    }
}
