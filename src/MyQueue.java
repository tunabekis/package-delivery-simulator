/**
 * FIFO queue built on top of {@link DoublyLinkedList}.
 */
public class MyQueue<T> {

    private final DoublyLinkedList<T> list = new DoublyLinkedList<>();

    public void enqueue(T item) {
        list.addLast(item);
    }

    public T dequeue() {
        return list.deleteFirst();
    }

    public int getSize() {
        return list.getSize();
    }
}
