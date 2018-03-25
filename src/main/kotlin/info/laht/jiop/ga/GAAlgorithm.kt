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
import java.util.Collections
import info.laht.jiop.MLAlgorithm
import info.laht.jiop.MLCandidate
import info.laht.jiop.MLEvaluator
import info.laht.jiop.MLResult
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.TerminationData

/**
 *
 * @author Lars Ivar Hatledal
 */
class GAAlgorithm (

        evaluator: MLEvaluator,
        val popSize: Int = 60,
        val elitism: Double = 0.1,
        val selectionRate: Double = 0.5,
        val mutationRate: Double = 0.1,

        private var selection: SelectionOperator = RoulettewheelSelection(),
        private var crossover: CrossoverOperator = DoubleArrayCrossover(),
        private var mutation: MutationOperator = DoubleArrayMutation()

) : MLAlgorithm("GA", evaluator) {

    private val pop: MutableList<MLCandidate> = ArrayList(popSize)


    class Builder(
            private val evaluator: MLEvaluator
    ) {

        private var popSize = 60
        private var elitism = 0.1
        private var selectionRate = 0.5
        private var mutationRate = 0.1


        fun popSize(`val`: Int): Builder {
            this.popSize = `val`
            return this
        }

        fun elitism(`val`: Double): Builder {
            this.elitism = `val`
            return this
        }

        fun selectionRate(`val`: Double): Builder {
            this.selectionRate = `val`
            return this
        }

        fun mutationRate(`val`: Double): Builder {
            this.mutationRate = `val`
            return this
        }

        fun build(): GAAlgorithm {
            return GAAlgorithm(evaluator, popSize, elitism, selectionRate, mutationRate)
        }
    }

    override fun solve(terminationCriteria: TerminationCriteria, seed: List<DoubleArray>?): MLResult {

        var t: Long
        val t0 = System.currentTimeMillis()

        var numit = 0

        generatePopulation(seed)
        evaluateAndUpdate(pop)
        Collections.sort(pop)

        var best: MLCandidate

        val numElites = Math.round(elitism * popSize).toInt()
        val numToSelect = Math.round(popSize * selectionRate).toInt()
        val numMutations = Math.round((pop.size - numElites).toDouble() * problemDimensionality.toDouble() * this.mutationRate).toInt()

        do {

            val select = selection.select(pop, numToSelect, random)
            val offspring = crossover.mate(select, popSize - numToSelect, random)

            pop.subList(select.size, pop.size).clear()
            pop.addAll(offspring)

            mutation.mutate(pop.subList(numElites, pop.size), numMutations, random)

            evaluateAndUpdate(pop)
            Collections.sort(pop)

            numit++
            t = System.currentTimeMillis() - t0
            best = pop[0]

        } while (!terminationCriteria.test(TerminationData(best.cost, numit, t)))

        return MLResult(denormalizeSolution(best), t, numit)
    }

    private fun generatePopulation(seed: List<DoubleArray>?) {
        pop.clear()
        if (seed != null) {
            for (s in seed) {
                pop.add(MLCandidate(s))
            }
        }
        while (pop.size != popSize) {
            pop.add(MLCandidate.randomCandidate(problemDimensionality))
        }
    }

    override fun toString(): String {
        return "GAAlgorithm{" + "popSize=" + popSize + ", elitism=" + elitism + ", selectionRate=" + selectionRate + ", mutationRate=" + mutationRate + '}'.toString()
    }

}
