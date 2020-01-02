package com.flash3388.tracer.benchmark;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import tracer.controllers.MotionControllerParameters;
import tracer.controllers.PidControllerParameters;
import tracer.controllers.TankTrajectoryController;
import tracer.motion.FrcParameters;
import tracer.motion.MotionParameters;
import tracer.trajectories.TankTrajectory;

import java.util.Arrays;

public class TracerBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void run(Blackhole blackhole) throws Exception {
        Waypoint start = new Waypoint(0, 0, 0);
        Waypoint end = new Waypoint(2, 0, 0);
        TankTrajectory trajectory = new TankTrajectory(SplineType.CUBIC_HERMITE, Arrays.asList(start, end), 0.65);
        MotionParameters max = new MotionParameters(0.7, FrcParameters.DEFAULT_ACCELERATION_METERS_PER_SECOND_PER_SECOND, FrcParameters.DEFAULT_JERK_METERS_PER_SECOND_PER_SECOND_PER_SECOND);
        MotionControllerParameters motionControllerParameters = new MotionControllerParameters(2, 0.8, 0.09 * 12);
        PidControllerParameters pidControllerParameters = new PidControllerParameters(1, 0, 0);
        TankTrajectoryController controller = new TankTrajectoryController(trajectory, max, motionControllerParameters, pidControllerParameters, 12, 0);

        blackhole.consume(pidControllerParameters);
        blackhole.consume(motionControllerParameters);
        blackhole.consume(controller);

        controller.reset();
    }
}
