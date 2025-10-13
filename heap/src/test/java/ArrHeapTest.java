import heap.ArrHeap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/12
 */
class ArrHeapTest {

    @Test
    void testInsertAndTop() {
        ArrHeap<Integer> heap = new ArrHeap<>();
        heap.insert(1);
        heap.insert(5);
        heap.insert(3);
        assertEquals(5, heap.top());
    }

    @Test
    void testPoll() {
        ArrHeap<Integer> heap = new ArrHeap<>();
        heap.insert(2);
        heap.insert(7);
        heap.insert(4);
        heap.poll();
        assertEquals(4, heap.top());
        heap.poll();
        assertEquals(2, heap.top());
    }

    @Test
    void testIsEmptyAndSize() {
        ArrHeap<Integer> heap = new ArrHeap<>();
        assertTrue(heap.isEmpty());
        heap.insert(10);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        heap.poll();
        assertTrue(heap.isEmpty());
    }

    @Test
    void testMultipleInsertAndPoll() {
        ArrHeap<Integer> heap = new ArrHeap<>();
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6};
        for (int v : arr) heap.insert(v);
        assertEquals(9, heap.top());
        heap.poll();
        assertEquals(6, heap.top());
        heap.poll();
        assertEquals(5, heap.top());
    }
}