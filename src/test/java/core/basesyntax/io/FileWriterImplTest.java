package core.basesyntax.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {

    private FileWriterImpl fileWriter;
    private final String filePath = "testFile.txt";

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void shouldWriteDataCorrectly_whenFilePathIsValid() throws IOException {
        String data = "Hello, FileWriter!";
        fileWriter.write(data, filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            assertEquals(data, line,
                    "The content written to the file should match the expected data");
        }
    }

    @Test
    void shouldCreateFile_whenDataIsWritten() {
        String data = "Checking file creation";
        fileWriter.write(data, filePath);

        File file = new File(filePath);
        assertTrue(file.exists(), "File should be created when data is written to it");
    }

    @Test
    void shouldThrowRuntimeException_whenFilePathIsInvalid() {
        String data = "Data that won't be written";
        String invalidPath = "/invalid/path/to/file.txt";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileWriter.write(data, invalidPath);
        }, "A RuntimeException should be thrown when an IOException occurs");

        assertTrue(exception.getMessage().contains("Can`t write data to file"),
                "The exception message should indicate a write failure");
    }

    @AfterEach
    void tearDown() {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
