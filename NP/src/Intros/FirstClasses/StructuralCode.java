package Intros.FirstClasses;

public class StructuralCode {
    /*
    count all the numbers between a and b which are
    divisible with the sum of their digits
     */

    // refactoring - refactor -> extract method
    public static int function(int a, int b){
        int counter=0;
        for (int i=a;i<b;i++){
            if(i%getSum(i)==0){
                counter++;
            }
        }
        return counter;
    }

    private static int getSum(int curr) {
        int sum=0;
        while(curr !=0){
            sum+= curr %10;
            curr /=10;
        }
        return sum;
    }

    public static void main(String[] args) {

    }
}
