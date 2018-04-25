/* 
 * The MIT License
 *
 * Copyright 2015 Lars Ivar Hatledal.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.laht.jiop.ga


import java.util.ArrayList
import java.util.Arrays
import java.util.Random
import info.laht.jiop.MLCandidate

/**
 *
 * https://github.com/dwdyer/watchmaker/blob/master/framework/src/java/main/org/uncommons/watchmaker/framework/selection/RouletteWheelSelection.java
 */
class RoulettewheelSelection : SelectionOperator {

    override fun select(population: List<MLCandidate>, selectionSize: Int, rng: Random): List<MLCandidate> {
        // Record the cumulative fitness scores.  It doesn't matter whether the
        // population is sorted or not.  We will use these cumulative scores to work out
        // an index into the population.  The cumulative array itself is implicitly
        // sorted since each element must be greater than the previous one.  The
        // numerical difference between an element and the previous one is directly
        // proportional to the probability of the corresponding candidate in the population
        // being selected.
        val cumulativeFitnesses = DoubleArray(population.size)
        cumulativeFitnesses[0] = getAdjustedFitness(population[0].cost)
        for (i in 1 until population.size) {
            val fitness = getAdjustedFitness(population[i].cost)
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness
        }

        val selection = ArrayList<MLCandidate>(selectionSize)
        for (i in 0 until selectionSize) {
            val randomFitness = rng.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.size - 1]
            var index = Arrays.binarySearch(cumulativeFitnesses, randomFitness)
            if (index < 0) {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1)
            }
            selection.add(population[index])
        }
        return selection
    }

    private fun getAdjustedFitness(rawFitness: Double): Double {
        // If standardised fitness is zero we have found the best possible
        // solution.  The evolutionary algorithm should not be continuing
        // after finding it.
        return if (rawFitness == 0.0) java.lang.Double.POSITIVE_INFINITY else 1 / rawFitness

    }

}
