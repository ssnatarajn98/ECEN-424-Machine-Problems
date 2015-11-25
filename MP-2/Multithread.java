package group23;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/*
 Created by Group 23.
 Due 10/1/15
 This program multiples two matrices using Multiple threading.
*/

public class Multithread {
    
//All variables below are outside and static so they can be seen by the threads
    //Initiate size of array:
    static  int n = 20;
    
    //Initiate Matrices:
    static int [][] Mat1 = new int[n][n]; //Matrix 1
    static int [][] Mat2 = new int[n][n]; //Matrix 2
    static int [][] Mat3 = new int[n][n]; //Result of the product of Matrix 1 and 2
    
    //Break up Matrix 1 into 5 components:
    static int [][] Mat1A = new int[n][n];
    static int [][] Mat1B = new int[n][n];
    static int [][] Mat1C = new int[n][n];
    static int [][] Mat1D = new int[n][n];
    static int [][] Mat1E = new int[n][n];

//Starting main:
    public static void main(String[] args) throws InterruptedException {
 
        /**A random generator is set up to create random #s in each matrix.
           The project doesn't specify what values to use, so it will be limited
             to the range 0 to (X-1) to keep the created matrices elements 1 digit long.
           The project asks that the matrices are created before the threads. 
              So, the matrices will created as a single thread and then broken up
              into 5 threads each. */
        Random Rand1 = new Random();
        
        //Runnables:
        Thread R1 = new Thread(new MyRunnable());
        Thread R2 = new Thread(new MyRunnable());
        Thread R3 = new Thread(new MyRunnable());
        Thread R4 = new Thread(new MyRunnable());
        Thread R5 = new Thread(new MyRunnable());
        
        //Create Matrix 1:
        System.out.println("Matrix 1 is:");
        System.out.println("_________________________________________");
        for (int i = 0;  i < n ; i++) {                 //Controls the # of rows
            for (int j = 0; j < n; j++) {               //Controls the # of columns
                Mat1[i][j] = Rand1.nextInt(10);   //Assigns NxN matrix with random values in the range 0 to (X-1) using Rand1.nextInt(X);
                System.out.print(Mat1[i][j] + " "); //Prints matrix to check for randomness of #s
            }
            System.out.print("|" + "Row: " + (i + 1)+ "\n"); //Formats the matrix to print nicely
        }
 
        //Create Matrix 2:
        System.out.println("");
        System.out.println("Matrix 2 is:");
        System.out.println("_________________________________________");
        for (int i = 0;  i < n ; i++){
            for (int j = 0; j < n; j++) {
                Mat2[i][j] = Rand1.nextInt(10);
                System.out.print(Mat2[i][j] + " ");
            }
            System.out.print("|" + "Row: "  + (i + 1) + "\n");
        }
        
//Break matrices into their parts:
        //1A:
        System.out.println("");
        System.out.println("Matrix 1A (Rows 1-4) is:");
        System.out.println("_________________________________________");
        for (int i = 0;  i < 4 ; i++){
            for (int j = 0; j < n; j++) {
                Mat1A[i][j] = Mat1[i][j];
                System.out.print(Mat1A[i][j] + " ");
            }
            System.out.print("|" + "Row: " + (i+1) + "\n");
        }
        
        //1B:
        System.out.println("");
        System.out.println("Matrix 1B (Rows 5-8) is:");
        System.out.println("_________________________________________");
        for (int i = 4;  i < 8 ; i++){
            for (int j = 0; j < n; j++) {
                Mat1B[i][j] = Mat1[i][j];
                System.out.print(Mat1B[i][j] + " ");
            }
            System.out.print("|" + "Row: "  + (i + 1) + "\n");
        }
        
        //1C:
        System.out.println("");
        System.out.println("Matrix 1C (Rows 9-12) is:");
        System.out.println("_________________________________________");
        for (int i = 8;  i < 12 ; i++){
            for (int j = 0; j < n; j++) {
                Mat1C[i][j] = Mat1[i][j];
                System.out.print(Mat1C[i][j] + " ");
            }
            System.out.print("|" + "Row: "  + (i + 1) + "\n");
        }
        
        //1D:
        System.out.println("");
        System.out.println("Matrix 1D (Rows 13-16) is:");
        System.out.println("_________________________________________");
        for (int i = 12;  i < 16 ; i++){
            for (int j = 0; j < n; j++) {
                Mat1D[i][j] = Mat1[i][j];
                System.out.print(Mat1D[i][j] + " ");
            }
            System.out.print("|" + "Row: "  + (i + 1) + "\n");
        }
        
        //1E:
        System.out.println("");
        System.out.println("Matrix 1E (Rows 17-20) is:");
        System.out.println("_________________________________________");
        for (int i = 16;  i < 20 ; i++){
            for (int j = 0; j < n; j++) {
                Mat1E[i][j] = Mat1[i][j];
                System.out.print(Mat1E[i][j] + " ");
            }
            System.out.print("|" + "Row: "  + (i + 1) + "\n");
        }
        
        //Create Matrix 3 (Product of Matrix 1 & 2):
        System.out.println("");
        System.out.println("Matrix 1 * Matrix 2 (Single-Threaded) Result:");
        System.out.println("_____________________________________________________________");
        for (int i = 0;  i < n ; i++){
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    Mat3[i][j] = Mat3[i][j] + (Mat1[i][k]*Mat2[k][j]);
                }
                System.out.print(Mat3[i][j] + " ");
            }
            System.out.print("|" + "Rows: " + (i+1) + "\n");
        }
        
        /**Here we are going to use runnables. We are starting the thread and then
               joining the values into the matrix.
           Basically the order is Start R1 -> Join values. Start R2 -> Join Values if it's R1.start() then R1.join(); etc */
        R1.start(); //Thread-0
        R2.start(); //Thread-1
        R3.start(); //Thread-2
        R4.start(); //Thread-3
        R5.start(); //Thread-4
        R1.join();
        R2.join();
        R3.join();
        R4.join();
        R5.join();
        MyRunnable Run = new MyRunnable();
        
        //Calling the Matrix from the runnable:
        int PHMat[][] = Run.PHMat;
        
        //Then we join them all together and then output. This set of for loops is simply outputting the results and it is not actually computing
        System.out.println("");
        System.out.println("Matrix 1 * Matrix 2 (Multi-threaded Matrix) Result: ");
        System.out.println("_____________________________________________________________");
        for (int i = 0;  i < n ; i++){
            for (int j = 0; j < n; j++) {
                System.out.print(PHMat[i][j] + " ");
            }
            System.out.print("|" + "Rows: " + (i+1) + "\n");
        }
        
    }
}
 
//Using Runnables:
    class MyRunnable implements Runnable {
        /**This is bringing over values from the previous class into this one
           Format would be: int X = Mthread.Whatyouwanttobringover;*/
        Multithread Mthread = new Multithread();
        public static int n = 20;
        public static int [][] PHMat = new int[n][n]; //This is just a placeholder matrix for the threads
        
        @Override
        public void run(){
            /*Keep any sort of int values you expect to change inside the run(). Anything you expect will not out
              Otherwise once you run the actual code it won't update. So for example if I have some int i = 3 and later change it to 300 in my code
              If I 'get' the int i outside of this run loop it'll only grab i = 3 and never change. But if I keep it inside it'll get whatever i is at that current point of running which is 300 */
            int Mat1A[][] = Mthread.Mat1A;
            int Mat1B[][] = Mthread.Mat1B;
            int Mat1C[][] = Mthread.Mat1C;
            int Mat1D[][] = Mthread.Mat1D;
            int Mat1E[][] = Mthread.Mat1E;
            
            int Mat2[][] = Mthread.Mat2;

            //The purpose of this Thread.currentThread().getName(); stuff is to check which thread is currently accessing the runnable
            String Checker = Thread.currentThread().getName();

            //Same concept as before. Now each thread does only a chunk of the overall calculation and the afterwards it's all joined together into one big matrix and output

            //Thread R1
           if (Checker.equals("Thread-0")) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < n; j++) {
                        for (int k = 0; k < n; k++) {
                            PHMat[i][j] = PHMat[i][j] + (Mat1A[i][k] * Mat2[k][j]); //Pumps the values into our Placeholder Matrix
                        }
                    }
                }
            }
            
            //Thread R2
            else if (Checker.equals("Thread-1")) {
                for (int i = 4; i < 8; i++) {
                    for (int j = 0; j < n; j++) {
                        for (int k = 0; k < n; k++) {
                            PHMat[i][j] = PHMat[i][j] + (Mat1B[i][k] * Mat2[k][j]);
                        }
                    }
                }
            }
            
            //Thread R3
            else if (Checker.equals("Thread-2")) {
                for (int i = 8; i < 12; i++) {
                    for (int j = 0; j < n; j++) {
                        for (int k = 0; k < n; k++) {
                            PHMat[i][j] = PHMat[i][j] + (Mat1C[i][k] * Mat2[k][j]);
                        }
                    }
                }
            }
            
            //Thread R4
            else if (Checker.equals("Thread-3")) {
                for (int i = 12; i < 16; i++) {
                    for (int j = 0; j < n; j++) {
                        for (int k = 0; k < n; k++) {
                            PHMat[i][j] = PHMat[i][j] + (Mat1D[i][k] * Mat2[k][j]);
                        }
                    }
                }
            }
            
            //Thread R5
            else if (Checker.equals("Thread-4")) {
                for (int i = 16; i < 20; i++) {
                    for (int j = 0; j < n; j++) {
                        for (int k = 0; k < n; k++) {
                            PHMat[i][j] = PHMat[i][j] + (Mat1E[i][k] * Mat2[k][j]);
                        }
                    }
                }
            }
            
            else{
                System.out.println("Whoops, something is borked!");
            }

    }
}
