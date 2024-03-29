package assignment2.test;

import assignment2.*;
import java.util.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link RecursiveAllocation} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class RecursiveAllocationTest {

	@Test
	public void basicTest() {

		int numWorkers = 2;
		int maxHours = 10;
		List<Integer> jobs = new ArrayList<>();
		jobs = new ArrayList<>();
		jobs.add(6);
		jobs.add(5);
		jobs.add(3);
		jobs.add(1);
		jobs.add(7);

		int expected = -17;
		int actual = RecursiveAllocation.maxRatingRecursive(numWorkers,
				maxHours, jobs);
		Assert.assertEquals(expected, actual);
	}

	@Test
    public void basicTest2() {

        int numWorkers = 2;
        int maxHours = 16;
        List<Integer> jobs = new ArrayList<>();
        jobs = new ArrayList<>();
        jobs.add(5);
        jobs.add(1);
        jobs.add(1);
        jobs.add(10);
        jobs.add(8);
        jobs.add(7);

        int expected = -29;
        int actual = RecursiveAllocation.maxRatingRecursive(numWorkers,
                maxHours, jobs);
        Assert.assertEquals(expected, actual);
    }
	
	   @Test
	    public void boundaryTest1() {

	        int numWorkers = 2;
	        int maxHours = 0;
	        List<Integer> jobs = new ArrayList<>();
	        jobs = new ArrayList<>();

	        int expected = 0;
	        int actual = RecursiveAllocation.maxRatingRecursive(numWorkers,
	                maxHours, jobs);
	        Assert.assertEquals(expected, actual);
	    }
	   
       @Test
       public void boundaryTest2() {

           int numWorkers = 2;
           int maxHours = 5;
           List<Integer> jobs = new ArrayList<>();
           jobs = new ArrayList<>();

           int expected = -50;
           int actual = RecursiveAllocation.maxRatingRecursive(numWorkers,
                   maxHours, jobs);
           Assert.assertEquals(expected, actual);
       }
       
       @Test
       public void boundaryTest3() {

           int numWorkers = 2;
           int maxHours = 0;
           List<Integer> jobs = new ArrayList<>();
           jobs = new ArrayList<>();
           jobs.add(5);
           jobs.add(1);
           jobs.add(1);
           jobs.add(10);
           jobs.add(8);
           jobs.add(7);

           int expected = 0;
           int actual = RecursiveAllocation.maxRatingRecursive(numWorkers,
                   maxHours, jobs);
           Assert.assertEquals(expected, actual);
       }
       
       @Test
       public void twoWayTestRandom() {

           int numWorkers = 2;
           int maxHours = 10;
           List<Integer> jobs = new ArrayList<>();
           jobs = new ArrayList<>();
           Random ra =new Random();
           for (int i =0;i<20;i++){
               jobs.add(ra.nextInt(10));
           }
           
           int recursive = RecursiveAllocation.maxRatingRecursive(numWorkers,
                   maxHours, jobs);
           int dynamic = DynamicAllocation.maxRatingDynamic(numWorkers,
                   maxHours, jobs);
           //System.out.println(dynamic);
           Assert.assertEquals(recursive, dynamic);
       }
       
       @Test
       public void moreWorkerTest() {

           int numWorkers = 5;
           int maxHours = 100;
           List<Integer> jobs = new ArrayList<>();
           jobs = new ArrayList<>();
           Random ra =new Random();
           for (int i =0;i<10;i++){
               jobs.add(ra.nextInt(10));
           }

           int expected = -17;
           int actual = RecursiveAllocation.maxRatingRecursive(numWorkers,
                   maxHours, jobs);
           //System.out.println(actual);
           //Assert.assertEquals(expected, actual);
       }
}
