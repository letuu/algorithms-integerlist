package pro.sky.algorithmsintegerlist;

import pro.sky.algorithmsintegerlist.exception.NonExistentValueException;
import pro.sky.algorithmsintegerlist.exception.ValueCannotBeNullException;
import pro.sky.algorithmsintegerlist.exception.WrongIndexException;

import java.util.Arrays;

public class IntegerListImpl implements IntegerList{

    private Integer[] data;
    private int size = 0;

    public IntegerListImpl() {
        this.data = new Integer[]{};
    }

    @Override
    public Integer add(Integer item) {
        return add(size, item);
    }

    @Override
    public Integer add(int index, Integer item) {
        checkItem(item);
        if (index < 0 || index > size) {
            throw new WrongIndexException("Wrong index");
        }
        if (size+1 > data.length) {
            grow();
        }
        System.arraycopy(this.data, index, this.data, index+1, size-index);
        this.data[index] = item;
        this.size++;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        checkItem(item);
        checkIndex(index);
        this.data[index] = item;
        return item;
    }

    @Override
    public Integer removeByItem(Integer item) {
        int elementIndex = indexOf(item);
        if (elementIndex == -1) {
            throw new NonExistentValueException("Failed to find the element to remove");
        }
        return removeByIndex(elementIndex);
    }
    //Это не очень эффективная техника, эффективно - это все в одном цикле аккуратно сделать,
    // но для данной реализации это не сильно требуется, поэтому такой реализации вполне достаточно
    //В ArrayList такая же плюс-минус реализация

    @Override
    public Integer removeByIndex(int index) {
        checkIndex(index);
        Integer elem = this.data[index];
        this.data[index] = null;
        //если элемент не последний
        if (index < size - 1) {
            System.arraycopy(this.data, index+1, this.data, index, size-index-1);
        }
        size--;
        return elem;
    }

    @Override
    public boolean contains(Integer item) {
        return indexOf(item) != -1 && indexOfBinary(item) != -1; //&& чисто для проверки, использовать одно из выражений
    }

    @Override
    public int indexOf(Integer item) {
//        for (int i = 0; i < this.data.length; i++) {
//            if (this.data[i] == null) {
//                break;
//            }
        checkItem(item);
        for (int i = 0; i < size; i++) {
            if (this.data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOfBinary(Integer item) {
//        Arrays.copyOf(this.data, this.data.length);
        //в этом случае нам выгодней выполнить toArray, потому что toArray удалить null-ы из нашего массива и создаст копию
        Integer[] array = toArray();
        sort(array);
        return binarySearch(array, item);
    }

//    @Override
//    public int lastIndexOf(Integer item) {
//        checkItem(item);
//        int lastIndex = -1;
//        for (int i = 0; i < size; i++) {
//            if (item.equals(this.data[i])) {
//                lastIndex = i;
//            }
//        }
//        return lastIndex;
//    }
//    //Вариант с переходом вперед и с бесконечной итерацией

    @Override
    public int lastIndexOf(Integer item) {
        checkItem(item);
        for (int i = size-1; i >= 0; i--) {
            if (item.equals(this.data[i])) {
                return i;
            }
        }
        return -1;
    }
    //Перебирание с конца - цикл сразу прерывается как только находим последний такой элемент списка

    @Override
    public Integer get(int index) {
        checkIndex(index);
        return this.data[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        if (otherList == null) {
            return false;
        }
        IntegerListImpl otherListImpl = (IntegerListImpl) otherList;
//        return Arrays.equals(this.data, otherListImpl.data); //не подходит такое сравнение - нижестоящие массивы различаются, а размерности могут быть равны
        if (otherListImpl.size != this.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.data[i].equals(otherListImpl.data[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            this.data[i] = null;
        }
        this.size = 0;
    }

    @Override
    public Integer[] toArray() {
        Integer[] Integers = new Integer[size];
        System.arraycopy(this.data, 0, Integers, 0, size);
        return Integers;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new WrongIndexException("Wrong index");
        }
    }

    private void checkItem(Integer item) {
        if (item == null) {
            throw new ValueCannotBeNullException("Value in List cannot be null");
        }
    }

//    private void sort(Integer[] array) {
//        for (int i = 1; i < array.length; i++) {
//            int temp = array[i];
//            int j = i;
//            while (j > 0 && array[j - 1] >= temp) {
//                array[j] = array[j - 1];
//                j--;
//            }
//            array[j] = temp;
//        }
//    }
    //Сортировка вставкой - с квадратичной сложностью, быстрее, чем пузырьковая сортировка и сортировка выбором

    private void sort(Integer[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }
    //Быстрая сортировка - самая популярная рекурсивная сортировка

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    private void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private int binarySearch(Integer[] array, int element) {
        int min = 0;
        int max = array.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == array[mid]) {
                return mid;
            }

            if (element < array[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return -1;
    }
    //Ищем индекс элемента в отсортированном списке

    private void grow() {
        int newSize = this.data.length + this.data.length / 2 + 1;
        this.data = Arrays.copyOf(this.data, newSize);
    }
}
