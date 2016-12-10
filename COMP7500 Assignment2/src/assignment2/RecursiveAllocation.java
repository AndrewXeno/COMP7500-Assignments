package assignment2;

import java.util.*;

public class RecursiveAllocation {

    /**
     * @param numWorkers
     *            The number of workers that are available to work. You may
     *            assume that numWorkers >= 0.
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
     *         This method must be implemented using a recursive programming
     *         solution to the problem. It is expected to have a worst-case
     *         exponential running time.
     */
    public static int maxRatingRecursive(int numWorkers, int maxHours,
            List<Integer> jobs) {
        int[] allocatedHours = new int[numWorkers];
        return maxRatingRecursive(numWorkers, maxHours, jobs, 0,
                allocatedHours);
    }

    private static int maxRatingRecursive(int numWorkers, int maxHours,
            List<Integer> jobs, int currentJob, int[] allocatedHours) {
        if (currentJob >= jobs.size()) {
            // if no job need to be allocated, directly return the rating
            return calculateRating(maxHours, allocatedHours);
        }
        if (currentJob % 3 == 0) {
            // the intern's turn
            int minRating = Integer.MAX_VALUE;
            for (int i = 0; i < numWorkers; i++) {
                if (jobs.get(currentJob) <= maxHours - allocatedHours[i]) {
                    int[] newAllocatedHours = allocatedHours.clone();
                    newAllocatedHours[i] += jobs.get(currentJob);
                    int newRating = maxRatingRecursive(numWorkers, maxHours,
                            jobs, currentJob + 1, newAllocatedHours);
                    if (newRating < minRating) {
                        minRating = newRating;
                    }
                }
            }
            if (minRating == Integer.MAX_VALUE) {
                // if the minimum value has not changed during the loop,
                // it means no worker can be assigned to any new job.
                // Thus, directly return the current rating.
                return calculateRating(maxHours, allocatedHours);
            } else {
                return minRating;
            }
        } else {
            // the manager's turn
            int maxRating = Integer.MIN_VALUE;
            for (int i = 0; i < numWorkers; i++) {
                if (jobs.get(currentJob) <= maxHours - allocatedHours[i]) {
                    int[] newAllocatedHours = allocatedHours.clone();
                    newAllocatedHours[i] += jobs.get(currentJob);
                    int newRating = maxRatingRecursive(numWorkers, maxHours,
                            jobs, currentJob + 1, newAllocatedHours);
                    if (newRating > maxRating) {
                        maxRating = newRating;
                    }
                }
            }
            if (maxRating == Integer.MIN_VALUE) {
                // if the maximum value has not changed during the loop,
                // it means no worker can be assigned to any new job.
                // Thus, directly return the current rating.
                return calculateRating(maxHours, allocatedHours);
            } else {
                return maxRating;
            }
        }
    }

    /**
     * Given the allocated hours for each worker, calculates the rating.
     * 
     * @param maxHours
     *            The maximum number of whole hours that each worker can work
     *            for the week.
     * @param allocatedHours
     *            The array identifying the allocated hours for each worker
     * @return The rating calculated basing on the allocated hours
     *         for each worker
     */
    private static int calculateRating(int maxHours, int[] allocatedHours) {
        int rating = 0;
        for (int i : allocatedHours) {
            rating -= (maxHours - i) * (maxHours - i);
        }
        return rating;
    }
}
