package com.my.OpenTSDBDemo.serviceImpl;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		
		//Scanner sc = new Scanner(System.in);
		int sum;
		sum = 16;
		int n;
		n=5;
		int[] arr = new int[]{10,2,5,11,13};
		/*for(int i=0; i<n; i++){
			arr[i] = sc.nextInt();
		}*/
		
		boolean[] temp = new boolean[100];
		
		for(int i=0;i<n ;i++){
			
			int temp1 = sum - arr[i];
			
			if(temp[temp1] && temp1 >0){
				
				System.out.println("numbers are:"+arr[i]+"and"+temp1);
			}else{
				temp[arr[i]] = true;
			}
			
			
		}
		
	}
}
