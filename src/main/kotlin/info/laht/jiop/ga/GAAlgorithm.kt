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

import info.laht.jiop.*
import java.util.ArrayList
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.IterationDataImpl
import info.laht.jiop.tuning.Optimizable

/**
 *
 * @author Lars Ivar Hatledal
 */
class GAAlgorithm (

        private var popSize: Int = 60,
        private var elitism: Double = 0.1,
        private var selectionRate: Double = 0.5,
        private var mutationRate: Double = 0.1,

        private val selection: SelectionOperator = RoulettewheelSelection(),
        private val crossover: CrossoverOperator = DoubleArrayCrossover(),
        private val mutation: MutationOperator = DoubleArrayMutation()

) : MLAlgorithm("GA"), Optimizable {

    private lateinit var pop: MutableList<MLCandidate>

    override val numberOfFreeParameters: Int = 4

    override fun setFreeParameters(params: DoubleArray) {

        popSize = params[0].toInt()
        elitism = params[1]
        selectionRate = params[2]
        mutationRate = params[3]

    }

    override fun getParameterLimits(): List<MLRange> {
        return listOf(
                MLRange(10.0, 100.0), //popSize
                MLRange(0.1, 1.0), //elitism
                MLRange(0.1,0.8), //selectionRate
                MLRange(0.01,0.8) //mutationRate
        )
    }

    override fun solve(problem: Problem, terminationCriteria: TerminationCriteria): MLResult {

        var t: Long
        val t0 = System.currentTimeMillis()

        var numit = 0

        generatePopulation(problem.dimensionality)
        problem.evaluateAndUpdate(pop)
        pop.sort()

        var best: MLCandidate = pop[0].copy()

        val numElites = Math.round(elitism * popSize).toInt()
        val numToSelect = Math.round(popSize * selectionRate).toInt()
        val numMutations = Math.round((pop.size - numElites) * problem.dimensionality * this.mutationRate).toInt()

        do {

            val select = selection.select(pop, numToSelect, random)
            val offspring = crossover.mate(select, popSize - numToSelect, random)

            pop.subList(select.size, pop.size).clear()
            pop.addAll(offspring)

            mutation.mutate(pop.subList(numElites, pop.size), numMutations, random)

            problem.evaluateAndUpdate(pop)
            pop.sort()

            numit++
            t = System.currentTimeMillis() - t0

            val newBest = pop[0]
            if (best != newBest) {
                best = newBest.copy()
            }

        } while (!terminationCriteria.test(IterationDataImpl(best.cost, numit, t)))

        return getResult(best, problem, numit, t)
    }

    private fun generatePopulation(problemDimensionality: Int) {
        pop = ArrayList(popSize)
        while (pop.size != popSize) {
            pop.add(MLCandidate.randomCandidate(problemDimensionality))
        }
    }

    override fun toString(): String {
        return "GAAlgorithm{" + "popSize=" + popSize + ", elitism=" + elitism + ", selectionRate=" + selectionRate + ", mutationRate=" + mutationRate + '}'.toString()
    }

}
