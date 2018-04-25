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
package info.laht.jiop.testfunctions

import info.laht.jiop.Problem
import info.laht.jiop.MLRange

/**
 * In mathematical optimization, the Rastrigin function is a non-convex function used
 * as a performance test problem for optimization algorithms.
 * It is a typical example of non-linear multimodal function.
 * It was first proposed by Rastrigin as a 2-dimensional function and has been generalized by Mühlenbein et al.
 * Finding the minimum of this function is a fairly difficult problem due to its large search space and its large number of local minima.
 *
 * @author Lars Ivar Hatledal
 */
class RastriginFunction(
        dimension: Int
) : Problem(dimension, MLRange(-5.12, 5.12)) {

    override fun evaluate(candidate: DoubleArray): Double {

        val denormalize = denormalize(candidate)

        val A = 10.0
        var cost = A * dimensionality
        for (i in 0 until dimensionality) {
            val x = denormalize[i]
            cost += x * x - A * Math.cos(2.0 * Math.PI * x)
        }

        return cost

    }

}
