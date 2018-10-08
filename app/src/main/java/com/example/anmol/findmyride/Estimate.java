package com.example.anmol.findmyride;

import org.ejml.simple.SimpleMatrix;

public class Estimate {

    private double time = 0;

    private double posx_mea = 0;
    private double posy_mea = 0;
    private double spdx_mea = 0;
    private double spdy_mea = 0;

    public double posx_est = 0;
    public double posy_est = 0;
    private double spdx_est = 0;
    private double spdy_est = 0;

    private double kposx = 0;
    private double kposy = 0;
    private double kspdx = 0;
    private double kspdy = 0;
    public String cordinates="";

   // public  Estimate(){}

    public Estimate(double t, SimpleMatrix x, SimpleMatrix z, SimpleMatrix K) {

        time = t;

        posx_mea = z.get(0);
        posy_mea = z.get(1);
        spdx_mea = z.get(2);
        spdy_mea = z.get(3);

        posx_est = x.get(0);
        posy_est = x.get(1);
        spdx_est = x.get(2);
        spdy_est = x.get(3);

        kposx = K.get(0,0);
        kposy = K.get(1,0);
        kspdx = K.get(2,0);
        kspdy = K.get(3,0);
        cordinates = posx_est + "," + posy_est;

    }
 public String co() {
        cordinates = posx_est + "," + posy_est;
        return cordinates;
    }
}
