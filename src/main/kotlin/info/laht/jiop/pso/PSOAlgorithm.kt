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
package info.laht.jiop.pso

import java.util.ArrayList
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
class PSOAlgorithm (
        evaluator: MLEvaluator,
        private var swarmSize: Int = 30,
        private var omega: Double = 0.9,
        private var c1: Double = 1.41,
        private var c2: Double = 1.41
) : MLAlgorithm("PSO", evaluator) {

    private val swarm: MutableList<Particle> = ArrayList(swarmSize)

    class Builder(
            private val evaluator: MLEvaluator
    ) {

        private var swarmSize = 30
        private var omega = 0.9
        private var c1 = 1.41
        private var c2 = 1.41

        fun swarmSize(`val`: Int): Builder {
            this.swarmSize = `val`
            return this
        }

        fun omega(`val`: Double): Builder {
            this.omega = `val`
            return this
        }

        fun c1(`val`: Double): Builder {
            this.c1 = `val`
            return this
        }

        fun c2(`val`: Double): Builder {
            this.c2 = `val`
            return this
        }

        fun build(): PSOAlgorithm {
            return PSOAlgorithm(evaluator, swarmSize, omega, c1, c2)
        }

    }

    override fun solve(terminationCriteria: TerminationCriteria, seed: List<DoubleArray>?): MLResult {

        var t: Long
        val t0 = System.currentTimeMillis()

        var numit = 0

        generateSwarm(seed)
        evaluateAndUpdate(swarm)

        var gBest: Particle = getBestFrom(swarm) as Particle

        do {

            for (p in swarm) {
                if (p !== gBest) {
                    p.update(omega, c1, c2, gBest.candidate)
                    val cost = evaluate(p.candidate)
                    p.cost = cost
                    if (cost < gBest.cost) {
                        gBest = p
                    }
                }
            }
            numit++
            t = System.currentTimeMillis() - t0
        } while (!terminationCriteria.test(TerminationData(gBest.cost, numit, t)))

        return MLResult(denormalizeSolution(gBest), t, numit)
    }

    private fun generateSwarm(seed: List<DoubleArray>?) {
        swarm.clear()
        if (seed != null) {
            for (s in seed) {
                swarm.add(Particle(MLCandidate(s)))
            }
        }

        while (swarm.size != swarmSize) {
            swarm.add(Particle(MLCandidate.randomCandidate(problemDimensionality)))
        }
    }

    override fun toString(): String {
        return "PSOAlgorithm{" + "swarmSize=" + swarmSize + ", omega=" + omega + ", c1=" + c1 + ", c2=" + c2 + '}'.toString()
    }

}
