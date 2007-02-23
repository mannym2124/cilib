/*
 * MeasurementSuite.java
 *
 * Created on February 5, 2003, 12:56 PM
 *
 * 
 * Copyright (C) 2003 - 2006 
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science 
 * University of Pretoria
 * South Africa
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *   
 */

package net.sourceforge.cilib.simulator;

import java.io.Serializable;
import java.util.ArrayList;

import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.measurement.Measurement;

/**
 * The <code>MeasurementSuite</code> is essentially a collection of measurements.
 *
 * @see net.sourceforge.Measurement.Measurement
 *
 * @author  Edwin Peer
 */
public class MeasurementSuite implements Serializable {
	
	private String file;
    private int samples;
    private int resolution;
    private ArrayList<Measurement> measurements;
    private SynchronizedOutputBuffer buffer;
    
    /** Creates a new instance of MeasurementSuite */
    public MeasurementSuite() {
        measurements = new ArrayList<Measurement>();
        file = "results.txt";
        samples = 30;
        resolution = 1;
    }
    
    public void initialise() {
        buffer = new SynchronizedOutputBuffer(file, measurements.size(), samples);
        buffer.write("# 0 - Iterations");
        for (Measurement measurement : measurements) {
            buffer.writeDescription(measurement);
        }
    }
    
    /**
     * Sets the output file to record the measurements in.
     *
     * @param file The name of the output file.
     */
    public void setFile(String file) {
        this.file = file;
    }
    
    /**
     * Sets the number of samples to take for each measurement. Each sample results
     * in the experiment being performed again.
     *
     * @param samples The number of samples.
     */
    public void setSamples(int samples) {
        this.samples = samples;
    }
    
    /**
     * Accessor for the number of samples to take for each measurement.
     *
     * @return The number of samples.
     */
    public int getSamples() {
        return samples;
    }
    
    /**
     * Sets the resolution of the results. The resolution determines how offen 
     * results are logged to file. If the resolution is 10 then results are
     * logged every 10 iterations.
     *
     * @param The result resolution.
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
    
    /**
     * Accessor for the resolution of the results.
     *
     * @param The result resolution.
     */
    public int getResolution() {
        return resolution;
    }
    
    public SynchronizedOutputBuffer getOutputBuffer() {
        return buffer;
    }
    
    /**
     * Adds a measurement to the suite.
     * 
     * @param measurement The measurement to be added.
     */
    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
    }
    
    public void measure(Algorithm algorithm) {
        for (Measurement measurement : measurements) {
            buffer.writeMeasuredValue(measurement.getValue(), algorithm, measurement);
        }
    }	

}