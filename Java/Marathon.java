/*
Author: Brice
*/

import java.util.Scanner;

class Marathon{
    //Method to parse given time to hours:minutes:seconds
    static int[] parseTime(String s){
        String times[] = s.split(":");
        //Return int array
        return new int[]{Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2])};
    }

    public static void main(String[] args) {
        //Variables to read marathon times
        String m1,m2;
        //Scanner to read user input
        Scanner obj = new Scanner(System.in);
        //Asking user for the time of the first marathon
        System.out.println("Please enter the first marathon time in hh:mm:ss format: ");
        m1 = obj.nextLine();
        //Convert into array of hours:minutes:seconds
        int t1[] = parseTime(m1);
        //Asking user for the time of the second marathon
        System.out.println("Please enter the second marathon time in hh:mm:ss format: ");
        m2 = obj.nextLine();
        //Convert into array of hours:minutes:seconds
        int t2[] = parseTime(m2);
        //Find total seconds
        int totalSecs = 3600*t1[0]+60*t1[1]+t1[2] + 3600*t2[0]+60*t2[1]+t2[2];
        //Find avg and round it
        int avgSecs = totalSecs/2 + totalSecs%2;
        //Find average hours
        int th = avgSecs/3600;
        avgSecs = avgSecs - th*3600;
        //Find average minutes
        int tm = avgSecs/60;
        avgSecs = avgSecs - tm*60;
        //Average seconds
        int ts = avgSecs;
        //Print average time
        System.out.println("The average of the two marathon times is: ");
        System.out.print((th<10?"0":"")+th+":"+(tm<10?"0":"")+tm+":"+(ts<10?"0":"")+ts);
        System.out.println();
    }
}