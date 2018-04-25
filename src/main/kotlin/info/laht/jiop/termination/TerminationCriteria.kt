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
package info.laht.jiop.termination


/**
 *
 * @author Lars Ivar Hatledal
 */
@FunctionalInterface
interface TerminationCriteria {

    fun test(data: IterationData): Boolean

    companion object {

        fun of (predicate: (data: IterationData) -> Boolean): TerminationCriteria {
            return object : TerminationCriteria {
                override fun test(data: IterationData): Boolean {
                    return predicate.invoke(data)
                }
            }
        }

    }

}


/**
 *
 * @author Lars Ivar Hatledal
 */
class IterationTerminationCriteria(private val numIterations: Int) : TerminationCriteria {

    override fun test(data: IterationData): Boolean {
        return numIterations <= data.numIterations
    }

}

/**
 *
 * @author Lars Ivar Hatledal
 */
class CostTerminationCriteria(val targetCost: Double) : TerminationCriteria {

    override fun test(data: IterationData): Boolean {
        return targetCost <= data.bestCost
    }

}

/**
 *
 * @author Lars Ivar Hatledal
 */
class TimeElapsedTerminationCriteria(private val maxSolveTime: Long) : TerminationCriteria {

    override fun test(data: IterationData): Boolean {
        return maxSolveTime <= data.timeElapsed
    }

}
