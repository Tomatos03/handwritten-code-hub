package heap;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/10/12
 */
public interface Heap<E extends Comparable<E>> {
    void insert(E value); /* 插入元素 */

    void poll(); /* 删除并返回堆顶元素 */

    E top(); /* 获取堆顶元素 */

    int size(); /* 获取堆大小 */

    boolean isEmpty(); /* 判断是否为空 */
}
