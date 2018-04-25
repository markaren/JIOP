package info.laht.jiop.ga

import info.laht.jiop.MLCandidate
import java.util.*

/**
 * https://github.com/dwdyer/watchmaker/blob/master/framework/src/java/main/org/uncommons/watchmaker/framework/selection/StochasticUniversalSampling.java
 */
class StochasticUniversalSampling: SelectionOperator {

    override fun select(population: List<MLCandidate>, selectionSize: Int, rng: Random): List<MLCandidate> {
        // Calculate the sum of all fitness values.
        var aggregateFitness = 0.0
        for (candidate in population) {
            aggregateFitness += SelectionOperator.getAdjustedFitness(candidate.cost)
        }

        val selection = ArrayList<MLCandidate>(selectionSize)
        // Pick a random offset between 0 and 1 as the starting point for selection.
        val startOffset = rng.nextDouble()
        var cumulativeExpectation = 0.0
        var index = 0
        for (candidate in population) {
            // Calculate the number of times this candidate is expected to
            // be selected on average and add it to the cumulative total
            // of expected frequencies.
            cumulativeExpectation += SelectionOperator.getAdjustedFitness(candidate.cost) / aggregateFitness * selectionSize

            // If f is the expected frequency, the candidate will be selected at
            // least as often as floor(f) and at most as often as ceil(f). The
            // actual count depends on the random starting offset.
            while (cumulativeExpectation > startOffset + index) {
                selection.add(candidate)
                index++
            }
        }
        return selection
    }
}