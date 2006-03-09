/*
 * RandomSampleDistroMutation.java
 * 
 * Created on Aug 20, 2005
 *
 * Copyright (C) 2003, 2004, 2005 - CIRG@UP 
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
package net.sourceforge.cilib.ec.mutationoperators;

import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.math.RandomNumber;

/**
 * @author otter
 * 
 * This abstract class defines another layer of abstraction for all mutation operators who mutates
 * by using sampling from random distributions, where the distributions are continous of nature.
 * 
 */
public abstract class ContinousRSDMutation<E extends Entity> extends RandomSampleDistributionMutation<E> {
    
	protected RandomNumber randomSample;
    
    public ContinousRSDMutation() {
        randomSample = new RandomNumber();
    }

    public void setRandomSample(RandomNumber randomSample) {
        this.randomSample = randomSample;
    }
}