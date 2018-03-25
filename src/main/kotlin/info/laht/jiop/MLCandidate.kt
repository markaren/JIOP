/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.laht.jiop

import java.util.*


/**
 *
 * @author Lars Ivar Hatledal
 */
open class MLCandidate(
        var candidate: DoubleArray,
        open var cost: Double = java.lang.Double.MAX_VALUE
) : Comparable<MLCandidate> {


    /**
     * Copy constructor
     *
     * @param candidate candidate to copy
     */
    protected constructor(
            candidate: MLCandidate
    ): this(candidate.candidate.clone(), candidate.cost)

    fun size(): Int {
        return candidate.size
    }

    operator fun get(i: Int): Double {
        return candidate[i]
    }

    operator fun set(i: Int, newVal: Double) {
        candidate[i] = newVal
    }

    fun copy(c: MLCandidate) {
        this.cost = c.cost
        candidate = c.candidate.clone()
    }

    fun copy(): MLCandidate {
        return MLCandidate(this)
    }

    override fun compareTo(o: MLCandidate): Int {
        if (this.cost == o.cost) {
            return 0
        } else if (this.cost < o.cost) {
            return -1
        } else {
            return 1
        }
    }

    override fun toString(): String {
        return "MLCandidate{" + "cost=" + cost + ", candidate=" + Arrays.toString(candidate) + '}'.toString()
    }

    companion object {

        fun randomCandidate(dof: Int): MLCandidate {
            return MLCandidate(MLUtil.randomArrayd(dof, 0.0, 1.0))
        }
    }

}
