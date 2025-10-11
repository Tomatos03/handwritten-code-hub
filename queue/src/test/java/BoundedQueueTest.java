import exception.QueueEmptyException;
import exception.QueueFullException;
import org.junit.jupiter.api.Test;
import queue.BoundedQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/11
 */
class BoundedQueueTest {

    @Test
    void testEnqueueAndSize() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(3);
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    void testDequeue() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(2);
        queue.enqueue(10);
        queue.enqueue(20);
        queue.dequeue();
        assertEquals(1, queue.size());
        assertEquals(20, queue.front());
    }

    @Test
    void testFront() {
        BoundedQueue<String> queue = new BoundedQueue<>(2);
        queue.enqueue("A");
        assertEquals("A", queue.front());
        queue.enqueue("B");
        assertEquals("A", queue.front());
    }

    @Test
    void testIsEmpty() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(1);
        assertTrue(queue.isEmpty());
        queue.enqueue(5);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testQueueFullException() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(2);
        assertThrows(QueueFullException.class, () -> queue.enqueue(3));
    }

    @Test
    void testQueueEmptyExceptionOnDequeue() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(1);
        assertThrows(QueueEmptyException.class, queue::dequeue);
    }

    @Test
    void testQueueEmptyExceptionOnFront() {
        BoundedQueue<Integer> queue = new BoundedQueue<>(1);
        assertNull(queue.front());
    }
}