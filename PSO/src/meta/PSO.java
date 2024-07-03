package meta;

public class PSO {

    private static final int maxIter = 100;
    protected static double inertia = 0.95; //inercja <0,1>
    static double c1 = 1.5;                 //wsp poznawczy <0,2>
    static double c2 = 0.2;                 //wsp spoleczny

    public static void run(Function function){
        Swarm swarm = new Swarm(function, -10, 10);
        for (int i = 0; i < maxIter; i++) {
            swarm.updatePosition(function);
        }
        System.out.println(swarm.getBestParticle().getBestY());
        System.out.println(swarm.getBestParticle().getBestX());
        System.out.println(swarm.getBestParticle().getBestFitness());

    }
}
