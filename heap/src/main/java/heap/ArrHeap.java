package heap;

import exception.HeapEmptyException;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于动态数组实现的堆
 *
 * @author : Tomatos
 * @date : 2025/10/12
 */
public class ArrHeap<E extends Comparable<E>> implements Heap<E> {
    private final List<E> list;

    public ArrHeap() {
        list = new ArrayList<>();
    }

    private void swap(int x, int y) {
        E a = list.get(x), b = list.get(y);
        list.set(x, b);
        list.set(y, a);
    }

    /**
     * 从位置x开始向下调整堆
     *
     * @param x
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/13 22:09
     */
    private void down(int x) {
        int n = list.size();
        while (((x << 1) + 1) < n) {
            int p = (x << 1) + 1;
            if (p + 1 < n && list.get(p + 1).compareTo(list.get(p)) > 0)
                ++p;
            if (list.get(x).compareTo(list.get(p)) > 0)
                break;
            swap(x, p);
            x = p;
        }
    }

    /**
     * 从位置x开始向上调整堆
     *
     * @param x
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/10/13 22:09
     */
    private void up(int x) {
        while (x > 0) {
            int p = (x - 1) >> 1;
            if (list.get(p).compareTo(list.get(x)) > 0)
                break;
            swap(x, p);
            x = p;
        }
    }

    @Override
    public void insert(E e) {
        list.add(e);
        up(list.size() - 1);
    }

    @Override
    public void poll() {
        if (list.isEmpty())
            throw new HeapEmptyException();
        int tail = list.size() - 1;
        swap(0, tail);
        list.remove(tail);
        down(0);
    }

    @Override
    public E top() {
        if (list.isEmpty())
            throw new HeapEmptyException();
        return list.get(0);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
