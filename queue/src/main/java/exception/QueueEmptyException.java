package exception;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/11
 */
public class QueueEmptyException extends RuntimeException {
    public QueueEmptyException() {
        super("当前栈为空");
    }
}
