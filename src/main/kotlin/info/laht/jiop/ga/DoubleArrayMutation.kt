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
package info.laht.jiop.ga

import java.util.Random
import info.laht.jiop.MLCandidate
import info.laht.jiop.clamp
import info.laht.jiop.randomRange

/**
 *
 * @author Lars Ivar Hatledal
 */
class DoubleArrayMutation(
        val factor: Double = 0.25
) : MutationOperator {

    override fun mutate(candidates: List<MLCandidate>, numMutation: Int, rng: Random) {

        for (i in 0 until numMutation) {
            val row = rng.nextInt(candidates.size)
            val candidate = candidates[row]
            val col = rng.nextInt(candidate.size())
            val currentValue = candidate[col]
            val newValue = randomRange(currentValue-factor, currentValue+factor).clamp(0.0, 1.0)
            candidate[col] = newValue
        }
    }
}
