package exception;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/10
 */
public class StackFullException extends RuntimeException {
    public StackFullException() {
        super("当前栈已经满了, 无法添加新元素");
    }
}
