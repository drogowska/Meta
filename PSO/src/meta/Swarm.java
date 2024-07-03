package meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Swarm {

    private List<Particle> swarm;
    private final int swarmSize = 100;
    private Particle bestParticle;

    public Swarm(Function fun, int from, int to) {
        init(fun, from, to);
    }

    private void init(Function fun, int from, int to) {
        swarm = new ArrayList<>();
        for(int i = 0; i < swarmSize; i++) {
            swarm.add(new Particle(from, to));
        }
        countFitness(fun);
        swarm.forEach(particle -> particle.setBestFitness(particle.getFitness()));
        bestParticle = swarm.get(0);
        setBestParticle();
    }

    private void countFitness(Function function) {
        swarm.forEach(particle -> particle.countFitness(function));
    }

    private void updateFitness(Function f) {
        countFitness(f);
        swarm.forEach(particle -> {
            if(particle.getFitness() < particle.getBestFitness()) {
                particle.setBestFitness(particle.getFitness());
                particle.setBestX(particle.getX());
                particle.setBestY(particle.getY());
            }
        });
        setBestParticle();
    }

    private void setBestParticle() {
        bestParticle = Collections.min(swarm);
    }

    private void updateVelocity() {
        swarm.forEach(particle -> {
            particle.setVelocityX(PSO.inertia * particle.getVelocityX() +
                    Math.random() * PSO.c1 * (particle.getBestX() - particle.getX()) +
                    Math.random() * PSO.c2 * (bestParticle.getX() - particle.getX()));
            particle.setVelocityY(PSO.inertia * particle.getVelocityY() +
                    Math.random() * PSO.c1 * (particle.getBestY() - particle.getY()) +
                    Math.random() * PSO.c2 * (bestParticle.getY() - particle.getY()));
        });

    }

    public void updatePosition(Function f) {
        updateVelocity();
        swarm.forEach(Particle::updatePosition);
        updateFitness(f);
    }

    public Particle getBestParticle() {
        return bestParticle;
    }
}
