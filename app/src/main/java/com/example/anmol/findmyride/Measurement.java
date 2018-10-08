package com.example.anmol.findmyride;

        import java.util.ArrayList;
        import java.util.Random;

        import org.ejml.simple.SimpleMatrix;


public class Measurement {

    // generate a set of measurements with gaussian noise
    public static Measurement[] create(
            double dt, double dtPos,
            int count,
            double posx, double posy, double devPos,
            double spdx, double spdy, double devSpd) {
        ArrayList<Measurement> v = new ArrayList<Measurement>();
        double t = 0;
        for (int i=0; i<count; i++) {
            double vx = spdx + (devSpd*new Random().nextGaussian());
            double vy = spdy + (devSpd*new Random().nextGaussian());
            if (i % (int)(dtPos/dt) == 0) {
                double x = posx + spdx*t + (devPos*new Random().nextGaussian());
                double y = posy + spdy*t + (devPos*new Random().nextGaussian());
                v.add(new Measurement(t, x, y, vx, vy, v.size()>0 ? v.get(v.size()-1) : null));
            } else {
                v.add(new Measurement(t, vx, vy, v.size()>0 ? v.get(v.size()-1) : null));
            }
            t += dt;
        }
        return v.toArray(new Measurement[v.size()]);
    }

    private static double start = Double.NaN;

    public  double dt = 0;
    public  double t = 0;

    private double posx = 0;
    private double posy = 0;

    private double spdx = 0;
    private double spdy = 0;

    public boolean update = true;

    public Measurement(double time, double vx, double vy, Measurement prev) throws NumberFormatException {

        this(time, prev!=null ? prev.posx : Double.NaN, prev!=null ? prev.posy : Double.NaN, vx, vy, prev);
        update = false;

    }

    public Measurement(double time, double x, double y, double vx, double vy, Measurement prev) throws NumberFormatException {

        posx = x;
        posy = y;

        spdx = vx;
        spdy = vy;

        if (Double.isNaN(start)) {
            start = time;
        }
        t = time - start;

        if (prev!=null) {
            dt = t - prev.t;
        }

    }

    public SimpleMatrix toMeasurement() {
        return new SimpleMatrix(new double[][] { {posx}, {posy}, {spdx}, {spdy} });
    }
}