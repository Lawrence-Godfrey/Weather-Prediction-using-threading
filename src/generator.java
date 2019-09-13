import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
/**
 * produces a input file
 * @author Lawrence Godfrey
 */
public class generator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileWriter fileWriter = null;
        try {
            String fileName = "input" + 10 + "-" + 512 + "-" + 512;
            fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf(Locale.US,String.format("%d %d %d%n", 10, 512, 512));

            for (int time = 0; time < 10; time++) {
                for (int x = 0; x < 512; x++) {
                    for (int y = 0; y < 512; y++) {
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
