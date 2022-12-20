package pro.sky.algorithmsintegerlist;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class SortComparison {
    public static void main(String[] args) {

//        int[] array = generateRandomArray();
//        long start = System.currentTimeMillis();
//        bubbleSort(array);
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
//        System.out.println(isSorted(array));
//        //Вместо этого - метод с функциональным интерфейсом benchmarkSort

//        benchmarkSort((ints) -> bubbleSort(ints));
//        benchmarkSort(SortComparison::bubbleSort);
//        benchmarkSort(SortComparison::selectionSort);
//        benchmarkSort(SortComparison::insertionSort);
        //это для метода benchmarkSortNewArray

        int[] array = generateRandomArray();
        for (int i = 0; i < 10; i++) {
            System.out.println("Iteration " + i);
            benchmarkSort(Arrays.copyOf(array, array.length), SortComparison::bubbleSort);
            benchmarkSort(Arrays.copyOf(array, array.length), SortComparison::bubbleSortSwap);
            benchmarkSort(Arrays.copyOf(array, array.length), SortComparison::bubbleSortVolkov);
            benchmarkSort(Arrays.copyOf(array, array.length), SortComparison::selectionSort);
            benchmarkSort(Arrays.copyOf(array, array.length), SortComparison::insertionSort);
        }
    }

    public static void benchmarkSortNewArray(Consumer<int[]> sortFunction) {
        int[] array = generateRandomArray();
        long start = System.currentTimeMillis();
        sortFunction.accept(array);
        long end = System.currentTimeMillis();
        System.out.println("IsSorted = " + isSorted(array));
        System.out.println("ExecutionTime = " + (end - start) + " ms");
    }
    //каждый проверка - новый массив

    public static void benchmarkSort(int[] array, Consumer<int[]> sortFunction) {
        long start = System.currentTimeMillis();
        sortFunction.accept(array);
        long end = System.currentTimeMillis();
        System.out.println("IsSorted = " + isSorted(array));
        System.out.println("ExecutionTime = " + (end - start) + " ms");
    }
    //каждый проверка - массив один генерируется и копируется во все проверки

    private static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    swapElements(array, j, j + 1);
                }
            }
        }
    }
    //пузырьковая сортировка из шпаргалки

    private static void bubbleSortSwap(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }
    //пузырьковая сортировка из шпаргалки, только метод swapElements реализован внутри сортировки
    //немного медленнее, чем та, у которой код перестановки элементов swapElements вынесен отдельно в метод

    private static void bubbleSortVolkov(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[j] > array[i]) {
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }
    //эта сортировка от АВолкова тоже пузырьковая, но она быстрее, чем дана в шпаргалке, примерно в 1,5 раза

    private static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    private static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }
    //самая быстрая из представленных сортировок

    private static int[] generateRandomArray() {
        return IntStream
                .generate(() -> ThreadLocalRandom.current().nextInt(0, 1_000_000))
                .limit(10000)
                .toArray();
    }

    private static void swapElements(int[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
