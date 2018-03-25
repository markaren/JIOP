/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.laht.jiop

/**
 *
 * @author Lars Ivar Hatledal
 */
class MLResult(
        candidate: MLCandidate,
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

    private val result: MLCandidate = candidate.copy()

    /**
     * Get the cost
     * @return the cost of the solution
     */
    val cost: Double
        get() = result.cost

    /**
     * Get the result
     * @return the result of the computation
     */
    fun getResult(): DoubleArray {
        return result.candidate
    }

    override fun toString(): String {
        return "MLResult{" + "numIt=" + numIterations + ", result=" + result + ", solveTime=" + solveTime + '}'.toString()
    }

}
