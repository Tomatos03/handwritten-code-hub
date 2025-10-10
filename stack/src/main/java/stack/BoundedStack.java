package stack;

import exception.StackEmptyException;
import exception.StackFullException;

/**
 * 实现一个有容量上限的栈
 *
 * @author : Tomatos
 * @date : 2025/10/10
 */
public class BoundedStack<E> implements Stack<E> {
    private int top = 0; // 顶部指针, 总是指向顶部元素的下一个位置
    private final int capacity; // 栈的容量
    private final Object[] arr;

    public BoundedStack(int capacity) {
        this.capacity = capacity;
        arr = new Object[capacity];
    }

    /**
     * 添加元素到栈
     *
     * @param e 添加元素
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/10 20:02
     */
    public void push(E e) {
        if (isFull())
            throw new StackFullException();

        arr[top] = e;
        ++top;
    }

    /**
     *
     * 这里没有设计返回值, 而是抛出异常
     *
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/10 19:56
     */
    public void pop() {
        if (isEmpty())
            throw new StackEmptyException();
        --top;
    }

    /**
     * 查看栈顶元素
     *
     * @return E 栈顶元素
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/10 20:01
     */
    @Override
    @SuppressWarnings("unchecked")
    public E top() {
        if (isEmpty())
            return null;
        return (E) arr[top - 1];
    }

    /**
     *  判断栈是否为空
     *
     * @return boolean
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/10 19:59
     */
    public boolean isEmpty() {
        return top == 0;
    }

    /**
     * 判读栈是否满了
     *
     * @return boolean
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/10 19:59
     */
    public boolean isFull() {
        return top == capacity;
    }

    /**
     * 获取栈元素个数
     *
     * @return int
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/10 19:59
     */
    public int size() {
        return top;
    }

    public int getCapacity() {
        return capacity;
    }
}