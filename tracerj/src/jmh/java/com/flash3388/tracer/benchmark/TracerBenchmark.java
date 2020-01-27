package com.flash3388.tracer.benchmark;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import calculus.trajectories.Trajectory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;

@State(Scope.Thread)
public class TracerBenchmark {
    private Trajectory trajectory;
    private Random rnd;

    @Setup(Level.Invocation)
    public void setup() {
        trajectory = new Trajectory(SplineType.CUBIC_HERMITE, new Waypoint(0, 0 ,0), new Waypoint(1, 0, 0));
        rnd = new Random();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void run(Blackhole blackhole) throws Exception {
        double s = trajectory.angleRadAt(rnd.nextDouble());
        blackhole.consume(s);
    }
}
