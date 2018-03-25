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

import java.util.ArrayList
import java.util.Arrays
import info.laht.jiop.de.DEAlgorithm
import info.laht.jiop.ga.GAAlgorithm
import info.laht.jiop.pso.MSOAlgorithm
import info.laht.jiop.pso.PSOAlgorithm
import info.laht.jiop.termination.TerminationCriteria
import info.laht.jiop.termination.TerminationData
import info.laht.jiop.testfunctions.AckleysFunction
import info.laht.jiop.testfunctions.RastriginFunction

/**
 *
 * @author Lars Ivar Hatledal
 */
object TestML {

    @JvmStatic
    fun main(args: Array<String>) {

        val func = RastriginFunction(10)

        val algorithms = listOf(
                PSOAlgorithm.Builder(func)
                        .swarmSize(40)
                        .omega(0.9)
                        .c1(2.0)
                        .c2(0.1).build(),
                DEAlgorithm.Builder(func)
                        .popSize(40)
                        .F(0.9)
                        .CR(0.8).build(),
                MSOAlgorithm.Builder(func)
                        .numSwarms(4)
                        .swarmSize(30)
                        .omega(0.9)
                        .c1(1.41)
                        .c2(1.41)
                        .c3(1.41).build(),
                GAAlgorithm.Builder(func)
                        .popSize(60)
                        .elitism(0.1)
                        .selectionRate(0.5)
                        .mutationRate(0.2).build())

        for (a in algorithms) {
            val solve = a.solve(TerminationCriteria.of { data: TerminationData -> data.bestCost == 0.0 || data.timeElapsed > 1000 })
            println("${a.name}: $solve")
        }


    }

}
