package algeo01_23002.imageresizing;

import algeo01_23002.mathmodels.Interpolation;
import algeo01_23002.types.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import java.util.Scanner;

import algeo01_23002.imageresizing.ImageResizing;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageResizingTest {


    private Matrix matrix;

    @BeforeEach
    public void setUp() {
        matrix = new Matrix(2,3);
        matrix.setAllData(new double[][] {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        });

    }


    /* cukup sekali test aja, lama, testnya jadi ke block
    @Nested
    @DisplayName("Image Resizing Test")
    class MainTest {
        @ParameterizedTest(name = "Test {index}: {0}")
        @MethodSource("imageResizingTestCases")
        public void testImageResizing(String description, String inputPath, String outputPath) throws IOException, InterruptedException, ExecutionException {
            Scanner scanner = new Scanner(System.in);
            BufferedImage originalImage = null;
            try {
                originalImage = ImageIO.read(new File(inputPath));
            } catch (IOException e) {
                System.err.println("Error loading image: " + e.getMessage());
                return;
            }

            // Resize the image
            BufferedImage resizedImage = ImageResizing.resizeImage(originalImage, 5, 1);

            // Save the resized image
            try {
                ImageIO.write(resizedImage, "jpg", new File(outputPath));
                System.out.println("Resized image saved at: " + outputPath);
            } catch (IOException e) {
                System.err.println("Error saving resized image: " + e.getMessage());
            } finally {
                scanner.close();
            }
        }
        static Stream<Object[]> imageResizingTestCases() {
            return Stream.of(
                    new Object[]{"Image (1)",
                            "test/java/algeo01_23002/imageresizing/Screenshot (410).png",
                            "test/java/algeo01_23002/imageresizing/Screenshot (410) 2.png"

                    },
                    new Object[]{"Image (2)",
                            "test/java/algeo01_23002/imageresizing/Screenshot (410).png",
                            "test/java/algeo01_23002/imageresizing/Screenshot (410) 3.png"
                    }
            );
        }
    }
     */

    // Helper method to create matrices using data arrays
    private static Matrix createMatrix(double[][] data) {
        Matrix matrix = new Matrix(data.length, data[0].length);
        matrix.setAllData(data);
        return matrix;
    }



}
