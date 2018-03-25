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

import info.laht.jiop.MLCandidate

/**
 *
 * @author Lars Ivar Hatledal
 */
interface IParticle {

    val position: DoubleArray
    val localBestPosition: MLCandidate?
    val velocity: DoubleArray

    /**
     * Regular update
     *
     * @param omega
     * @param c1
     * @param c2
     * @param globalBestPosition
     */
    fun update(omega: Double, c1: Double, c2: Double, globalBestPosition: DoubleArray)

    /**
     * Multi swarm update
     *
     * @param omega
     * @param c1
     * @param c2
     * @param c3
     * @param swarmBestPosition
     * @param globalBestPosition
     */
    fun update(omega: Double, c1: Double, c2: Double, c3: Double, swarmBestPosition: DoubleArray, globalBestPosition: DoubleArray)

}
