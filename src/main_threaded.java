import java.util.concurrent.ForkJoinPool;

public class main_threaded
{
    static long startTime = 0;

    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock(){
        return (System.currentTimeMillis() - startTime);
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static Integer[] classify(Vector[][][] advection, float[][][] convection,int lo,int hi, int dimt, int dimx, int dimy){
        return fjPool.invoke(new CloudDataThreaded(advection,convection,lo,hi,dimt, dimx, dimy));
    }


    public static void main(String [] args) {

        CloudDataThreaded cdt = new CloudDataThreaded();
        cdt.readData("/home/lawrence/IdeaProjects/untitled/src/largesample_input.txt");
        cdt.findAve();
        //System.out.println(cd.averageWind.x + " , " + cd.averageWind.y);
        tick();

        Integer classArray[] = classify(cdt.advection, cdt.convection, 0,cdt.dim(),cdt.dimt, cdt.dimx, cdt.dimy);
        System.out.println(tock());

        cdt.writeData("/home/lawrence/IdeaProjects/untitled/src/output.txt", cdt.averageWind, classArray);

    }
}
