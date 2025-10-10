package exception;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/10
 */
public class StackEmptyException extends RuntimeException {
    public StackEmptyException() {
        super("当前栈为空, 无法继续取出元素");
    }
}
