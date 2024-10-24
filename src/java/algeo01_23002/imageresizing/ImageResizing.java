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
                double[] normalizedXArray = new double[newWidth];  // Array store normalizedX values for the row

                for (int newX = 0; newX < newWidth; newX++) {
                    double origX = newX / scaleX;
                    int baseX = (int) Math.floor(origX);
                    double normalizedX = origX - baseX;

                    Matrix[] YInput = getYInputForGroup(finalOriginal, baseX, baseY);
                    rowYInput[newX] = YInput; // array of RGB matrices
                    normalizedXArray[newX] = normalizedX;  // normalizedX for each newX
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
        Matrix[] YInput = new Matrix[3]; // Array for RGB matrices
        YInput[0] = new Matrix(16, 1); // R
        YInput[1] = new Matrix(16, 1); // G
        YInput[2] = new Matrix(16, 1); // B

        int kF = 0, kFx = 4, kFy = 8, kFxy = 12;

        // Loop for f values (first 4 rows)
        for (int dy = 0; dy <= 1; dy++) {
            for (int dx = 0; dx <= 1; dx++) {
                int pixelX = baseX + dx;
                int pixelY = baseY + dy;

                double red = 0, green = 0, blue = 0;
                if (pixelX >= 0 && pixelX < original.getWidth() && pixelY >= 0 && pixelY < original.getHeight()) {
                    int pixel = original.getRGB(pixelX, pixelY);
                    red = (pixel >> 16) & 0xFF;
                    green = (pixel >> 8) & 0xFF;
                    blue = pixel & 0xFF;
                }

                YInput[0].setData(kF, 0, red);   // Red f
                YInput[1].setData(kF, 0, green); // Green f
                YInput[2].setData(kF, 0, blue);  // Blue f
                kF++;
            }
        }

        // for fx: (f(x+1) - f(x-1)) / 2
        for (int dy = 0; dy <= 1; dy++) {
            int pixelY = baseY + dy;
            for (int dx = 0; dx <= 1; dx++) {
                int leftX = baseX + dx - 1;
                int rightX = baseX + dx + 1;

                double redLeft = 0, greenLeft = 0, blueLeft = 0;
                double redRight = 0, greenRight = 0, blueRight = 0;

                if (leftX >= 0 && leftX < original.getWidth() && pixelY >= 0 && pixelY < original.getHeight()) {
                    int leftPixel = original.getRGB(leftX, pixelY);
                    redLeft = (leftPixel >> 16) & 0xFF;
                    greenLeft = (leftPixel >> 8) & 0xFF;
                    blueLeft = leftPixel & 0xFF;
                }

                if (rightX >= 0 && rightX < original.getWidth() && pixelY >= 0 && pixelY < original.getHeight()) {
                    int rightPixel = original.getRGB(rightX, pixelY);
                    redRight = (rightPixel >> 16) & 0xFF;
                    greenRight = (rightPixel >> 8) & 0xFF;
                    blueRight = rightPixel & 0xFF;
                }

                // Central difference formula
                double redFx = (redRight - redLeft) / 2.0;
                double greenFx = (greenRight - greenLeft) / 2.0;
                double blueFx = (blueRight - blueLeft) / 2.0;

                YInput[0].setData(kFx, 0, redFx);   // Red fx
                YInput[1].setData(kFx, 0, greenFx); // Green fx
                YInput[2].setData(kFx, 0, blueFx);  // Blue fx
                kFx++;
            }
        }

        // for fy: (f(y+1) - f(y-1)) / 2
        for (int dx = 0; dx <= 1; dx++) {
            int pixelX = baseX + dx;
            for (int dy = 0; dy <= 1; dy++) {
                int topY = baseY + dy - 1;
                int bottomY = baseY + dy + 1;

                double redTop = 0, greenTop = 0, blueTop = 0;
                double redBottom = 0, greenBottom = 0, blueBottom = 0;

                if (topY >= 0 && topY < original.getHeight() && pixelX >= 0 && pixelX < original.getWidth()) {
                    int topPixel = original.getRGB(pixelX, topY);
                    redTop = (topPixel >> 16) & 0xFF;
                    greenTop = (topPixel >> 8) & 0xFF;
                    blueTop = topPixel & 0xFF;
                }

                if (bottomY >= 0 && bottomY < original.getHeight() && pixelX >= 0 && pixelX < original.getWidth()) {
                    int bottomPixel = original.getRGB(pixelX, bottomY);
                    redBottom = (bottomPixel >> 16) & 0xFF;
                    greenBottom = (bottomPixel >> 8) & 0xFF;
                    blueBottom = bottomPixel & 0xFF;
                }

                // Central difference formula
                double redFy = (redBottom - redTop) / 2.0;
                double greenFy = (greenBottom - greenTop) / 2.0;
                double blueFy = (blueBottom - blueTop) / 2.0;

                YInput[0].setData(kFy, 0, redFy);   // Red fy
                YInput[1].setData(kFy, 0, greenFy); // Green fy
                YInput[2].setData(kFy, 0, blueFy);  // Blue fy
                kFy++;
            }
        }

        // Central difference for fxy: (f(x+1, y+1) - f(x+1, y-1) - f(x-1, y+1) + f(x-1, y-1)) / 4
        for (int dx = 0; dx <= 1; dx++) {
            int pixelX = baseX + dx;
            for (int dy = 0; dy <= 1; dy++) {
                int topLeftX = pixelX - 1, topLeftY = baseY + dy - 1;
                int topRightX = pixelX + 1, topRightY = baseY + dy - 1;
                int bottomLeftX = pixelX - 1, bottomLeftY = baseY + dy + 1;
                int bottomRightX = pixelX + 1, bottomRightY = baseY + dy + 1;

                double redTopLeft = 0, greenTopLeft = 0, blueTopLeft = 0;
                double redTopRight = 0, greenTopRight = 0, blueTopRight = 0;
                double redBottomLeft = 0, greenBottomLeft = 0, blueBottomLeft = 0;
                double redBottomRight = 0, greenBottomRight = 0, blueBottomRight = 0;

                if (topLeftX >= 0 && topLeftY >= 0 && topLeftX < original.getWidth() && topLeftY < original.getHeight()) {
                    int topLeftPixel = original.getRGB(topLeftX, topLeftY);
                    redTopLeft = (topLeftPixel >> 16) & 0xFF;
                    greenTopLeft = (topLeftPixel >> 8) & 0xFF;
                    blueTopLeft = topLeftPixel & 0xFF;
                }

                if (topRightX >= 0 && topRightY >= 0 && topRightX < original.getWidth() && topRightY < original.getHeight()) {
                    int topRightPixel = original.getRGB(topRightX, topRightY);
                    redTopRight = (topRightPixel >> 16) & 0xFF;
                    greenTopRight = (topRightPixel >> 8) & 0xFF;
                    blueTopRight = topRightPixel & 0xFF;
                }

                if (bottomLeftX >= 0 && bottomLeftY >= 0 && bottomLeftX < original.getWidth() && bottomLeftY < original.getHeight()) {
                    int bottomLeftPixel = original.getRGB(bottomLeftX, bottomLeftY);
                    redBottomLeft = (bottomLeftPixel >> 16) & 0xFF;
                    greenBottomLeft = (bottomLeftPixel >> 8) & 0xFF;
                    blueBottomLeft = bottomLeftPixel & 0xFF;
                }

                if (bottomRightX >= 0 && bottomRightY >= 0 && bottomRightX < original.getWidth() && bottomRightY < original.getHeight()) {
                    int bottomRightPixel = original.getRGB(bottomRightX, bottomRightY);
                    redBottomRight = (bottomRightPixel >> 16) & 0xFF;
                    greenBottomRight = (bottomRightPixel >> 8) & 0xFF;
                    blueBottomRight = bottomRightPixel & 0xFF;
                }

                // Central difference formula for mixed partial derivative
                double redFxy = (redBottomRight - redTopRight - redBottomLeft + redTopLeft) / 4.0;
                double greenFxy = (greenBottomRight - greenTopRight - greenBottomLeft + greenTopLeft) / 4.0;
                double blueFxy = (blueBottomRight - blueTopRight - blueBottomLeft + blueTopLeft) / 4.0;

                YInput[0].setData(kFxy, 0, redFxy);   // Red fxy
                YInput[1].setData(kFxy, 0, greenFxy); // Green fxy
                YInput[2].setData(kFxy, 0, blueFxy);  // Blue fxy
                kFxy++;
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