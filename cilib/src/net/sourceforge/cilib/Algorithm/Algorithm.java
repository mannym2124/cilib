/*
 * Algorithm.java
 *
 * Created on January 17, 2003, 4:54 PM
 *
 * 
 * Copyright (C) 2003, 2004 - CIRG@UP 
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

package net.sourceforge.cilib.Algorithm;

import java.util.Iterator;
import java.util.Vector;

import net.sourceforge.cilib.StoppingCondition.StoppingCondition;

/**
 * <p>
 * All algorithms in CILib should be subclasses of <code>Algorithm</code>. This class
 * handles stopping criteria, events, threading and measurements. Subclasses of 
 * <code>Algorithm</code> must provide an implementation for 
 * <code>protected abstract void performIteration()</code>.
 * If a subclass overrides {@link #initialise()} then it must call 
 * <code>super.initialise()</code>. Failure to do so will cause an 
 * {@link InitialisationException} to be thrown when {@link #run()} is called.
 * </p>
 *
 * @author  espeer
 */
public abstract class Algorithm implements Runnable {
    
    protected Algorithm() {
        stoppingConditions = new Vector();
        algorithmListeners = new Vector();
        running = false;
        initialised = false;
    }
    
    /**
     * Intialises the algorithm. Must be called before {@link #run()} is called.
     */
    public void initialise() {
        iterations = 0;
        running = true;
        initialised = true;
    }
    
    protected abstract void performIteration();

    /** 
     * Executes the algorithm.
     * <p />
     * @exception InitialisationException algorithm was not properly initialised.  
     */
    public final void run() {
    	if (! initialised) {
            throw new InitialisationException("Algorithm not initialised");
        }

    	if (localInstance.get() == null) {
    		localInstance.set(this);
    	}
    
        fireAlgorithmStarted();
        
        while (running && (! isFinished())) {
            performIteration();
            ++iterations;
    
            fireIterationCompleted();
        }
        
        if (running) {
        	fireAlgorithmFinished();
        }
        else {
        	fireAlgorithmTerminated();
        }
      
        // TODO: Figure this stuff out
        // initialised = false; // This breaks MultiStartOptimisationAlgorithm - does it make sense to set it false here?
        // localInstance.set(null); // By not setting to null we allow algorithm to be accessed after compeltion - should be fine
    }
    
    /**
     * Adds a stopping condition.
     *
     * @param stoppingCondition A {@link net.sourceforge.cilib.StoppingCondition.StoppingCondition} to be added.
     */
    public final void addStoppingCondition(StoppingCondition stoppingCondition) {
        stoppingCondition.setAlgorithm(this);
        stoppingConditions.add(stoppingCondition);
    }
    
    /**
     * Removes a stopping condition.
     *
     * @param stoppingCondition The {@link net.sourceforge.cilib.StoppingCondition.StoppingCondition} to be removed.
     */
    public final void removeStoppingCondition(StoppingCondition stoppingCondition) {
        stoppingConditions.remove(stoppingCondition);
    }
    
    /**
     * Adds an algorithm event listener. Event listeners are notified at various stages 
     * during the execution of an algorithm.
     * 
     * @param listener An {@link AlgorithmListener} to be added.
     */
    public final void addAlgorithmListener(AlgorithmListener listener) {
        algorithmListeners.add(listener);
    }
    public static byte _ciclops_exclude_algorithmListener = 1;
    
    /**
     * Removes an alogorithm event listener
     *
     * @param listener The {@link AlgorithmListener} to be removed.
     */
    public final void removeAlgorithmListener(AlgorithmListener listener) {
        algorithmListeners.remove(listener);
    }
    
    /**
     * Returns the number of iterations that have been performed by the algorihtm.
     *
     * @return The number of iterations.
     */
    public final int getIterations() {
        return iterations;
    }
    
    /**
     * Returns the percentage the algorithm is from completed (as a fraction). The
     * percentage complete is calculated based on the stopping condition that is
     * closest to finished.
     *
     * @return The percentage complete as a fraction.
     */
    public final double getPercentageComplete() {
    	double percentageComplete = 0;
        for (Iterator i = stoppingConditions.iterator(); i.hasNext(); ) {
            StoppingCondition condition = (StoppingCondition) i.next();
            if (condition.getPercentageCompleted() > percentageComplete) {
                percentageComplete = condition.getPercentageCompleted();
            }
        }
        return percentageComplete;
    }
    
    /**
     * Returns true if the algorithm has finished executing.
     * 
     * @return true if the algorithm is finished
     */
    public final boolean isFinished() {
        for (Iterator i = stoppingConditions.iterator(); i.hasNext(); ) {
            StoppingCondition condition = (StoppingCondition) i.next();
            if (condition.isCompleted()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Terminates the algorithm.
     */
    public final void terminate() {
        running = false;
    }
    
    /**
     * Accessor for the top-level algorithm running in the current thread
     * 
     * @return the instance of the algorithm that is running in the current thread,.
     */
    public static Algorithm get() {
        return (Algorithm) localInstance.get();
    }
    
    private void fireAlgorithmStarted() {
    	for (Iterator i = algorithmListeners.iterator(); i.hasNext(); ) {
    		AlgorithmListener listener = (AlgorithmListener) i.next();
    		listener.algorithmStarted(new AlgorithmEvent(this));
    	}
    }

    private void fireAlgorithmFinished() {
    	for (Iterator i = algorithmListeners.iterator(); i.hasNext(); ) {
    		AlgorithmListener listener = (AlgorithmListener) i.next();
    		listener.algorithmFinished(new AlgorithmEvent(this));
    	}
    }

    private void fireAlgorithmTerminated() {
    	for (Iterator i = algorithmListeners.iterator(); i.hasNext(); ) {
    		AlgorithmListener listener = (AlgorithmListener) i.next();
    		listener.algorithmTerminated(new AlgorithmEvent(this));
    	}
    }
    
    private void fireIterationCompleted() {
    	for (Iterator i = algorithmListeners.iterator(); i.hasNext(); ) {
    		AlgorithmListener listener = (AlgorithmListener) i.next();
    		listener.iterationCompleted(new AlgorithmEvent(this));
    	}
    }

    
    private Vector stoppingConditions;
    private Vector algorithmListeners;
    private int iterations;
    private volatile boolean running;
    private boolean initialised;
    
    private static ThreadLocal localInstance = new ThreadLocal(); 
}
