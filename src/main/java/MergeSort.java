public class MergeSort {

  public static void main(String[] args) {

    int idx = 1;
    switch(idx){
      case 1:
      case 2:
        System.out.println("2");
        break;
      default:
        System.out.println("Default");
        break;
    }
    int[] arr = new int[]{2,1,6,3,0};

    mergeSort(arr);

    for (int i : arr){
      System.out.print(i + " ");
    }
  }

  private static void mergeSort(int[] arr) {
    if(arr.length>1){
      int[] d1 = divide(arr,1);
      int[] d2 = divide(arr,2);
      if(d1.length>1) mergeSort(d1);
      if(d2.length>1) mergeSort(d2);
      int idxD1 = 0;
      int idxD2 = 0;
      int arrIdx = 0;
      while(idxD1 < d1.length || idxD2 < d2.length){
        if(idxD1 < d1.length && idxD2 < d2.length){
          if(d1[idxD1] < d2[idxD2])
            arr[arrIdx++] = d1[idxD1++];
          else arr[arrIdx++] = d2[idxD2++];
        } else {
          if(idxD1 == d1.length)
            arr[arrIdx++] = d2[idxD2++];
          else arr[arrIdx++] = d1[idxD1++];
        }
      }
    }
  }

  private static int[] divide(int[] arr, int group) {
    int divisionPoint = (arr.length - 1) / 2;
    int[] result;
    if(group == 1){
      result = new int[divisionPoint + 1];
      for(int i = 0; i <= divisionPoint; i++)
        result[i] = arr[i];
    } else {
      result = new int[arr.length - divisionPoint - 1];
      for(int i = divisionPoint + 1; i < arr.length; i++)
        result[i - (divisionPoint + 1)] = arr[i];
    }

    return result;
  }

}
