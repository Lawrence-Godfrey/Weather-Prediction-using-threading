import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

public class Classify extends RecursiveTask<CloudData> {

    float[][][] convection; // vertical air movement strength, that evolves over time
    int[][][] classification; // cloud type per grid point, evolving over time
    Vector[][][] advection;
    int dimx, dimy, dimt; // data dimensions
    int dim;
    int t, x, y;
    Vector averageWind;

    CloudData cd;

    int lo; // arguments
    int hi;

    static final int SEQUENTIAL_CUTOFF = 90000;

    Classify() {
    }

    Classify(CloudData cd, int l, int h, int dim) {
        lo = l;
        hi = h;
        this.dim = dim;
        this.cd = cd;
        averageWind = new Vector();
    }

    void locate(int pos) {
        this.t = (int) pos / (dimx * dimy); // t
        this.x = (pos % (dimx * dimy)) / dimy; // x
        this.y = pos % (dimy); // y
    }

    int dim() {
        return dimt * dimx * dimy;
    }

    protected CloudData compute() {
        if ((hi - lo) < SEQUENTIAL_CUTOFF) {

            this.convection = cd.convection;
            this.advection = cd.advection;
            this.classification = cd.classification;
            this.dimt = cd.dimt;
            this.dimx = cd.dimx;
            this.dimy = cd.dimy;

            for (int h = lo; h < hi; h++) {
                float avex = 0;
                float avey = 0;
                int dividor = 0;

                locate(h);

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        //System.out.println(x+i+" , "+ (y+j));
                        if (!((x + i) < 0) && !((x + i) > dimx - 1)) {
                            if (!((y + j) < 0) && !((y + j) > dimy - 1)) {
                                avex += advection[t][x + i][y + j].x;
                                avey += advection[t][x + i][y + j].y;
                                dividor++;
                                //System.out.println(x + i + " , " + (y + j));

                            }
                        }

                    }
                }

                avex = avex / dividor;
                avey = avey / dividor;
                double ave_magnitude = Math.sqrt(avex * avex + avey * avey);
                //System.out.println(dividor + " , " + ave_magnitude);
                if (Math.abs(convection[t][x][y]) > ave_magnitude) {
                    cd.classification[t][x][y] = 0;
                } else if (ave_magnitude > 0.2 && (ave_magnitude >= Math.abs(convection[t][x][y]))) {
                    cd.classification[t][x][y] = 1;
                } else {
                    cd.classification[t][x][y] = 2;
                }

                averageWind.x += advection[t][x][y].x;
                averageWind.y += advection[t][x][y].y;
            }
            return cd;

        } else {
            Classify left = new Classify(cd, lo, (hi + lo) / 2, dim());
            Classify right = new Classify(cd, (hi + lo) / 2, hi, dim());

            // order of next 4 lines
            // essential – why?
            left.fork();
            right.compute();
            left.join();
            cd.averageWind.x+=left.averageWind.x+right.averageWind.x;
            cd.averageWind.y+=left.averageWind.y+right.averageWind.y;
            return cd;
        }
    }

}