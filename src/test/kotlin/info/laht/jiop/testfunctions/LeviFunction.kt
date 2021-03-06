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
 *
 * @author Lars Ivar Hatledal
 */
class LeviFunction : Problem(2, MLRange(-10.0, 10.0)) {

    override fun evaluate(candidate: DoubleArray): Double {
        val denormalize = denormalize(candidate)
        val x = denormalize[0]
        val y = denormalize[1]

        return Math.pow(Math.sin(3.0 * Math.PI * x), 2.0) + Math.pow(x - 1, 2.0) * (1 + Math.pow(Math.sin(3.0 * Math.PI * y), 2.0)) + Math.pow(y - 1, 2.0) * (1 + Math.pow(Math.sin(2.0 * Math.PI * y), 2.0))
    }

}
