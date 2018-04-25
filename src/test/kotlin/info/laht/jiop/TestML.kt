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
package info.laht.jiop

import info.laht.jiop.de.DEAlgorithm
import info.laht.jiop.ga.DoubleArrayMutation
import info.laht.jiop.ga.GAAlgorithm
import info.laht.jiop.ga.StochasticUniversalSampling
import info.laht.jiop.pso.MSOAlgorithm
import info.laht.jiop.pso.PSOAlgorithm
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.IterationData
import info.laht.jiop.testfunctions.RastriginFunction
import info.laht.jiop.tuning.AlgorithmOptimizer
import info.laht.jiop.tuning.Optimizable

/**
 *
 * @author Lars Ivar Hatledal
 */
object TestML {

    @JvmStatic
    fun main(args: Array<String>) {

        val func = RastriginFunction(10)

        val algorithms = listOf(
                PSOAlgorithm(
                        swarmSize = func.dimensionality*2,
                        omega = 0.8,
                        c1 = 1.41,
                        c2 = 1.41),
                MSOAlgorithm(
                        numSwarms = 3,
                        swarmSize = func.dimensionality*3,
                        omega = 0.9,
                        c1 = 1.41,
                        c2 = 1.41,
                        c3 = 0.41),
                DEAlgorithm(
                        popSize = func.dimensionality*3,
                        F = 0.9,
                        CR = 0.8),
                GAAlgorithm(
                        popSize = func.dimensionality*2,
                        elitism = 0.1,
                        selectionRate = 0.5,
                        mutationRate = 0.1,
                        mutation = DoubleArrayMutation(0.1),
                        selection = StochasticUniversalSampling())
        )

//        AlgorithmOptimizer(DEAlgorithm(), algorithms[0] as Optimizable).apply {
//            optimize(func, 10000)
//        }
//        AlgorithmOptimizer(DEAlgorithm(), algorithms[2] as Optimizable).apply {
//            optimize(func, 1000)
//        }
        AlgorithmOptimizer(DEAlgorithm(), algorithms[3] as Optimizable).apply {
            optimize(func, 10000)
        }

        for (a in algorithms) {
            val solve = a.solve(func, TerminationCriteria.of { it.bestCost == 0.0 || it.timeElapsed > 1000 })
            println("${a.name}: $solve")
        }

    }

}
