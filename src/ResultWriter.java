import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes the final state of every city to {@value #RESULT_FILE}.
 */
public class ResultWriter {

    private static final String RESULT_FILE = "result.txt";

    public void write(Cities[] cities) throws IOException {
        StringBuilder report = new StringBuilder();
        for (Cities city : cities) {
            report.append(city.generateReport());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DataLoader.DATA_DIR + RESULT_FILE))) {
            writer.write(report.toString());
        }
    }
}
