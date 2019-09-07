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
        cd.readData("/home/lawrence/IdeaProjects/untitled/src/largesample_input.txt");
        cd.findAve();
        //System.out.println(cd.averageWind.x + " , " + cd.averageWind.y);
        tick();
        cd.classify();
        System.out.println(tock());
        cd.writeData("/home/lawrence/IdeaProjects/untitled/src/output.txt", cd.averageWind );
    }
}
