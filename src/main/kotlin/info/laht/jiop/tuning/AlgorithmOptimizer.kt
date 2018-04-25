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


package info.laht.jiop.tuning


import info.laht.jiop.Problem
import info.laht.jiop.ProblemSolver
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.TimeElapsedTerminationCriteria
import javafx.scene.web.PromptData
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author Lars Ivar Hatledal
 */
class AlgorithmOptimizer(
        private val solver: ProblemSolver,
        private val optimizable: Optimizable
) {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(AlgorithmOptimizer::class.java)
    }


    fun optimize(problem: Problem, timeOut: Long) {

        val eval = OptimizerEvaluator(problem).apply {
            parallel = false
        }

        LOG.info("Optimizing ${optimizable.name} using ${solver.name}")
        val solve = solver.solve(eval, TimeElapsedTerminationCriteria(timeOut))
        optimizable.setFreeParameters(solve.result)
        LOG.info("Done! Optimized variables: ${solve.result.toList()}")

    }

    inner class OptimizerEvaluator(
            private val problemToSolve: Problem
    ): Problem(optimizable.numberOfFreeParameters, optimizable.getParameterLimits()) {

        override fun evaluate(candidate: DoubleArray): Double {

            val denormalize = denormalize(candidate)
            optimizable.setFreeParameters(denormalize)

            val numRuns = 5
            var cost = 0.0

            for (i in 0 until numRuns) {
                val solve = optimizable.solve(problemToSolve, TerminationCriteria.of { it.bestCost == 0.0 || it.timeElapsed > 50 })
                cost += solve.cost
            }

            return cost/numRuns

        }

    }

}
