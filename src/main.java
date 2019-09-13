/**
 * Runs unthreaded version 6 times and prints out recorded times in ms
 * takes in an input file name and output file name.
 * @author Lawrence Godfrey
 */
public class main {

    static long startTime = 0;

    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock() {
        return (System.currentTimeMillis() - startTime);
    }

    public static void main(String [] args)
    {
        CloudData cd = new CloudData();
        cd.readData(args[0]);
        tick();
        cd.findAve();
        cd.classify();
        System.out.println(tock());
        tick();

        cd = new CloudData();
        cd.readData(args[0]);
        tick();
        cd.findAve();
        cd.classify();
        System.out.println(tock());
        tick();

        cd = new CloudData();
        cd.readData(args[0]);
        tick();
        cd.findAve();
        cd.classify();
        System.out.println(tock());
        tick();

        cd = new CloudData();
        cd.readData(args[0]);
        tick();
        cd.findAve();
        cd.classify();
        System.out.println(tock());
        tick();

        cd = new CloudData();
        cd.readData(args[0]);
        tick();
        cd.findAve();
        cd.classify();
        System.out.println(tock());
        tick();

        cd = new CloudData();
        cd.readData(args[0]);
        tick();
        cd.findAve();
        cd.classify();
        System.out.println(tock());
        tick();

        cd.writeData(args[1], cd.averageWind );
    }
}
