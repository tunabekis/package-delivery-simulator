/**
 * A city that owns a stack of waiting packages and a queue of stationed
 * vehicles.
 */
public class Cities {

    public String name;

    private final MyStack<Packages> packageStack = new MyStack<>();
    private final MyQueue<Vehicles> vehicleQueue = new MyQueue<>();

    public MyStack<Packages> getPackageStack() {
        return packageStack;
    }

    public MyQueue<Vehicles> getVehicleQueue() {
        return vehicleQueue;
    }

    /**
     * Builds this city's section of the final report. Note that this
     * drains the package stack and vehicle queue as it reads them, so it
     * is only safe to call once, after all missions have completed.
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(name).append("\n");

        report.append("Packages:").append("\n");
        int packageCount = packageStack.getSize();
        for (int i = 0; i < packageCount; i++) {
            report.append(packageStack.pop().getName()).append("\n");
        }

        report.append("Vehicles:").append("\n");
        int vehicleCount = vehicleQueue.getSize();
        for (int i = 0; i < vehicleCount; i++) {
            report.append(vehicleQueue.dequeue().getName()).append("\n");
        }

        report.append("-------------").append("\n");
        return report.toString();
    }
}
