package assignment2;

import java.util.*;

public class DynamicAllocation {

    /**
     * @param numWorkers
     *            The number of workers that are available to work.
     * 
     *            For this dynamic programming solution you should assume that
     *            numWorkers=2.
     * @param maxHours
     *            The maximum number of whole hours that each worker can work
     *            for the week. You may assume that maxHours >= 0.
     * @param jobs
     *            A list describing the list of jobs to allocate, in order of
     *            priority. Each job is described by the number of whole hours
     *            that it will take to complete. That is, there are jobs.size()
     *            jobs on the list, and the ith highest priority job will take
     *            jobs.get(i) whole hours to complete. The number of hours that
     *            any job will take to complete is greater than 0.
     * 
     * @return Given the parameters of the allocation problem, this method
     *         returns the maximum performance-metric rating achievable by the
     *         manager, given that the intern seeks to make job allocations that
     *         minimise the rating. (See handout for details.)
     * 
     *         This method must be implemented using an efficient bottom-up
     *         dynamic programming solution to the problem under the assumption
     *         that numWorkers == 2.
     */
    public static int maxRatingDynamic(int numWorkers, int maxHours,
            List<Integer> jobs) {
        int[][][] ratings = new int[jobs.size() + 1][maxHours + 1][maxHours
                + 1];
        for (int i = jobs.size(); i >= 0; i--) {
            for (int j = 0; j <= maxHours; j++) {
                for (int k = 0; k <= maxHours; k++) {
                    int availableHourA = maxHours - j;
                    int availableHourB = maxHours - k;
                    if (i >= jobs.size()) {
                        ratings[i][j][k] = -(availableHourA * availableHourA
                                + availableHourB * availableHourB);
                        continue;
                    }
                    if (jobs.get(i) > availableHourA
                            && jobs.get(i) > availableHourB) {
                        ratings[i][j][k] = -(availableHourA * availableHourA
                                + availableHourB * availableHourB);
                        continue;
                    }
                    if (jobs.get(i) > availableHourA) {
                        ratings[i][j][k] = ratings[i + 1][j][k + jobs.get(i)];
                        continue;
                    }
                    if (jobs.get(i) > availableHourB) {
                        ratings[i][j][k] = ratings[i + 1][j + jobs.get(i)][k];
                        continue;
                    }
                    if (i % 3 == 0) {
                        ratings[i][j][k] = Math.min(
                                ratings[i + 1][j][k + jobs.get(i)],
                                ratings[i + 1][j + jobs.get(i)][k]);
                        continue;
                    }
                    ratings[i][j][k] = Math.max(
                            ratings[i + 1][j][k + jobs.get(i)],
                            ratings[i + 1][j + jobs.get(i)][k]);
                }
            }
        }
        return ratings[0][0][0];
    }
}
