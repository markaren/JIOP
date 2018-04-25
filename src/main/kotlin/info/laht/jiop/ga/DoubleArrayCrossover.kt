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

import info.laht.jiop.MLCandidate
import java.util.*

/**
 *
 * @author Lars Ivar Hatledal
 */
class DoubleArrayCrossover : CrossoverOperator {

    override fun mate(parents: List<MLCandidate>, numOffspring: Int, rng: Random): List<MLCandidate> {

        Collections.shuffle(parents, rng)
        val offspring = ArrayList<MLCandidate>()

        var i = 0
        while (i < numOffspring) {

            if (i + 1 >= parents.size) {
                break
            }
            val ma = parents[i]
            val pa = parents[i + 1]
            val mateTwo = mateTwo(ma, pa, rng)

            offspring.add(mateTwo.first)
            offspring.add(mateTwo.second)
            i += 2
        }

        if (offspring.size < numOffspring) {

            do {
                val ma = parents[rng.nextInt(parents.size)]
                val pa = parents[rng.nextInt(parents.size)]
                val mateTwo = mateTwo(ma, pa, rng)

                offspring.add(mateTwo.first)
                offspring.add(mateTwo.second)

            } while (offspring.size < numOffspring)

        }

        while (offspring.size > numOffspring) {
            offspring.removeAt(offspring.size - 1)
        }

        return offspring

    }

    private fun mateTwo(ma: MLCandidate, pa: MLCandidate, rng: Random): Pair<MLCandidate, MLCandidate> {


        val offspring1 = MLCandidate(DoubleArray(ma.size()))
        val offspring2 = MLCandidate(DoubleArray(ma.size()))

        //blending method
        for (i in 0 until ma.size()) {

            val beta = rng.nextDouble()

            offspring1[i] = beta * ma[i] + (1 - beta) * pa[i]
            offspring2[i] = (1 - beta) * ma[i] + beta * pa[i]

        }

        return Pair(offspring1, offspring2)

    }

}
