import exception.StackEmptyException;
import org.junit.jupiter.api.Test;
import stack.LinkedStack;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/10
 */
public class LinkedStackTest {

    @Test
    void testPushAndTop() {
        LinkedStack<Integer> stack = new LinkedStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.top());
        assertEquals(3, stack.size());
    }

    @Test
    void testPop() {
        LinkedStack<String> stack = new LinkedStack<>();
        stack.push("A");
        stack.push("B");
        stack.pop();
        assertEquals("A", stack.top());
        assertEquals(1, stack.size());
    }

    @Test
    void testIsEmpty() {
        LinkedStack<Double> stack = new LinkedStack<>();
        assertTrue(stack.isEmpty());

        stack.push(1.1);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopEmptyThrows() {
        LinkedStack<Integer> stack = new LinkedStack<>();
        assertThrows(StackEmptyException.class, stack::pop);
    }

    @Test
    void testTopOnEmpty() {
        LinkedStack<Integer> stack = new LinkedStack<>();
        assertNull(stack.top());
    }
}