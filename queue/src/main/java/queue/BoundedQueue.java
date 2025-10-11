package queue;

import exception.QueueEmptyException;
import exception.QueueFullException;

/**
 * 基于数组实现有容量上限的队列
 *
 * @author : Tomatos
 * @date : 2025/10/11
 */
public class BoundedQueue<E> implements Queue<E> {
    private int head, tail;
    private final Object[] arr;
    private int size = 0;
    private final int capacity;

    public BoundedQueue(int capacity) {
        arr = new Object[capacity];
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void enqueue(E e) {
        if (!isEmpty() && head == tail)
            throw new QueueFullException();

        arr[tail] = e;
        tail = (tail + 1) % capacity;
        ++size;
    }

    @Override
    public void dequeue() {
        if (isEmpty())
            throw new QueueEmptyException();
        head = (head + 1) % capacity;
        --size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E front() {
        return (E) arr[head];
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }
}
