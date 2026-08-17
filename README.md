# Package Delivery Simulator

A small Java simulation of a package delivery network. Cities hold vehicles
and packages; a series of missions moves a vehicle (with a batch of
packages) from an origin city, through a relay city, to a destination city,
dropping some packages off along the way.

The project's real purpose is educational: it implements its own **stack**,
**queue**, and **doubly linked list** from scratch (no `java.util` collections)
and uses them to drive the simulation end to end.

## How it works

1. **Load data** — `DataLoader` reads city names, vehicles, packages, and
   missions from the text files in `CMPE223HW1/src/txtfiles/`.
2. **Station the fleet** — every vehicle is enqueued into its home city's
   vehicle queue, and every package is pushed onto its home city's package
   stack.
3. **Run missions** — for each mission, `MissionService` builds a "convoy"
   (a doubly linked list starting with the vehicle, followed by the packages
   it's carrying), drops two packages off at the relay city, and delivers
   the rest — plus the vehicle — to the destination city.
4. **Report** — `ResultWriter` writes each city's final package and vehicle
   state to `CMPE223HW1/src/txtfiles/result.txt`.

## Project structure

```
CMPE223HW1/
└── src/
    ├── Main.java              Entry point: wires loading, missions, and reporting together
    ├── DataLoader.java        Reads cities/vehicles/packages/missions from text files
    ├── MissionService.java    Executes delivery missions
    ├── ResultWriter.java      Writes the final report
    ├── Cities.java            A city's package stack + vehicle queue, and its report
    ├── Packages.java          A package (name, current city)
    ├── Vehicles.java          A vehicle (name, current city, capacity)
    ├── Transportable.java     Shared contract for anything that rides in a convoy
    ├── MyStack.java           LIFO stack (custom data structure)
    ├── MyQueue.java           FIFO queue (custom data structure)
    ├── DoublyLinkedList.java  Doubly linked list backing both of the above
    ├── Node.java              Linked list node
    └── txtfiles/              Input data (cities, vehicles, packages, missions) and result.txt output
```

## Input file formats

- `cities.txt` — one city name per line.
- `vehicles.txt` — one vehicle per line: `name city capacity`.
- `packages.txt` — one package per line: `name city`.
- `missons.txt` — one mission per line, six `-`-separated fields:
  `origin-relay-destination-firstBatchSize-secondBatchSize-dropIndexes`,
  where `dropIndexes` is a pair of comma-separated positions (e.g. `1,2`)
  identifying which two packages to leave at the relay city.

## Technologies used

- Java 17 (no external libraries or build tool — plain `javac`/`java`)
- IntelliJ IDEA project files (`.idea/`, `.iml`) for out-of-the-box import

## Building and running

From the `CMPE223HW1` directory (this is required so the relative
`src/txtfiles/...` paths resolve correctly):

```bash
javac -d out src/*.java
java -cp out Main
```

Or simply open `CMPE223HW1` as a project in IntelliJ IDEA and run `Main`.

The result is written to `src/txtfiles/result.txt`.
