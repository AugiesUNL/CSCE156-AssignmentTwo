package com.bc.model.containers;

import java.util.Arrays;
import java.util.Comparator;

public class ADT<T> {
    private static final int SIZE = 1;
    private T[] arr;
    private int size;
    private Comparator<T> comparator;

    public ADT() {
        this.arr = (T[]) new Object[SIZE];
        this.size = 0;
    }

    private void increaseArraySize() {
        this.arr = Arrays.copyOf(this.arr, this.arr.length + SIZE);
    }

    public T getElementAtIndex(int index) {
        if (index < 0 || index > this.size) {
            throw new ArrayIndexOutOfBoundsException("Enter a correct index!!");
        }
        return this.arr[index];
    }

    public void addElementToEnd(T item) {
        this.addElementAtIndex(item, this.size);
    }

    public void addElementToStart(T item) {
        this.addElementAtIndex(item, 0);
    }

    public void addElementAtIndex(T item, int index) {
        if (index < 0 || index > this.size) {
            throw new ArrayIndexOutOfBoundsException("Enter a correct index!!");
        }
        if (this.arr.length == this.size) {
            this.increaseArraySize();
        }
        for (int i = this.size; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        this.arr[index] = item;
        this.size++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            sb.append(this.arr[i] + ", ");
        }
        return sb.toString();
    }

}

