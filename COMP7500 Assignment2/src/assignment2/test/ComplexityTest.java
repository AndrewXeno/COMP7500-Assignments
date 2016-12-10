package assignment2.test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import assignment2.*;
public class ComplexityTest {

    public static void main(String[] args) {
//        multiTestDynamic(100, 10000);
//        multiTestRecursive(16, 32);
//        int maxHours = 600;
//        int numJobs = 1000;
//        System.out.println(oneTestDynamic(2000, 100));
//        System.out.println(oneTestRecursive(15, 30));
//        multiTestRecursiveFixedH();
//        multiTestRecursiveFixedM();
        //multiTestDynamicFixedH(5000);
        multiTestDynamicFixedM(2000);
    }
    
    private static long oneTestDynamic(int maxHours, int numJobs){
        List<Integer> jobs = new ArrayList<>();
        jobs = new ArrayList<>();
        for (int i=0;i<numJobs;i++){
            jobs.add(1);
        }
        long start = System.currentTimeMillis();
        DynamicAllocation.maxRatingDynamic(2, maxHours, jobs);
        long runningTime = System.currentTimeMillis()-start;
        return runningTime;
    }
    
    private static long oneTestRecursive(int maxHours, int numJobs){
        List<Integer> jobs = new ArrayList<>();
        jobs = new ArrayList<>();
        for (int i=0;i<numJobs;i++){
            jobs.add(1);
        }
        long start = System.currentTimeMillis();
        RecursiveAllocation.maxRatingRecursive(2, maxHours, jobs);
        long runningTime = System.currentTimeMillis()-start;
        return runningTime;
    }

    private static void multiTestDynamic(int maxH, int maxM){
        oneTestDynamic(200, 200);
        oneTestDynamic(200, 200);
        for(int h=1000;h<maxH; h=h+100){
            for (int m=100;m<maxM;m=m+100){
                long time = oneTestDynamic(h, m);
                System.out.println(String.format("H=%d, M=%d, time=%d", h, m, time));   
            }
        }
    }
    
    private static void multiTestRecursive(int maxH, int maxM){
        long[][] result=new long[maxH+1][maxM+1];
        oneTestRecursive(20, 20);
        oneTestRecursive(20, 20);
        for(int h=0;h<=maxH; h=h+1){
            for (int m=0;m<=maxM;m=m+1){
                long time = oneTestRecursive(h, m);
                result[h][m]=time;
                System.out.println(String.format("H=%d, M=%d, time=%d", h, m, time));   
            }
        }
        
        for(int h=0;h<=maxH; h=h+1){
            System.out.println(Arrays.toString(result[h])); 
        }
    }
    
    private static void multiTestRecursiveFixedH(){
        for (int i=0;i<3;i++){
            long[]result=new long[39];
            for (int m=0;m<=38;m=m+1){
                result[m] = oneTestRecursive(17, m);
                System.out.println(String.format("M=%d, time=%d", m, result[m]));   
            }
            System.out.println(Arrays.toString(result)); 
        }
    }
    
    private static void multiTestRecursiveFixedM(){
        for (int i=0;i<1;i++){
            long[]result=new long[25];
            for (int h=21;h<=24;h=h+1){
                result[h] = oneTestRecursive(h, 32);
                System.out.println(String.format("H=%d, time=%d", h, result[h]));   
            }
            System.out.println(Arrays.toString(result)); 
        }
    } 
    
    private static void multiTestDynamicFixedH(int maxM){
        for (int j =0;j<1;j++){
            long[] result=new long[501];
            oneTestDynamic(200, 200);
            oneTestDynamic(200, 200);
            int i =0;
            for (int m=0;m<=maxM;m=m+10){
                
                long time = oneTestDynamic(200, m);
                result[i]=time;
                //System.out.println(String.format("H=%d, M=%d, time=%d", 200, m, time));  
                i++;
            }
            System.out.println(Arrays.toString(result)); 
        }
    }
    
    private static void multiTestDynamicFixedM(int maxH){
        for (int j =0;j<5;j++){
            long[] result=new long[105];
            oneTestDynamic(200, 200);
            oneTestDynamic(200, 200);
            int i =0;
            for (int h=0;h<=maxH;h=h+20){
                
                long time = oneTestDynamic(h, 100);
                result[i]=time;
                //System.out.println(String.format("H=%d, M=%d, time=%d", h, 400, time));  
                i++;
            }
            System.out.println(Arrays.toString(result)); 
        }
    }
}
