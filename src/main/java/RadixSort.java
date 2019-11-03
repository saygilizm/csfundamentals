import java.util.Scanner;
import java.util.stream.Stream;

public class RadixSort {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String[] input = sc.nextLine().split(",");
    int[] arr = Stream.of(input).mapToInt(Integer::parseInt).toArray();
    radixSort(arr);

    for (int element : arr) {
      System.out.println(element);
    }
  }

  private static void radixSort(int[] arr) {
    int max = getMaxValue(arr);
    for(int i = 1; i < max; i *= 10)
      countingSort(arr, i);
  }

  private static void countingSort(int[] arr, int currentDigit) {
    int length = arr.length;
    int[] count = new int[10];
    int[] output = new int[length];
    for(int element : arr)
      count[(element % (currentDigit * 10)) / currentDigit]++;
    for(int i = 1; i < 10; i++)
      count[i] += count[i-1];
    for(int i = length-1; i >= 0; i--)
      output[(count[(arr[i] % (currentDigit * 10)) / currentDigit]--) - 1] = arr[i];
    for(int i = 0; i < length; i++)
      arr[i] = output[i];
  }

  private static int getMaxValue(int[] arr) {
  int max = Integer.MIN_VALUE;
    for(int element : arr){
      if(element > max) max = element;
    }
    return max;
  }
}
