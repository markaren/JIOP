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

import info.laht.jiop.*
import java.util.ArrayList
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.IterationDataImpl
import info.laht.jiop.tuning.Optimizable
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author Lars Ivar Hatledal
 */
class PSOAlgorithm (
        private var swarmSize: Int = 30,
        private var omega: Double = 0.9,
        private var c1: Double = 1.41,
        private var c2: Double = 1.41
) : MLAlgorithm("PSO"), Optimizable {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(PSOAlgorithm::class.java)
    }

    private lateinit var swarm: MutableList<Particle>

    override val numberOfFreeParameters: Int = 4

    override fun setFreeParameters(params: DoubleArray) {

        swarmSize = params[0].toInt()
        omega = params[1]
        c1 = params[2]
        c2 = params[3]

    }

    override fun getParameterLimits(): List<MLRange> {
        return listOf(
                MLRange(10.0, 100.0), //swarmSize
                MLRange(0.1, 0.5), //omega
                MLRange(0.1,2.0), //c1
                MLRange(0.1,2.0) //c2
        )
    }

    override fun solve(problem: Problem, terminationCriteria: TerminationCriteria): MLResult {

        var t: Long
        val t0 = System.currentTimeMillis()

        var numit = 0

        generateSwarm(problem.dimensionality)
        problem.evaluateAndUpdate(swarm)

        var gBest: Particle = getBestFrom(swarm) as Particle

        do {

            for (p in swarm) {
                if (p !== gBest) {
                    p.update(omega, c1, c2, gBest.candidate)
                    val cost = problem.evaluate(p.candidate)
                    p.cost = cost
                    if (cost < gBest.cost) {
                        gBest = p
                    }
                }
            }
            numit++
            t = System.currentTimeMillis() - t0
        } while (!terminationCriteria.test(IterationDataImpl(gBest.cost, numit, t)))

        return getResult(gBest, problem, numit, t)
    }

    private fun generateSwarm(problemDimensionality: Int) {
        swarm = ArrayList(swarmSize)
        while (swarm.size != swarmSize) {
            swarm.add(Particle(MLCandidate.randomCandidate(problemDimensionality)))
        }
    }

    override fun toString(): String {
        return "PSOAlgorithm{" + "swarmSize=" + swarmSize + ", omega=" + omega + ", c1=" + c1 + ", c2=" + c2 + '}'.toString()
    }

}
