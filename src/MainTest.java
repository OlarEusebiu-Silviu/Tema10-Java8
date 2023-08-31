import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    public void testMainFunctionality() throws IOException {
        // Crearea unui fișier temporar de intrare
        Path inputPath = Files.createTempFile("input", ".txt");
        Files.write(inputPath, "Alice,Johnson,2000-01-15\nBob,Smith,1995-02-25\nEve,Williams,1998-01-10".getBytes());

        // Definirea parametrilor de test
        String inputFilename = inputPath.toString();
        int targetMonth = 1;  // Luna ianuarie
        String outputFilename = "output.txt";

        // Rularea funcției principale
        Main.main(new String[]{inputFilename, Integer.toString(targetMonth), outputFilename});

        // Citirea datelor din fișierul de ieșire generat
        List<String> outputLines = Files.readAllLines(Path.of(outputFilename));

        // Verificarea datelor din fișierul de ieșire
        assertEquals(2, outputLines.size()); // Doar 2 persoane au ziua de naștere în ianuarie
        assertTrue(outputLines.contains("Alice Johnson"));
        assertTrue(outputLines.contains("Eve Williams"));

        // Ștergerea fișierelor temporare create pentru test
        Files.deleteIfExists(inputPath);
        Files.deleteIfExists(Path.of(outputFilename));
    }
}
