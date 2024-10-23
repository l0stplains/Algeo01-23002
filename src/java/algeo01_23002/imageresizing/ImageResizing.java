package algeo01_23002.imageresizing;

import algeo01_23002.mathmodels.Interpolation;
import algeo01_23002.types.Matrix;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageResizing {
    public static BufferedImage extendImage(BufferedImage original) {
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        // Create a new image with padding (2 pixels on each side for bicubic interpolation)
        int extendedWidth = originalWidth + 4;
        int extendedHeight = originalHeight + 4;

        BufferedImage extendedImage = new BufferedImage(extendedWidth, extendedHeight, original.getType());

        // Fill in the extended image
        for (int y = 0; y < extendedHeight; y++) {
            for (int x = 0; x < extendedWidth; x++) {
                int originalX = x - 2;
                int originalY = y - 2;

                if (originalX >= 0 && originalX < originalWidth && originalY >= 0 && originalY < originalHeight) {
                    extendedImage.setRGB(x, y, original.getRGB(originalX, originalY));
                } else {
                    int clampedX = Math.max(0, Math.min(originalWidth - 1, originalX));
                    int clampedY = Math.max(0, Math.min(originalHeight - 1, originalY));
                    extendedImage.setRGB(x, y, original.getRGB(clampedX, clampedY));
                }
            }
        }

        return extendedImage;
    }

    public static BufferedImage resizeImage(BufferedImage original, double scaleX, double scaleY) {
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();
        int newWidth = (int) (originalWidth * scaleX);
        int newHeight = (int) (originalHeight * scaleY);

        Matrix redChannel = new Matrix(newHeight, newWidth);
        Matrix greenChannel = new Matrix(newHeight, newWidth);
        Matrix blueChannel = new Matrix(newHeight, newWidth);

        Matrix XInverse = Interpolation.getXInverseBicubicSpline();
        original = extendImage(original);

        // Pooling
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Using Multithread
        for (int newY = 0; newY < newHeight; newY++) {
            final int row = newY;

            BufferedImage finalOriginal = original;
            executor.submit(() -> {
                double origY = row / scaleY;
                int baseY = (int) Math.floor(origY);
                double normalizedY = origY - baseY;

                // Store YInput and normalizedX for the entire row group
                Matrix[][] rowYInput = new Matrix[newWidth][3]; // Change to 2D array for RGB
                double[] normalizedXArray = new double[newWidth];  // Array to store normalizedX values for the row

                for (int newX = 0; newX < newWidth; newX++) {
                    double origX = newX / scaleX;
                    int baseX = (int) Math.floor(origX);
                    double normalizedX = origX - baseX;

                    Matrix[] YInput = getYInputForGroup(finalOriginal, baseX, baseY);
                    rowYInput[newX] = YInput; // Store the array of RGB matrices
                    normalizedXArray[newX] = normalizedX;  // Store normalizedX for each newX
                }

                // Perform interpolation for the whole row
                interpolateRow(redChannel, greenChannel, blueChannel, row, rowYInput, normalizedXArray, normalizedY, XInverse);
            });
        }

        // Shutdown the executor and wait for all tasks to finish
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return matrixToImage(new Matrix[]{redChannel, greenChannel, blueChannel});
    }




    // Collect YInput for a row group
    private static Matrix[] getYInputForGroup(BufferedImage original, int baseX, int baseY) {
        Matrix[] YInput = new Matrix[3]; // Array for Input RGB matrices
        YInput[0] = new Matrix(16, 1); // For Red
        YInput[1] = new Matrix(16, 1); // For Green
        YInput[2] = new Matrix(16, 1); // For Blue
        int k = 0;

        for (int dy = -1; dy <= 2; dy++) {//calculating derivative
            for (int dx = -1; dx <= 2; dx++) {
                int pixelX = baseX + dx;
                int pixelY = baseY + dy;

                double red = 0, green = 0, blue = 0;
                if (pixelX >= 0 && pixelX < original.getWidth() && pixelY >= 0 && pixelY < original.getHeight()) {
                    int pixel = original.getRGB(pixelX, pixelY);

                    // Extract Red channel
                    red = (pixel >> 16) & 0xFF;

                    // Extract Green channel
                    green = (pixel >> 8) & 0xFF;

                    // Extract Blue channel
                    blue = pixel & 0xFF;
                }

                YInput[0].setData(k, 0, red);
                YInput[1].setData(k, 0, green);
                YInput[2].setData(k, 0, blue);
                k++;
            }
        }
        return YInput;
    }



    private static void interpolateRow(Matrix redChannel, Matrix greenChannel, Matrix blueChannel, int newY,
                                       Matrix[][] rowYInput, double[] normalizedXArray, double normalizedY, Matrix XInverse) {
        for (int newX = 0; newX < rowYInput.length; newX++) {
            Matrix YInputRed = rowYInput[newX][0];   // Get red channel matrix
            Matrix YInputGreen = rowYInput[newX][1]; // Get green channel matrix
            Matrix YInputBlue = rowYInput[newX][2];  // Get blue channel matrix

            // Interpolate for Red channel using the corresponding normalizedX for each newX
            double redValue = Interpolation.bicubicSplineInterpolation(YInputRed, normalizedXArray[newX], normalizedY, XInverse);
            redChannel.setData(newY, newX, redValue);

            // Interpolate for Green channel
            double greenValue = Interpolation.bicubicSplineInterpolation(YInputGreen, normalizedXArray[newX], normalizedY, XInverse);
            greenChannel.setData(newY, newX, greenValue);

            // Interpolate for Blue channel
            double blueValue = Interpolation.bicubicSplineInterpolation(YInputBlue, normalizedXArray[newX], normalizedY, XInverse);
            blueChannel.setData(newY, newX, blueValue);
        }
    }





    // Convert RGB matrices back to BufferedImage
    public static BufferedImage matrixToImage(Matrix[] rgbMatrices) {
        Matrix redChannel = rgbMatrices[0];
        Matrix greenChannel = rgbMatrices[1];
        Matrix blueChannel = rgbMatrices[2];

        int width = redChannel.getColsCount();
        int height = redChannel.getRowsCount();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = (int) redChannel.getData(y, x);
                int green = (int) greenChannel.getData(y, x);
                int blue = (int) blueChannel.getData(y, x);

                red = Math.max(0, Math.min(255, red));
                green = Math.max(0, Math.min(255, green));
                blue = Math.max(0, Math.min(255, blue));

                int rgb = (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, rgb);
            }
        }

        return image;
    }
}