//  GenericBinarySolution.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2014 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.solution.impl;

import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.binarySet.BinarySet;

import java.util.HashMap;

/**
 * Defines an implementation of a binary solution
 * Created by Antonio J. Nebro on 03/09/14.
 */
public class DefaultBinarySolution extends AbstractGenericSolution<BinarySet, BinaryProblem> implements BinarySolution {

  /** Constructor */
  public DefaultBinarySolution(BinaryProblem problem) {
    super(problem) ;

    overallConstraintViolationDegree = 0.0 ;
    numberOfViolatedConstraints = 0 ;

    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      setVariableValue(i, createNewBitSet(problem.getNumberOfBits(i)));
    }

    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
      setObjective(i, 0.0);
    }
  }

  /** Copy constructor */
  public DefaultBinarySolution(DefaultBinarySolution solution) {
    super(solution.problem);

    for (int i = 0; i < problem.getNumberOfVariables(); i++) {
      setVariableValue(i, (BinarySet) solution.getVariableValue(i).clone());
    }

    for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
      setObjective(i, solution.getObjective(i)) ;
    }

    overallConstraintViolationDegree = solution.overallConstraintViolationDegree ;
    numberOfViolatedConstraints = solution.numberOfViolatedConstraints ;

    attributes = new HashMap<Object, Object>(solution.attributes) ;
  }

  private BinarySet createNewBitSet(int numberOfBits) {
    BinarySet bitSet = new BinarySet(numberOfBits) ;

    for (int i = 0; i < numberOfBits; i++) {
      double rnd = randomGenerator.nextDouble() ;
      if (rnd < 0.5) {
        bitSet.set(i);
      } else {
        bitSet.clear(i);
      }
    }
    return bitSet ;
  }

  @Override
  public int getNumberOfBits(int index) {
    return getVariableValue(index).getBinarySetLength() ;
  }

  @Override
  public DefaultBinarySolution copy() {
    return new DefaultBinarySolution(this);
  }

  @Override
  public int getTotalNumberOfBits() {
    int sum = 0 ;
    for (int i = 0; i < getNumberOfVariables(); i++) {
      sum += getVariableValue(i).getBinarySetLength() ;
    }

    return sum ;
  }

  @Override
  public String getVariableValueString(int index) {
    String result = "" ;
    for (int i = 0; i < getVariableValue(index).getBinarySetLength() ; i++) {
      if (getVariableValue(index).get(i)) {
        result += "1" ;
      }
      else {
        result+= "0" ;
      }
    }
    return result ;
  }
/*
  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof AbstractGenericSolution))
      return false;

    AbstractGenericSolution that = (AbstractGenericSolution) o;

    if (Double.compare(that.overallConstraintViolationDegree, overallConstraintViolationDegree)
        != 0)
      return false;
    if (attributes != null ? !attributes.equals(that.attributes) : that.attributes != null)
      return false;
    if (objectives != null ? !objectives.equals(that.objectives) : that.objectives != null)
      return false;
    if (problem != null ? !problem.equals(that.problem) : that.problem != null)
      return false;
    if (variables != null ? !variables.equals(that.variables) : that.variables != null)
      return false;

    return true;
  }

  @Override public int hashCode() {
    int result;
    long temp;
    result = objectives != null ? objectives.hashCode() : 0;
    result = 31 * result + (variables != null ? variables.hashCode() : 0);
    result = 31 * result + (problem != null ? problem.hashCode() : 0);
    temp = Double.doubleToLongBits(overallConstraintViolationDegree);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
    return result;
  }
  */
}
