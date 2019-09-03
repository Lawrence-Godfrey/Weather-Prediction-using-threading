public class main {

    public static void main(String [] args)
    {
        CloudData cd = new CloudData();
        cd.readData("/home/lawrence/IdeaProjects/untitled/src/simplesample_input.txt");
        cd.findAve();
        System.out.println(cd.averageWind.x + " , " + cd.averageWind.y);
        cd.classify();
        cd.writeData("/home/lawrence/IdeaProjects/untitled/src/output.txt", cd.averageWind );
    }
}
