/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.laht.jiop

import java.util.Random


/**
 *
 * @author Lars Ivar Hatledal laht@ntnu.no.
 */
object MLUtil {

    fun clamp(x: Double, min: Double, max: Double): Double {
        return if (x < min) min else if (x > max) max else x
    }

    fun randomRange(min: Double, max: Double): Double {
        return Math.random() * Math.abs(max - min) + min
    }

    fun randomRange(min: Double, max: Double, rng: Random): Double {
        return rng.nextDouble() * Math.abs(max - min) + min
    }

    fun map(x: Double, in_min: Double, in_max: Double, out_min: Double, out_max: Double): Double {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }

    fun map(x: DoubleArray, in_min: DoubleArray, in_max: DoubleArray, out_min: DoubleArray, out_max: DoubleArray): DoubleArray {
        val mapped = DoubleArray(x.size)
        for (i in mapped.indices) {
            mapped[i] = map(x[i], in_min[i], in_max[i], out_min[i], out_max[i])
        }
        return mapped
    }

    fun map(x: DoubleArray, in_min: Double, in_max: Double, out_min: Double, out_max: Double): DoubleArray {
        val mapped = DoubleArray(x.size)
        for (i in mapped.indices) {
            mapped[i] = map(x[i], in_min, in_max, out_min, out_max)
        }
        return mapped
    }

    fun map(x: DoubleArray, in_min: DoubleArray, in_max: DoubleArray, out_min: Double, out_max: Double): DoubleArray {
        val mapped = DoubleArray(x.size)
        for (i in mapped.indices) {
            mapped[i] = map(x[i], in_min[i], in_max[i], out_min, out_max)
        }
        return mapped
    }

    fun map(x: DoubleArray, in_min: Double, in_max: Double, out_min: DoubleArray, out_max: DoubleArray): DoubleArray {
        val mapped = DoubleArray(x.size)
        for (i in mapped.indices) {
            mapped[i] = map(x[i], in_min, in_max, out_min[i], out_max[i])
        }
        return mapped
    }

    fun clamp(arr: DoubleArray, min: Double, max: Double): DoubleArray {
        val result = DoubleArray(arr.size)
        for (i in arr.indices) {
            var d = arr[i]
            if (d > max) {
                d = max
            } else if (d < min) {
                d = min
            }
            result[i] = d
        }
        return result
    }

    fun clamp(arr: DoubleArray, min: DoubleArray, max: DoubleArray): DoubleArray {
        val result = DoubleArray(arr.size)
        for (i in arr.indices) {
            var d = arr[i]
            if (d > max[i]) {
                d = max[i]
            } else if (d < min[i]) {
                d = min[i]
            }
            result[i] = d
        }
        return result
    }

    fun randomArrayd(len: Int, min: Double, max: Double): DoubleArray {
        val rng = Random()
        val result = DoubleArray(len)
        for (i in 0 until len) {
            result[i] = rng.nextDouble() * Math.abs(max - min) + min
        }
        return result
    }

    fun randomArrayd(min: DoubleArray, max: DoubleArray): DoubleArray {
        val rng = Random()
        val result = DoubleArray(min.size)
        for (i in result.indices) {
            result[i] = rng.nextDouble() * Math.abs(max[i] - min[i]) + min[i]
        }
        return result
    }

}
