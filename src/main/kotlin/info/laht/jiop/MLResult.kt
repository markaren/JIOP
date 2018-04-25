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
class MLResult(

        val result: DoubleArray,

        val cost: Double,
        /**
        * Get the solve time
        * @return how long it took to get this result (in millis)
        */
       val solveTime: Long,
        /**
        * Get the number of iterations
        * @return the number of iterations the algorithm used to get this result
        */
       val numIterations: Int
) {


    override fun toString(): String {
        return "MLResult{" +"cost=$cost, numIt=" + numIterations + ", result=" + Arrays.toString(result) + ", solveTime=" + solveTime + '}'
    }

}
