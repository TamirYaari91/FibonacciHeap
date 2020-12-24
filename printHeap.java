//package Heap;
// By Elad Feldman 19/12/20

import java.util.ArrayList;
import java.util.Collections;

public class printHeap {
    public static void main(String[] args)  {
        FibonacciHeap h1 = new FibonacciHeap();
        printHeapFib(h1);

//        insertN(h1,5);
        h1.insert(2);
        h1.insert(1);
        h1.insert(4);
        h1.insert(3);
        h1.insert(0);
        printHeapFib(h1);
        h1.deleteMin();
        printHeapFib(h1);
        h1.deleteMin();
        printHeapFib(h1);
        h1.deleteMin();
        printHeapFib(h1);
        System.out.println(h1.getLast().getKey());
        System.out.println(h1.getLast().getChild().getNext().getKey());
        System.out.println("size = "+ h1.size());
        h1.deleteMin();
        printHeapFib(h1);
        h1.deleteMin();
        printHeapFib(h1);

//        System.out.println("total cuts = " + FibonacciHeap.totalCuts());
//        System.out.println("total link = " + FibonacciHeap.totalLinks());
//        System.out.println("potential = " + h1.potential());

    }





    public static void printHeapFib(FibonacciHeap heap) {
        System.out.println("-----------------------------------------------");
        if (heap.size() == 0) {
            System.out.println("empty heap!");
            System.out.println("-----------------------------------------------");
            return;
        }
        String[] list = new String[heap.size()*10];
        for (int i = 0; i < heap.size(); i++) {
            list[i] = "";
        }
        Integer level = 0;
        printHeapFib(heap.findMin(), list, level);
        for (int i = 0; i < heap.size(); i++) {
            if (list[i]!="")
                System.out.println(list[i]);
        }
        System.out.println("-----------------------------------------------");
    }

    public  static void printHeapFib(FibonacciHeap.HeapNode node, String[] list, Integer level) {

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

                list[level] +=getChain("=");
                temp = temp.getNext();
            } while (temp != node);
            list[level] += ")";
        }


    }
    private static String getChain(String link){
        int count =2;;
        String str="";
        if (count==0){
            return "";
        }
        for (int i=0;i<count/3+1;i++){
            str+=link;
        }
        return str+">";
    }
    public static void insertN(FibonacciHeap h1,int n) {
        insertN( h1,1, n);

    }


    public static void insertN(FibonacciHeap h1,int start, int amount) {
        ArrayList<Integer> mylist = new ArrayList();
        for(int i = start; i <  start+amount; i++) {
            mylist.add(i);
        }
        Collections.shuffle(mylist);
        for (int i = 0; i < amount; i++) {
            h1.insert(mylist.get(i));

        }
    }

}

