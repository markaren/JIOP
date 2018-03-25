package info.laht.jiop.tuning

///* 
// * The MIT License
// *
// * Copyright 2015 Lars Ivar Hatledal.
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//package info.laht.jiop.tuning;
//
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import info.laht.jiop.MLResult;
//import info.laht.jiop.MLAlgorithm;
//import info.laht.jiop.MLEvaluator;
//import info.laht.jiop.termination.TimeElapsedTerminationCriteria;
//
///**
// *
// * @author Lars Ivar Hatledal
// */
//public class AlgorithmOptimizer {
//
//    private static final Logger LOG = Logger.getLogger(AlgorithmOptimizer.class.getName());
//
//    private final MLAlgorithm algorithm;
//    private final Optimizable optimizable;
//
//    private final static int NUM_RUNS = 5;
//
//    public AlgorithmOptimizer(MLAlgorithm algorithm, Optimizable optimizable) {
//        this.algorithm = algorithm;
//        this.optimizable = optimizable;
//        this.algorithm.setEval(new OptimizerEvaluator());
//
//    }
//
//    public void optimize(long timeOut) {
//
//        LOG.log(Level.INFO, "Optimizing {0} using {1}", new Object[]{optimizable, algorithm});
//        MLResult solve = algorithm.solve(new TimeElapsedTerminationCriteria(timeOut));
//        optimizable.setFreeParameters(solve.getResult());
//        LOG.log(Level.INFO, "Done! Optimized variables: {0}", solve.getResult());
//
//    }
//
//    private class OptimizerEvaluator extends MLEvaluator {
//
//        public OptimizerEvaluator() {
//            super(optimizable.getParameterLimits());
//        }
//
//        @Override
//        public double evaluate(double[] candidate) {
//            double[] denormalize = denormalize(candidate);
//            optimizable.setFreeParameters(denormalize);
//
//            double cost = 0;
//            for (int i = 0; i < NUM_RUNS; i++) {
//                MLResult solve = optimizable.solve(new TimeElapsedTerminationCriteria(50));
//                cost += solve.getCost();
//            }
//            return cost / NUM_RUNS;
//
//        }
//    }
//}
