import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class generator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numberOfTimeSteps = sc.nextInt();
        int width = sc.nextInt();
        int height = sc.nextInt();

        FileWriter fileWriter = null;
        try {
            String fileName = "genInput" + numberOfTimeSteps + "_" + width + "_" + height;
            fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.printf(Locale.US,String.format("%d %d %d%n", numberOfTimeSteps, width, height));

            for (int time = 0; time < numberOfTimeSteps; time++) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        for (int i = 0; i < 3; i++) {
                            printWriter.printf(Locale.US,String.format(Locale.US,"%.6f ", (-1 + Math.random() * 2)));
                        }
                    }
                }
                printWriter.print("\n");
            }

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
