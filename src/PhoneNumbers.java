import java.util.PriorityQueue;
import java.util.Scanner;


public class PhoneNumbers {

	int[] array;
	
	public PhoneNumbers(int[] number){
		array = new int[number.length];
		
		for(int i = 0; i < number.length; i++)
			array[i] = number[i];		
	}
	
	public void findAllPermutations(){
		System.out.println(findAllThePermutationsRecursive(0, new StringBuilder()));	
	}
	
	private int findAllThePermutationsRecursive(int index, StringBuilder word){
		if(array.length == index){
			System.out.println(word.toString());
			return 1;
		}
		
		int result = 0;
		StringBuilder temp;
		
		if(array[index] == 1 || array[index] == 0){
			temp = new StringBuilder();
			temp.append(word.toString());
			temp.append(getCharacter(array[index], 0));
			result += findAllThePermutationsRecursive(index+1, temp);
		}else{
			for(int j = 0; j<3; j++){
				temp = new StringBuilder();
				temp.append(word.toString());
				temp.append(getCharacter(array[index], j));
				result += findAllThePermutationsRecursive(index+1, temp);
			}
		}
	
		return result;
	}
	
	private char getCharacter(int a, int b){

		switch(a){
		case 0:	
			return '0';
		case 1:
			return '1';
		case 2:
			switch(b){
			case 0:
				return 'A';
			case 1:
				return 'B';
			case 2:
				return 'C';
			}
			break;
		case 3:
			switch(b){
			case 0:
				return 'D';
			case 1:
				return 'E';
			case 2:
				return 'F';
			}
			break;
		case 4:
			switch(b){
			case 0:
				return 'G';
			case 1:
				return 'H';
			case 2:
				return 'I';
			}
			break;
		case 5:
			switch(b){
			case 0:
				return 'J';
			case 1:
				return 'K';
			case 2:
				return 'L';
			}
			break;
		case 6:
			switch(b){
			case 0:
				return 'M';
			case 1:
				return 'N';
			case 2:
				return 'O';
			}
			break;
		case 7:
			switch(b){
			case 0:
				return 'P';
			case 1:
				return 'R';
			case 2:
				return 'S';
			}
			break;
		case 8:
			switch(b){
			case 0:
				return 'T';
			case 1:
				return 'U';
			case 2:
				return 'V';
			}
			break;
		case 9:
			switch(b){
			case 0:
				return 'W';
			case 1:
				return 'X';
			case 2:
				return 'Y';
			}
			break;
		
		}
		return '0';
	}

	
	
	public static void main(String[] args) {

		int[] phone = {4,9,7, 1,9,2,7};
				
		PhoneNumbers number = new PhoneNumbers(phone);
		number.findAllPermutations();

	}
}
