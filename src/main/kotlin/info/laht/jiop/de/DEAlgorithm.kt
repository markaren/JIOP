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
package info.laht.jiop.de

import info.laht.jiop.*
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.TerminationData

/**
 *
 * @author Lars Ivar Hatledal
 */
class DEAlgorithm(
        evaluator: MLEvaluator,
        val popSize: Int = 30,
        val F: Double = 0.9,
        val CR: Double = 0.8
) : MLAlgorithm("DE", evaluator) {

    private val pop: MutableList<MLCandidate> = ArrayList(popSize)

    class Builder(
            private val evaluator: MLEvaluator
    ) {
        
        private var popSize = 30
        private var F = 0.9
        private var CR = 0.8

        fun popSize(`val`: Int): Builder {
            this.popSize = `val`
            return this
        }

        fun F(`val`: Double): Builder {
            this.F = `val`
            return this
        }

        fun CR(`val`: Double): Builder {
            this.CR = `val`
            return this
        }

        fun build(): DEAlgorithm {
            return DEAlgorithm(evaluator, popSize, F, CR)
        }
    }

    override fun solve(terminationCriteria: TerminationCriteria, seed: List<DoubleArray>?): MLResult {

        var t: Long
        val t0 = System.currentTimeMillis()

        var numit = 0

        generatePopulation(seed)
        evaluateAndUpdate(pop)

        var best: MLCandidate?
        val sample = MLCandidate.randomCandidate(problemDimensionality)

        do {

            for (i in pop.indices) {

                val c = pop[i]
                var c1: MLCandidate
                var c2: MLCandidate
                var c3: MLCandidate

                do {
                    c1 = pop[random.nextInt(pop.size)]
                } while (c1 === c)
                do {
                    c2 = pop[random.nextInt(pop.size)]
                } while (c2 === c || c2 === c1)
                do {
                    c3 = pop[random.nextInt(pop.size)]
                } while (c3 === c || c3 === c1 || c3 === c2)

                val R = random.nextInt(problemDimensionality) + 1
                for (j in 0 until sample.size()) {
                    if (random.nextDouble() < CR || j == R) {
                        sample[j] = MLUtil.clamp(c1[j] + F * (c2[j] - c3[j]), 0.0, 1.0)
                    } else {
                        sample[j]= c[j]
                    }

                }
                val cost = evaluate(sample.candidate)
                sample.cost = cost
                if (cost < c.cost) {
                    c.copy(sample)
                }
            }
            numit++
            best = getBestFrom(pop)
            t = System.currentTimeMillis() - t0
        } while (!terminationCriteria.test(TerminationData(best!!.cost, numit, t)))

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
        return "DEAlgorithm{" + "popSize=" + popSize + ", F=" + F + ", CR=" + CR + '}'.toString()
    }

}
