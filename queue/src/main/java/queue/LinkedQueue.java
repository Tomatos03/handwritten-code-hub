package queue;

import exception.QueueEmptyException;

/**
 * 基于单向链表实现的无界队列
 *
 * @author : Tomatos
 * @date : 2025/10/11
 */
public class LinkedQueue<E> implements Queue<E>{
    private int size = 0;
    private Node<E> tail;
    private Node<E> head;

    private static class Node<E> {
        Node<E> next;
        E val;

        public Node(E val) {
            this.val = val;
        }
    }

    @Override
    public void enqueue(E e) {
        Node<E> node = new Node<>(e);
        if (head == null)
            head = node;
        if (tail != null)
            tail.next = node;
        tail = node;

        ++size;
    }

    @Override
    public void dequeue() {
        if (isEmpty())
            throw new QueueEmptyException();

        if (head == tail)
            tail = null;

        Node<E> tmp = head.next;
        head.next = null;
        head = tmp;

        --size;
    }

    @Override
    public E front() {
        if (head == null)
            return null;
        return head.val;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }
}
