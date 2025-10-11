import exception.QueueEmptyException;
import org.junit.jupiter.api.Test;
import queue.LinkedQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/11
 */
class LinkedQueueTest {

    @Test
    void testEnqueueAndSize() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void testDequeue() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals(10, queue.front());
        queue.dequeue();
        assertEquals(20, queue.front());
        queue.dequeue();
        assertEquals(30, queue.front());
        queue.dequeue();

        assertNull(queue.front());

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(10, queue.front());
    }

    @Test
    void testFront() {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("A");
        assertEquals("A", queue.front());
        queue.enqueue("B");
        assertEquals("A", queue.front());
    }

    @Test
    void testIsEmpty() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        assertTrue(queue.isEmpty());
        queue.enqueue(5);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testDequeueException() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        assertThrows(QueueEmptyException.class, queue::dequeue);
    }

    @Test
    void testFrontException() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        assertNull(queue.front());
    }
}