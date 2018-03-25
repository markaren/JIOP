/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.laht.jiop

import java.io.Serializable

/**
 *
 * @author Lars Ivar Hatledal laht@ntnu.no.
 */
class MLRange(
        val lowerBound: Double,
        val upperBound: Double
) : Serializable {

    val mean: Double
        get() = (upperBound + lowerBound) / 2.0

    fun enforceRange(`val`: Double): Double {
        return MLUtil.clamp(`val`, lowerBound, upperBound)
    }

    fun randomInRange(): Double {
        return MLUtil.randomRange(lowerBound, upperBound)
    }

    fun normalize(x: Double): Double {
        return MLUtil.map(x, lowerBound, upperBound, 0.0, 1.0)
    }

    fun denormalize(x: Double): Double {
        return MLUtil.map(x, 0.0, 1.0, lowerBound, upperBound)
    }

    override fun toString(): String {
        return "MLRange{" + "lowerBound=" + lowerBound + ", upperBound=" + upperBound + '}'.toString()
    }

    companion object {

        val MAX_RANGE = MLRange(-java.lang.Double.MAX_VALUE, java.lang.Double.MAX_VALUE)
    }

}
