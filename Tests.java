public class Tests {

    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(2);
        heap.insert(1);
        FibonacciHeap.HeapNode eighteen = heap.insert(18);
        heap.insert(22);
//        heap.insert(0);
        heap.insert(55);
//        heap.deleteMin();
        heap.deleteMin();
        heap.decreaseKey(eighteen,50);
//        System.out.println("eighteen parent is "+eighteen.parent.getKey());
        System.out.println("min = "+heap.findMin().getKey());
        System.out.println("last = "+heap.last.getKey());
//        System.out.println(eighteen.getKey());
        System.out.println("last.next = "+heap.last.next.getKey());
        System.out.println("last.prev = "+heap.last.prev.getKey());
        System.out.println("last.child = "+heap.last.child.getKey());

//        System.out.println("last.child = "+heap.last.child.getKey());
//        System.out.println("last.child.parent = "+heap.last.child.parent.getKey());
//        System.out.println("last.child.next = "+heap.last.child.next.getKey());
    }
}
