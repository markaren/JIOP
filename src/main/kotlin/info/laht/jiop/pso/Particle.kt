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

import java.util.*
import info.laht.jiop.MLCandidate
import info.laht.jiop.MLUtil

/**
 *
 * @author Lars Ivar Hatledal
 */
class Particle(
        candidate: MLCandidate
) : MLCandidate(candidate), IParticle {

    override var localBestPosition: MLCandidate? = null
        private set
    override val velocity: DoubleArray

    private val rng = Random()
    private val lowerVel = -0.5
    private val upperVel = 0.5

    override val position: DoubleArray
        get() = candidate

    override var cost: Double
        get() = super.cost
        set(cost) {
            super.cost = cost
            if (cost < localBestPosition!!.cost) {
                localBestPosition = copy()
            }
        }

    init {
        this.localBestPosition = candidate.copy()
        this.velocity = MLUtil.randomArrayd(candidate.size(), -1.0, 1.0)
    }

    override fun update(omega: Double, c1: Double, c2: Double, globalBestPosition: DoubleArray) {
        for (i in velocity.indices) {
            val r1 = rng.nextDouble()
            val r2 = rng.nextDouble()

            // update velocity
            velocity[i] = MLUtil.clamp(
                    velocity[i] * omega
                            + c1 * r1 * (localBestPosition!!.get(i) - get(i))
                            + c2 * r2 * (globalBestPosition[i] - get(i)), lowerVel, upperVel)

            set(i, MLUtil.clamp(get(i) + velocity[i], 0.0, 1.0))
        }
    }

    override fun update(omega: Double, c1: Double, c2: Double, c3: Double, swarmBestPosition: DoubleArray, globalBestPosition: DoubleArray) {
        for (i in velocity.indices) {
            val r1 = rng.nextDouble()
            val r2 = rng.nextDouble()
            val r3 = rng.nextDouble()

            // update velocity
            velocity[i] = MLUtil.clamp(
                    velocity[i] * omega
                            + c1 * r1 * (localBestPosition!!.get(i) - get(i))
                            + c2 * r2 * (swarmBestPosition[i] - get(i))
                            + c3 * r3 * (globalBestPosition[i] - get(i)), lowerVel, upperVel)

            set(i, MLUtil.clamp(get(i) + velocity[i], 0.0, 1.0))
        }
    }
}
