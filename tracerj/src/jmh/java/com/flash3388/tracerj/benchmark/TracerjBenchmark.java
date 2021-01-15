package com.flash3388.tracerj.benchmark;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import calculus.trajectories.Trajectory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;

@State(Scope.Thread)
public class TracerjBenchmark {
    @Setup(Level.Invocation)
    public void setup() {
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void run(Blackhole blackhole) throws Exception {
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE, new Waypoint(0, 0 ,0), new Waypoint(1, 0, 0), new Waypoint(2, 0, 0), new Waypoint(3, 0, 0));
        blackhole.consume(trajectory);
    }
}
