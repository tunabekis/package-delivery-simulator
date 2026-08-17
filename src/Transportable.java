/**
 * Common contract for anything that can travel inside the mission convoy
 * (a {@link DoublyLinkedList} mixing a {@link Vehicles} node with several
 * {@link Packages} nodes). Implemented independently by both classes since
 * a vehicle is not a kind of package - a shared interface avoids modeling
 * a false "is-a" relationship between them.
 */
public interface Transportable {

    String getName();

    String getCity();

    void setCity(String city);
}
