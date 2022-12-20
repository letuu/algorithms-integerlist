package pro.sky.algorithmsintegerlist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.algorithmsintegerlist.exception.NonExistentValueException;
import pro.sky.algorithmsintegerlist.exception.ValueCannotBeNullException;
import pro.sky.algorithmsintegerlist.exception.WrongIndexException;

public class IntegerListTests {

    private IntegerList integerList;

    @BeforeEach
    public void setUp() {
        this.integerList = new IntegerListImpl();
    }

    @Test
    public void whenItemAddedThenItCanBeFoundItList() {
        this.integerList.add(10);
        Assertions.assertEquals(1, this.integerList.size());
    }

    @Test
    public void whenItemAddedToSpecificIndexWhenElementsIsShiftedRight() {
        this.integerList.add(10);
        this.integerList.add(0, 11);
        this.integerList.add(1, 12);
        this.integerList.add(3, 100);
        Assertions.assertEquals(4, this.integerList.size());
        Assertions.assertEquals(11, this.integerList.get(0));
        Assertions.assertEquals(12, this.integerList.get(1));
        Assertions.assertEquals(10, this.integerList.get(2));
        Assertions.assertEquals(100, this.integerList.get(3));
    }

    @Test
    public void whenValueIsSetWhenGetReturnsThisValue() {
        this.integerList.add(10);
        this.integerList.add(11);
        this.integerList.add(12);

        this.integerList.set(1, 111);   //111 - NEW_VALUE
        Assertions.assertEquals(3, this.integerList.size());
        Assertions.assertEquals(111, this.integerList.get(1));
    }

    @Test
    public void whenTwoElementsAddedInListThenIndexOfReturnsFirst() {
        this.integerList.add(10);
        this.integerList.add(10);
        Assertions.assertEquals(0, this.integerList.indexOf(10));
    }

    @Test
    public void whenTwoElementsAddedInListThenIndexOfUnknownReturnsMinusOne() {
        this.integerList.add(10);
        this.integerList.add(10);
        Assertions.assertEquals(-1, this.integerList.indexOf(999)); //999 - NON_EXISTING_VALUE
    }

    @Test
    public void whenThreeElementsAddedInListThenIndexOfBinary() {
        this.integerList.add(11);
        this.integerList.add(10); //после сортировки внутри метода indexOfBinary этот элемент будет с индексом 0 как min
        this.integerList.add(12);
        Assertions.assertEquals(0, this.integerList.indexOfBinary(10));
    }

    @Test
    public void whenTwoElementsAddedInListThenIndexOfBinaryUnknownReturnsMinusOne() {
        this.integerList.add(11);
        this.integerList.add(10);
        Assertions.assertEquals(-1, this.integerList.indexOfBinary(999)); //999 - NON_EXISTING_VALUE
    }

    @Test
    public void whenTwoElementsAddedInListThenLastIndexOfReturnsSecond() {
        this.integerList.add(10);
        this.integerList.add(10);
        Assertions.assertEquals(1, this.integerList.lastIndexOf(10));
    }

    @Test
    public void whenTwoElementsAddedInListThenLastIndexOfUnknownReturnsMinusOne() {
        this.integerList.add(10);
        this.integerList.add(10);
        Assertions.assertEquals(-1, this.integerList.lastIndexOf(999)); //999 - NON_EXISTING_VALUE
    }

    @Test
    public void whenTwoElementsAddedAndSecondRemovedTheSizeIsOne() {
        this.integerList.add(10);
        this.integerList.add(11);
        this.integerList.removeByIndex(1);
        Assertions.assertEquals(1, this.integerList.size());
        Assertions.assertEquals(10, this.integerList.get(0));
    }

    @Test
    public void whenTwoElementsAddedAndFirstRemovedTheSizeIsOne() {
        this.integerList.add(10);
        this.integerList.add(11);
        this.integerList.removeByIndex(0);
        Assertions.assertEquals(1, this.integerList.size());
        Assertions.assertEquals(11, this.integerList.get(0));
    }

    @Test
    public void whenThreeElementsAddedAndSecondRemovedTheSizeIsTwo() {
        this.integerList.add(10);
        this.integerList.add(11);
        this.integerList.add(12);
        this.integerList.removeByIndex(1);
        Assertions.assertEquals(2, this.integerList.size());
        Assertions.assertEquals(10, this.integerList.get(0));
        Assertions.assertEquals(12, this.integerList.get(1));
    }

    @Test
    public void whenElementsAddedAndClearIsCalledThenListIsEmpty() {
        this.integerList.add(10);
        this.integerList.add(11);
        this.integerList.add(12);
        this.integerList.clear();
        Assertions.assertTrue(this.integerList.isEmpty());
        Assertions.assertEquals(0, this.integerList.size());
    }

    //
    @Test
    public void whenThreeElementsAddedAndSecondRemovedByItemTheSizeIsTwo() {
        this.integerList.add(10);
        this.integerList.add(11);
        this.integerList.add(12);
        this.integerList.removeByItem(11);
        Assertions.assertEquals(2, this.integerList.size());
        Assertions.assertEquals(10, this.integerList.get(0));
        Assertions.assertEquals(12, this.integerList.get(1));
    }

    @Test
    public void whenElementsAddedAndContainsThenListContains() {
        this.integerList.add(10);
        this.integerList.add(11);
        Assertions.assertTrue(this.integerList.contains(11));
    }

    @Test
    public void whenArrayListToArrayThenArrayListCopiedToArrayAndReturns() {
        this.integerList.add(10);
        this.integerList.add(11);
        Integer[] integers = this.integerList.toArray();
        Assertions.assertEquals(2, integers.length);
        Assertions.assertEquals(10, integers[0]);
        Assertions.assertEquals(11, integers[1]);
    }

    @Test
    public void whenOtherListEquals() {
        this.integerList.add(10);
        this.integerList.add(11);
        IntegerList otherListEquals =  new IntegerListImpl();
        otherListEquals.add(10);
        otherListEquals.add(11);
        IntegerList otherListNotEqualElement =  new IntegerListImpl();
        otherListNotEqualElement.add(10);
        otherListNotEqualElement.add(13);
        IntegerList ListNotEqualSize =  new IntegerListImpl();
        ListNotEqualSize.add(10);
        ListNotEqualSize.add(11);
        ListNotEqualSize.add(12);
        Assertions.assertFalse(this.integerList.equals(null));
        Assertions.assertTrue(this.integerList.equals(otherListEquals));
        Assertions.assertFalse(this.integerList.equals(otherListNotEqualElement));
        Assertions.assertFalse(this.integerList.equals(ListNotEqualSize));
    }

    @Test
    public void whenInvalidArgumentThenException() {
        this.integerList.add(10);
        this.integerList.add(11);
        this.integerList.add(12);
        Assertions.assertThrows(WrongIndexException.class, () -> this.integerList.add(-1, 99)); //OUT_OF_SIZE
        Assertions.assertThrows(WrongIndexException.class, () -> this.integerList.add(4, 99)); //OUT_OF_SIZE
        Assertions.assertThrows(NonExistentValueException.class, () -> this.integerList.removeByItem(999)); //NON_EXISTING_VALUE
        //проверка checkIndex(index)
        Assertions.assertThrows(WrongIndexException.class, () -> this.integerList.get(3));
        //проверка checkItem(item)
        Assertions.assertThrows(ValueCannotBeNullException.class, () -> this.integerList.indexOf(null));
    }
}
