import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;

public class CloudDataThreaded extends RecursiveTask<Integer[]>
{

    float[][][] convection; // vertical air movement strength, that evolves over time
    Integer[] classification; // cloud type per grid point, evolving over time
    int dimx, dimy, dimt; // data dimensions

    Vector averageWind;

    int lo; // arguments
    int hi;
    Vector[][][] advection;
    static final int SEQUENTIAL_CUTOFF=100000;

    CloudDataThreaded()
    {

    }

    CloudDataThreaded(Vector[][][] advect,float[][][] convect,int l, int h,int dimt, int dimx, int dimy)
    {
        advection = advect; convection = convect;lo=l; hi=h; this.dimx = dimx; this.dimy = dimy; this.dimt = dimt;
    }

    int[] locate(int pos) {
        int ind[] = new int[3];
        ind[0] = (int) pos / (dimx * dimy); // t
        ind[1] = (pos % (dimx * dimy)) / dimy; // x
        ind[2] = pos % (dimy); // y
        return ind;
    }

    int delocate(int t, int x, int y) {
        return t*dimx*dimy + x*dimy + y;
    }

    int dim() {
        return dimt * dimx * dimy;
    }

    protected Integer[] compute(){
        classification = new Integer[dimt*dimx*dimy];
        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            for (int h = lo; h < hi; h++) {
                float avex=0;
                float avey=0;
                int dividor = 0;

                int t = locate(h)[0];
                int x = locate(h)[1];
                int y = locate(h)[2];

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        //System.out.println(x+i+" , "+ (y+j));
                        if (!((x+i) < 0) && !((x+i) > dimx-1)) {
                            if(!((y+j) < 0) && !((y+j) > dimy-1)){
                                avex += advection[t][x + i][y + j].x;
                                avey += advection[t][x + i][y + j].y;
                                dividor++;
                                //System.out.println(x + i + " , " + (y + j));

                            }
                        }

                    }
                }

                avex=avex/dividor;
                avey=avey/dividor;
                double ave_magnitude = Math.sqrt(avex*avex + avey*avey);
                //System.out.println(dividor + " , " + ave_magnitude);
                if(Math.abs(convection[t][x][y])>ave_magnitude)
                {
                    classification[h]=0;
                }
                else if(ave_magnitude>0.2 && (ave_magnitude >= Math.abs(convection[t][x][y])))
                {
                    classification[h]=1;
                }
                else
                {
                    classification[h]=2;
                }
            }
        return classification;

        }
        else {
            CloudDataThreaded left = new CloudDataThreaded(advection,convection,lo,(hi+lo)/2,dimt, dimx, dimy);
            CloudDataThreaded right= new CloudDataThreaded(advection,convection,(hi+lo)/2,hi,dimt, dimx, dimy);

            // order of next 4 lines
            // essential – why?
            left.fork();
            Integer[] rightAns = right.compute();
            Integer[] leftAns  = left.join();

            Integer[] newArray = new Integer[dimt*dimx*dimy];
            for (int i = 0; i < dimt*dimx*dimy; i++) {
                if((rightAns[i]!=null)) {
                    newArray[i] = rightAns[i];
                }
                else if (leftAns[i]!=null) {
                    newArray[i]=leftAns[i];
                }
            }

            return newArray;
        }
    }

    void findAve() {
        averageWind = new Vector();
        for (int t = 0; t < dimt; t++){
            for (int x = 0; x < dimx; x++){
                for (int y = 0; y < dimy; y++) {
                    averageWind.x += advection[t][x][y].x;
                    averageWind.y += advection[t][x][y].y;
                    //System.out.println(averageWind.x + " " + averageWind.y);
                }
            }
        }
        averageWind.x = averageWind.x/dim();
        averageWind.y = averageWind.y/dim();
    }

    // read cloud simulation data from file
    void readData(String fileName){
        try{
            Scanner sc = new Scanner(new File(fileName), "UTF-8");

            // input grid dimensions and simulation duration in timesteps
            dimt = sc.nextInt();
            dimx = sc.nextInt();
            dimy = sc.nextInt();
            //System.out.println(dimt+" "+dimx + " "+dimy);
            // initialize and load advection (wind direction and strength) and convection
            advection = new Vector[dimt][dimx][dimy];
            convection = new float[dimt][dimx][dimy];
            for(int t = 0; t < dimt; t++)
                for(int x = 0; x < dimx; x++)
                    for(int y = 0; y < dimy; y++){
                        advection[t][x][y] = new Vector();
                        advection[t][x][y].x = Float.parseFloat(sc.next());
                        advection[t][x][y].y = Float.parseFloat(sc.next());
                        convection[t][x][y] = Float.parseFloat(sc.next());
                        //System.out.println(advection[t][x][y].x+" "+advection[t][x][y].y + " "+convection[t][x][y]);
                    }

            //classification = new Integer[dimt][dimx][dimy];
            sc.close();
        }
        catch (IOException e){
            System.out.println("Unable to open input file "+fileName);
            e.printStackTrace();
        }
        catch (InputMismatchException e){
            System.out.println("Malformed input file "+fileName);
            e.printStackTrace();
        }
    }

    // write classification output to file
    void writeData(String fileName, Vector wind, Integer[] classArray){
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("%d %d %d\n", dimt, dimx, dimy);
            printWriter.printf(Locale.US,"%f %f\n", wind.x, wind.y);
            int counter = 1;
            for(int i = 0; i < dim(); i++){
                printWriter.printf("%d ", classArray[i]);
                if(i==counter*dimx*dimy-1) {
                    printWriter.printf("\n");
                    counter++;
                }
            }

            printWriter.close();
        }
        catch (IOException e){
            System.out.println("Unable to open output file "+fileName);
            e.printStackTrace();
        }
    }
}
