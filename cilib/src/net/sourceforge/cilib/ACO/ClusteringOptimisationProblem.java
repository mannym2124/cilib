/*
 * ClusteringOptimisationProblem.java
 *
 * Created on Jul 28, 2004
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
 */
package net.sourceforge.cilib.ACO;

import net.sourceforge.cilib.Container.Matrix;
import net.sourceforge.cilib.Domain.DomainComponent;
import net.sourceforge.cilib.Problem.DataSet;
import net.sourceforge.cilib.Problem.Fitness;

/**
 * @author gpampara
 */
public class ClusteringOptimisationProblem implements DiscreteOptimisationProblem {
	private DataSet dataSet;
	private Matrix grid;

	protected Fitness calculateFitness(Object solution) { // FIXME: What do I do here?
		return null;
	}

	public DomainComponent getDomain() {
		return null;
	}
	
	public Matrix getGrid() {
		return grid;
	}
	
	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
		
		initialiseDataSet();
	}
	
	private void initialiseDataSet() {
		System.out.println("Now initialising the data set for a ClusteringOptimisationProblem");		
	}

	// This is an empty implementation to comply with the template 
	// method design pattern
	public void degrade() {
	}
}