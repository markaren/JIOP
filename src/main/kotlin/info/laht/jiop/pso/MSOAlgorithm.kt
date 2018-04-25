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

import info.laht.jiop.MLAlgorithm
import info.laht.jiop.MLCandidate
import info.laht.jiop.Problem
import info.laht.jiop.MLResult
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.IterationDataImpl

/**
 *
 * @author Lars Ivar Hatledal
 */
class MSOAlgorithm (
        private val numSwarms: Int = 3,
        private val swarmSize: Int = 30,
        private val omega: Double = 0.5,
        private val c1: Double = 1.41,
        private val c2: Double = 1.41,
        private val c3: Double = 1.41
) : MLAlgorithm("MSO") {

    private lateinit var swarms: MutableList<MutableList<Particle>>

    private val best: Particle?
        get() {
            var gBest: Particle? = null
            for (swarm in this.swarms) {
                val best = getBestFrom(swarm)
                if (gBest == null) {
                    gBest = best as Particle
                } else if (best.cost < gBest.cost) {
                    gBest = best as Particle
                }
            }
            return gBest
        }

    override fun solve(problem: Problem, terminationCriteria: TerminationCriteria): MLResult {

        var t: Long
        val t0 = System.currentTimeMillis()

        var numit = 0

        generateSwarm(problem.dimensionality)
        for (swarm in swarms) {
            problem.evaluateAndUpdate(swarm)
        }

        var gBest = best

        do {
            for (swarm in swarms) {
                var sBest: Particle = getBestFrom(swarm) as Particle
                for (p in swarm) {
                    if (p !== gBest) {
                        p.update(omega, c1, c2, c3, sBest.candidate, gBest!!.candidate)
                        val cost = problem.evaluate(p.candidate)
                        p.cost = cost
                        if (cost < sBest.cost) {
                            sBest = p
                        }
                        if (cost < gBest.cost) {
                            gBest = p
                        }
                    }
                }
            }
            numit++
            t = System.currentTimeMillis() - t0
        } while (!terminationCriteria.test(IterationDataImpl(gBest!!.cost, numit, t)))

        return getResult(gBest, problem, numit, t)
    }

    private fun generateSwarm(problemDimensionality: Int) {
        swarms = MutableList(numSwarms, {ArrayList<Particle>(swarmSize)})
        for (swarm in swarms) {
            while (swarm.size != swarmSize) {
                swarm.add(Particle(MLCandidate.randomCandidate(problemDimensionality)))
            }
        }
    }

    override fun toString(): String {
        return "MultiSwarmAlgorithm{" + "numSwarms=" + numSwarms + ", swarmSize=" + swarmSize + ", omega=" + omega + ", c1=" + c1 + ", c2=" + c2 + ", c3=" + c3 + '}'.toString()
    }

}
