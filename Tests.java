import java.util.ArrayList;
import java.util.Arrays;

public class Tests {

    static FibonacciHeap fibonacciHeap;


    static void addKeys(int start) {
        for (int i = 0; i < 1000; i++) {//@@@@@@@ i<1000 @@@@@
            fibonacciHeap.insert(start + i);
        }
    }

    static void addKeysReverse(int start) {
        for (int i = 999; i >= 0; i--) {
            fibonacciHeap.insert(start + i);
        }
    }

    public static void main(String[] args) {
        fibonacciHeap = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.insert(4);
        fibonacciHeap.insert(5);
        FibonacciHeap.HeapNode node = fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();

        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();

        System.out.println(fibonacciHeap.getLast().getChild().getChild().getParent().getParent().getKey());
        fibonacciHeap.decreaseKey(node, 2);

        System.out.println("counterMarked outside = "+fibonacciHeap.counterMarked);
        System.out.println("-------");
        System.out.println("potential = "+fibonacciHeap.potential());
        System.out.println("cuts = "+(FibonacciHeap.totalCuts() - cuts));
        System.out.println("links = "+(FibonacciHeap.totalLinks() - links));

        if (fibonacciHeap.potential() != 4 ||
                FibonacciHeap.totalCuts() - cuts != 1 ||
                FibonacciHeap.totalLinks() - links != 3)
            System.out.println("bug");



            //        heap.insert(2);
//        heap.insert(3);
//        FibonacciHeap.HeapNode eighteen = heap.insert(18);
//        heap.insert(22);
//        heap.insert(1);
//        heap.insert(55);
//        heap.insert(6);
//        heap.insert(30);
//        heap.insert(9);
//        heap.deleteMin();
//        heap.decreaseKey(eighteen,50);
//        heap.deleteMin();
//        heap.deleteMin();

//        int[] arr = FibonacciHeap.kMin(heap,4);
//        System.out.println(Arrays.toString(arr));

//        System.out.println("eighteen parent is "+eighteen.parent.getKey());
//        System.out.println("min = "+heap.findMin().getKey());
//        System.out.println("min.child = "+heap.findMin().getChild().getKey());

//        System.out.println("last = "+heap.getLast().getKey());
//        System.out.println(eighteen.getKey());
//        System.out.println("last.next = "+heap.getLast().getNext().getKey());

//        System.out.println("last.prev = "+heap.getLast().getPrev().getKey());
//        System.out.println("last.child = "+heap.getLast().getChild().getKey());

//        System.out.println("last.child = "+heap.last.child.getKey());
//        System.out.println("last.child.parent = "+heap.last.child.parent.getKey());
//        System.out.println("last.child.next = "+heap.last.child.next.getKey());
    }
}
