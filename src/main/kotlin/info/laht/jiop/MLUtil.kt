/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@file:JvmName("MLUtils")
package info.laht.jiop

import java.util.Random

fun randomRange(min: Double, max: Double): Double {
    return Math.random() * Math.abs(max - min) + min
}

fun randomRange(min: Double, max: Double, rng: Random): Double {
    return rng.nextDouble() * Math.abs(max - min) + min
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


fun Double.clamp(min: Double, max: Double): Double {
    return if (this < min) min else if (this > max) max else this
}

fun Double.map(in_min: Double, in_max: Double, out_min: Double, out_max: Double): Double {
    return (this - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
}

fun DoubleArray.map(in_min: DoubleArray, in_max: DoubleArray, out_min: DoubleArray, out_max: DoubleArray): DoubleArray {
    val mapped = DoubleArray(size)
    for (i in mapped.indices) {
        mapped[i] = get(i).map(in_min[i], in_max[i], out_min[i], out_max[i])
    }
    return mapped
}

fun DoubleArray.map(in_min: Double, in_max: Double, out_min: Double, out_max: Double): DoubleArray {
    val mapped = DoubleArray(size)
    for (i in mapped.indices) {
        mapped[i] = get(i).map(in_min, in_max, out_min, out_max)
    }
    return mapped
}

fun DoubleArray.map(in_min: DoubleArray, in_max: DoubleArray, out_min: Double, out_max: Double): DoubleArray {
    val mapped = DoubleArray(size)
    for (i in mapped.indices) {
        mapped[i] = get(i).map(in_min[i], in_max[i], out_min, out_max)
    }
    return mapped
}

fun DoubleArray.map(in_min: Double, in_max: Double, out_min: DoubleArray, out_max: DoubleArray): DoubleArray {
    val mapped = DoubleArray(size)
    for (i in mapped.indices) {
        mapped[i] = get(i).map(in_min, in_max, out_min[i], out_max[i])
    }
    return mapped
}

fun DoubleArray.clamp(min: Double, max: Double): DoubleArray {
    val result = DoubleArray(size)
    for (i in indices) {
        var d = get(i)
        if (d > max) {
            d = max
        } else if (d < min) {
            d = min
        }
        result[i] = d
    }
    return result
}

fun DoubleArray.clamp(min: DoubleArray, max: DoubleArray): DoubleArray {
    val result = DoubleArray(size)
    for (i in indices) {
        var d = get(i)
        if (d > max[i]) {
            d = max[i]
        } else if (d < min[i]) {
            d = min[i]
        }
        result[i] = d
    }
    return result
}
