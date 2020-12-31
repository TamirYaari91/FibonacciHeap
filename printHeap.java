//package Heap;
// By Elad Feldman 19/12/20

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class printHeap {

    public static void main(String[] args) {
        kMinTest3(5);
    }

    public static void kMinTest1(int k) {
        FibonacciHeap h1 = new FibonacciHeap();
        printHeapFib(h1);
        h1.insert(-1);
        h1.insert(0);
        h1.insert(30);
        h1.insert(15);
        h1.insert(27);
        h1.insert(20);
        h1.insert(35);
        h1.insert(31);
        h1.insert(46);
        h1.insert(1);
        h1.insert(19);
        h1.insert(40);
        h1.insert(51);
        h1.insert(2);
        h1.insert(18);
        h1.insert(3);
        h1.insert(4);
        h1.deleteMin();
        printHeapFib(h1);
        int[] kmin = FibonacciHeap.kMin2(h1, k);
        System.out.println(Arrays.toString(kmin));
    }

    public static void kMinTest2(int k) {
        FibonacciHeap h1 = new FibonacciHeap();
        printHeapFib(h1);
        h1.insert(-1);
        h1.insert(0);
        h1.insert(15);
        h1.insert(2);
        h1.insert(6);
        h1.insert(3);
        h1.insert(12);
        h1.insert(14);
        h1.insert(20);
        h1.insert(1);
        h1.insert(10);
        h1.insert(5);
        h1.insert(38);
        h1.insert(7);
        h1.insert(120);
        h1.insert(101);
        h1.insert(102);
        h1.deleteMin();
        printHeapFib(h1);
        int[] kmin = FibonacciHeap.kMin2(h1, k);
        System.out.println(Arrays.toString(kmin));
    }

    public static void kMinTest3(int k) {
        FibonacciHeap fibonacciHeap = new FibonacciHeap();
        for (int i = 0; i < 33; i++) {
            fibonacciHeap.insert(i);
        }
        fibonacciHeap.deleteMin();
        printHeapFib(fibonacciHeap);

        int[] kmin = FibonacciHeap.kMin2(fibonacciHeap, 10);
        System.out.println(Arrays.toString(kmin));
    }


    public static void printHeapFib(FibonacciHeap heap) {
        System.out.println("-----------------------------------------------");
        if (heap.size() == 0) {
            System.out.println("empty heap!");
            System.out.println("-----------------------------------------------");
            return;
        }
        String[] list = new String[heap.size() * 10];
        for (int i = 0; i < heap.size(); i++) {
            list[i] = "";
        }
        Integer level = 0;
        printHeapFib(heap.findMin(), list, level);
        for (int i = 0; i < heap.size(); i++) {
            if (list[i] != "")
                System.out.println(list[i]);
        }
        System.out.println("-----------------------------------------------");
    }

    public static void printHeapFib(FibonacciHeap.HeapNode node, String[] list, Integer level) {

        list[level] += "(";
        if (node == null) {
            list[level] += ")";
            return;
        } else {
            FibonacciHeap.HeapNode temp = node;
            do {
                list[level] += temp.getKey();
                FibonacciHeap.HeapNode k = temp.getChild();
                printHeapFib(k, list, level + 1);

                list[level] += getChain("=");
                temp = temp.getNext();
            } while (temp != node);
            list[level] += ")";
        }


    }

    private static String getChain(String link) {
        int count = 2;
        ;
        String str = "";
        if (count == 0) {
            return "";
        }
        for (int i = 0; i < count / 3 + 1; i++) {
            str += link;
        }
        return str + ">";
    }

    public static void insertN(FibonacciHeap h1, int n) {
        insertN(h1, 1, n);

    }


    public static void insertN(FibonacciHeap h1, int start, int amount) {
        ArrayList<Integer> mylist = new ArrayList();
        for (int i = start; i < start + amount; i++) {
            mylist.add(i);
        }
        Collections.shuffle(mylist);
        for (int i = 0; i < amount; i++) {
            h1.insert(mylist.get(i));

        }
    }

}

