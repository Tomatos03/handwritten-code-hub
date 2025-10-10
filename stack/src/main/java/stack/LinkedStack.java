package stack;

import exception.StackEmptyException;

/**
 * 基于单向链表实现的无界栈
 *
 * @author : Tomatos
 * @date : 2025/10/10
 */
public class LinkedStack<E> implements Stack<E> {
    private int size;
    private final Node dummy = new Node(); // 虚拟节点
    private Node top = dummy;

    private class Node {
        public Node() {}

        public Node(Node next, E val) {
            this.next = next;
            this.val = val;
        }

        Node next;
        E val;
    }

    @Override
    public void push(E e) {
        top = new Node(top, e);
        ++size;
    }

    @Override
    public void pop() {
        if (isEmpty())
            throw new StackEmptyException();

        --size;
        Node next = top.next;
        top.next = null;
        top = next;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E top() {
        if (isEmpty())
            return null;
        return top.val;
    }

    @Override
    public int size() {
        return this.size;
    }
}