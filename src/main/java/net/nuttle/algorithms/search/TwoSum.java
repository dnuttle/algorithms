package net.nuttle.algorithms.search;

public class TwoSum {

  /**
   * O(n) algorithm to find all pairs that sum to zero
   * @param values must be sorted in ascending order
   * @return
   */
  public static int faster(int[] values) {
    int left = 0;
    int right = values.length - 1;
    int count = 0;
    int leftVal = values[left];
    int rightVal = values[right];
    while (left <= right && leftVal <= 0 && rightVal >= 0) {
      leftVal = values[left];
      rightVal = values[right];
      if (leftVal == 0 && rightVal == 0) {
        count += factorial(right - left);
        break;
      }
      else if (leftVal + rightVal == 0) {
        int leftTmp = left + 1;
        int rightTmp = right - 1;
        int countTmp = 0;
        while (rightTmp > left && values[rightTmp] == rightVal) {
          countTmp++;
          rightTmp--;
        }
        while (leftTmp < right && values[leftTmp] == leftVal) {
          countTmp++;
          leftTmp++;
        }
        if (countTmp > 0) count += Math.pow(countTmp, 2);
        else count++;
        left = leftTmp;
        right = rightTmp;
        /*
        count++;
        while (left + 1 < right && values[left + 1] == leftVal) {
          count++;
          left++;
        }
        left++;
        //right--;
         * 
         */
      }
      else if (Math.abs(leftVal) > Math.abs(rightVal)) left++;
      else right--;
    }
    return count;
  }
  
  public static int factorial(int i) {
    int result = 1;
    for (int j = i; j > 0; j--) {
      result *= j;
    }
    return result;
  }
}

