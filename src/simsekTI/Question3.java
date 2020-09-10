package simsekTI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Question3 {

	public static void main(String[] args) {

		Random random = new Random();
		ArrayList<Integer> numbers500 = new ArrayList<>();
		
		for (int i=0; i<500; i++) {
			numbers500.add(random.nextInt());
		}
		
		nthSmallestNumber(numbers500, 197);
		
		
		/*Optional for seeing the fullly sorted list.
		System.out.println("After Sorting:");
		   for(int counter: numbers500){
				System.out.println(counter);
			} */

	}

	private static void nthSmallestNumber(ArrayList<Integer> numbers500, int i) {
		Collections.sort(numbers500);
		System.out.println("The " + i + ". smallest number is: " + numbers500.get(i-1));
		
	}

}
