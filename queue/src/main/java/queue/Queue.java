package queue;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/11
 */
public interface Queue<T> {
    void enqueue(T element);
    void dequeue();
    T front();
    boolean isEmpty();
    int size();
}
