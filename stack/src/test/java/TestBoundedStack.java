import exception.StackEmptyException;
import exception.StackFullException;
import org.junit.jupiter.api.Test;
import stack.BoundedStack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/10
 */
public class TestBoundedStack {

    @Test
    void testPushAndPop() {
        BoundedStack<Integer> stack = new BoundedStack<>(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.top());
        stack.pop();

        assertEquals(2, stack.top());
        stack.pop();

        assertEquals(1, stack.top());
        stack.pop();
    }

    @Test
    void testPeek() {
        BoundedStack<String> stack = new BoundedStack<>(2);
        stack.push("A");
        assertEquals("A", stack.top());

        stack.push("B");
        assertEquals("B", stack.top());
    }

    @Test
    void testPushOverCapacity() {
        BoundedStack<Integer> stack = new BoundedStack<>(1);
        stack.push(10);
        assertThrows(StackFullException.class, () -> stack.push(20));
    }

    @Test
    void testPopEmpty() {
        BoundedStack<Integer> stack = new BoundedStack<>(2);
        assertThrows(StackEmptyException.class, stack::pop);
    }
}
