package exception;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/11
 */
public class QueueFullException extends RuntimeException {
    public QueueFullException() {
        super("当前队列元素已达到容量上限");
    }
}
