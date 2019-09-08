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
    static CloudData classify(CloudData cd, int lo,int hi, int dim){
        return fjPool.invoke(new Classify(cd,lo,hi,dim));
    }


    public static void main(String [] args) {

        CloudData cd = new CloudData();
        cd.readData(args[0]);
        int dim = cd.dim();
        tick();
        cd = classify(cd ,0,dim, dim);
        cd.averageWind.x=cd.averageWind.x/dim;
        cd.averageWind.y=cd.averageWind.y/dim;
        System.out.println(tock());

        cd = new CloudData();
        cd.readData(args[0]);
        dim = cd.dim();
        tick();
        cd = classify(cd ,0,dim, dim);
        cd.averageWind.x=cd.averageWind.x/dim;
        cd.averageWind.y=cd.averageWind.y/dim;
        System.out.println(tock());

        cd = new CloudData();
        cd.readData(args[0]);
        dim = cd.dim();
        tick();
        cd = classify(cd ,0,dim, dim);
        cd.averageWind.x=cd.averageWind.x/dim;
        cd.averageWind.y=cd.averageWind.y/dim;
        System.out.println(tock());

        cd = new CloudData();
        cd.readData(args[0]);
        dim = cd.dim();
        tick();
        cd = classify(cd ,0,dim, dim);
        cd.averageWind.x=cd.averageWind.x/dim;
        cd.averageWind.y=cd.averageWind.y/dim;
        System.out.println(tock());

        cd = new CloudData();
        cd.readData(args[0]);
        dim = cd.dim();
        tick();
        cd = classify(cd ,0,dim, dim);
        cd.averageWind.x=cd.averageWind.x/dim;
        cd.averageWind.y=cd.averageWind.y/dim;
        System.out.println(tock());

        cd = new CloudData();
        cd.readData(args[0]);
        dim = cd.dim();
        tick();
        cd = classify(cd ,0,dim, dim);
        cd.averageWind.x=cd.averageWind.x/dim;
        cd.averageWind.y=cd.averageWind.y/dim;
        System.out.println(tock());

        cd.writeData(args[1], cd.averageWind);
    }
}
