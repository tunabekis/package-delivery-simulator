/**
 * LIFO stack built on top of {@link DoublyLinkedList}.
 */
public class MyStack<T> {

    private final DoublyLinkedList<T> list = new DoublyLinkedList<>();

    public void push(T item) {
        list.addFirst(item);
    }

    public T pop() {
        return list.deleteFirst();
    }

    public int getSize() {
        return list.getSize();
    }
}
