/*
Given an array of integers, write a function that returns the beginning and end
indices of the largest consecutive subarray sum of integers


Ex: -8, 2, 5, -6, 3, 4, -30     
Largest consecutive subset: 2 + 5 + -6 + 3 + 4 = 8
Returns 1, 5

Ex: 5, -10, 6, -10, 8, -7, 1
Largest consecutive subset: 8
Returns 4, 4
*/


// Ex: -8, 2, 5, -6, 3, 4, -30
//     -8, -6, -1,-7,-4, 0, -30


/*
public void printLargestSum(int[] numbers){
    int[] preProcessedArray = new int[numbers.length];
    
    for(int i = 0; i<numbers.length; i++){
        if(i>=0){
            preProcessedArray[i] = preProcessedArray[i-1] + numbers[i];
        }else{
            preProcessedArray[i] = number[i];   
        }
    }
    
    int lastMinValue = 0, lastMinIndex = -1, maxDifference = 0, maxIndex = 0, currentMinValue = 0 ,currentMinIndex = -1;
    //     -1,8,-1
    /*
    j=0;
    minValue = 0
    minIndex = 0
    maxDifference = 0
    maxIndex = 0
    */
    
    // [7]
    
/*
    for(int j = 0, j<preProcessedArray.length ; j++){
        if(preProcessedArray[j]-currentMinValue > maxDifference){
            maxDifference = preProcessedArray[j]-minValue;
            maxIndex = j;
            lastMinValue = currentMinValue;
            lastMinIndex = currentMinIndex;
        }
        
        if(preProcessedArray[j] < minValue){
            currentMinValue = preProcessedArray[j];
            currentMinIndex = j;            
        }
    }
    
    System.out.println((lastMinIndex+2) + ", " + (maxIndex+1));
}

*/