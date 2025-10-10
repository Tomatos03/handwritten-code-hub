package stack;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/10
 */
public interface Stack<E> {
    void push(E e);
    void pop();
    boolean isEmpty();
    E top();
    int size();
}