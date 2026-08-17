/**
 * A generic doubly linked list backing {@link MyStack} and {@link MyQueue},
 * and used directly as the mission "convoy" in {@link MissionService}.
 */
public class DoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
        size++;
    }

    public void addLast(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        size++;
    }

    public void addMiddle(T data, int position) {
        if (position == 0) {
            addFirst(data);
            return;
        }

        Node<T> current = head;
        int currentPosition = 0;
        while (current != null && currentPosition < position) {
            current = current.getNext();
            currentPosition++;
        }

        if (current == null) {
            addLast(data);
            return;
        }

        Node<T> node = new Node<>(data);
        node.setNext(current);
        node.setPrev(current.getPrev());
        current.getPrev().setNext(node);
        current.setPrev(node);
        size++;
    }

    public T deleteFirst() {
        if (head == null) {
            return null;
        }

        Node<T> removed = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrev(null);
        }
        size--;
        return removed.getData();
    }

    public T deleteLast() {
        if (tail == null) {
            return null;
        }

        Node<T> removed = tail;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        size--;
        return removed.getData();
    }

    /**
     * Removes and returns the node immediately after position {@code pos}
     * (0-indexed), or {@code null} if {@code pos} is out of bounds or is
     * the last node.
     */
    public T removeAfter(int pos) {
        if (head == null) {
            return null;
        }

        Node<T> current = head;
        int index = 0;
        while (current != null && index < pos) {
            current = current.getNext();
            index++;
        }

        if (current == null || current.getNext() == null) {
            return null;
        }

        Node<T> toRemove = current.getNext();
        current.setNext(toRemove.getNext());
        if (toRemove.getNext() != null) {
            toRemove.getNext().setPrev(current);
        } else {
            tail = current;
        }

        size--;
        return toRemove.getData();
    }

    public int getSize() {
        return size;
    }
}
