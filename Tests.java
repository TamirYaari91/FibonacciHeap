import java.util.Arrays;

public class Tests {

    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
//        heap.insert(2);
        heap.insert(3);
        FibonacciHeap.HeapNode eighteen = heap.insert(18);
        heap.insert(22);
        heap.insert(1);
        heap.insert(55);
        heap.insert(6);
//        heap.insert(30);
//        heap.insert(9);
//        heap.deleteMin();
        heap.decreaseKey(eighteen,50);
        heap.deleteMin();
        int[] arr = FibonacciHeap.kMin(heap,4);
        System.out.println(Arrays.toString(arr));

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
