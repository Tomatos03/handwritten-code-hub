package exception;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/12
 */
public class HeapEmptyException extends RuntimeException {
    public HeapEmptyException() {
        super("堆当前为空");
    }
}
