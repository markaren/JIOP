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

import java.io.Serializable

/**
 *
 * @author Lars Ivar Hatledal
 */
abstract class MLEvaluator(
        val problemDimensionality: Int,
        private val ranges: List<MLRange>
) : Serializable {

    constructor(problemDimensionality: Int, range: MLRange): this(problemDimensionality, List(problemDimensionality, {range}))

    fun normalize(candidate: DoubleArray): DoubleArray {

        val result = DoubleArray(problemDimensionality)
        for (i in 0 until problemDimensionality) {
            result[i] = ranges[i].normalize(candidate[i])
        }
        return result

    }

    fun denormalize(candidate: DoubleArray): DoubleArray {

        val result = DoubleArray(problemDimensionality)
        for (i in 0 until problemDimensionality) {
            result[i] = ranges[i].denormalize(candidate[i])
        }
        return result

    }

    fun denormalizeSolution(candidate: MLCandidate): MLCandidate {
        return MLCandidate(denormalize(candidate.candidate), candidate.cost)
    }

    fun evaluateAndUpdate(candidates: List<MLCandidate>) {
        candidates.parallelStream().forEach { c: MLCandidate -> c.cost = evaluate(c.candidate) }
    }

    abstract fun evaluate(candidate: DoubleArray): Double
}
