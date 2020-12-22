/**
 * FibonacciHeap
 * <p>
 * An implementation of fibonacci heap over integers.
 */

import java.util.*;

public class FibonacciHeap {

    public HeapNode min = null; // change to private before submitting
    public HeapNode last = null; // change to private before submitting
    public int size = 0; // change to private before submitting
    private final double phi = (1 + Math.sqrt(5)) / 2;


    /**
     * public boolean isEmpty()
     * <p>
     * precondition: none
     * <p>
     * The method returns true if and only if the heap
     * is empty.
     */

    public FibonacciHeap() {

    }

    private FibonacciHeap(int key) {
        HeapNode root = new HeapNode(key);
        min = root;
        last = root;
    }

    public boolean isEmpty() {
        return (min == null);
    }

    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * <p>
     * Returns the new node created.
     */
    public HeapNode insert(int key) {
        FibonacciHeap toInsert = new FibonacciHeap(key);
        HeapNode res = toInsert.min;
        toInsert.meld(this);
        min = toInsert.findMin();
        last = toInsert.last;
        size++;
        return res; // should be replaced by student code
    }

    /**
     * public void deleteMin()
     * <p>
     * Delete the node containing the minimum key.
     */
    public void deleteMin() {
        HeapNode min = findMin();
        if (min.child == null) {
            HeapNode minNext = min.next;
            HeapNode minPrev = min.prev;
            minPrev.next = minNext;
            minNext.prev = minPrev;
            if (min.getKey() == last.getKey()) {
                last = min.prev;
            }
        } else {
            HeapNode minChild = min.child;
            HeapNode minLast = min.child.prev;

            min.prev.next = minChild;
            minChild.prev = min.prev;
            min.next.prev = minLast;
            minLast.next = min.next;

            if (min.getKey() == last.getKey()) {
                last = min.child.prev;
            }

            // delete parent for all children

            HeapNode firstList = minChild;
            while (true) {
                minChild.parent = null;
                minChild = minChild.next;
                if (minChild.getKey() == firstList.getKey()) {
                    break;
                }
            }
        }
        size--;
        HeapNode[] newFields = consolidate(last);
        System.out.println(newFields[1].child.next.parent.getKey());
        this.min = newFields[0];
        this.last = newFields[1];
        return;
    }


    private HeapNode link(HeapNode firstNode, HeapNode secondNode) {
        HeapNode a = null;
        HeapNode b = null;
        if (firstNode.getKey() > secondNode.getKey()) {
            b = firstNode;
            a = secondNode;
        } else {
            a = firstNode;
            b = secondNode;
        } // a is the tree with the smaller key, b is the other tree

        if (a.child != null) { // there are children to connect. changing relevant pointers
            insertAfter(b, a.child);
        }
        a.child = b; // adding b to the list of a's children.
        b.parent = a;
        a.rank += 1; // a's rank is incremented by 1
        if (a.getKey() == 2 && b.getKey() == 18) {
            System.out.println("print from link: parent of "+b.getKey()+" is "+b.parent.getKey());
        }
        return a;
    }

    private void insertAfter(HeapNode before, HeapNode after) {
        HeapNode beforeListFirst = before.next;
        HeapNode afterListPrev = after.prev;

        //changing pointers:
        before.next = after;
        after.prev = before;
        afterListPrev.next = beforeListFirst;
        beforeListFirst.prev = afterListPrev;


    }

    private HeapNode[] consolidate(HeapNode x) {
        double bSizeDouble = Math.ceil(Math.log10(size()) / Math.log10(phi));
        int bSize = (int) bSizeDouble;
        HeapNode[] B = new HeapNode[bSize];
        toBuckets(x, B);
        return fromBuckets(B);
    }

    private void toBuckets(HeapNode x, HeapNode[] B) { // assuming x is the last node
        x = x.next;
        HeapNode firstList = x;
        Arrays.fill(B, null);

        while (true) {
            HeapNode y = x;
            x = x.next;
            while (B[y.rank] != null) {
                emptyNode(y);
                y = link(y, B[y.rank]);
//                System.out.println("y is "+y.getKey());
//                System.out.println("y child is "+y.child.getKey());
//                System.out.println("y child.next is "+y.child.next.getKey());
//                System.out.println("y child.next.parent is "+y.child.next.parent.getKey());
                B[y.rank - 1] = null;
            }
            B[y.rank] = y;
            emptyNode(y);
            if (x.getKey() == firstList.getKey()) {
                break;
            }
        }
    }
    private void emptyNode(HeapNode node){
        node.next = node;
        node.prev = node;
    }

    private HeapNode[] fromBuckets(HeapNode[] B) { // returns HeapNode array[], 0 = min, 1 = last
        HeapNode last = null;
        HeapNode min = null;
        for (int i = 0; i < B.length; i++) {
            if (B[i] != null) {
                if (last == null) {
                    last = B[i];
                    min = B[i];
                    last.next = last;
                    last.prev = last;
//                    if (last.getKey() == 2) {
//                        System.out.println("last.child.next.parent = "+last.child.next.parent.getKey());
//                    }
                } else {
                    insertAfter(last, B[i]);

                    last = B[i];
                    if (B[i].getKey() < min.getKey()) {
                        min = B[i];
                    }
                }
            }
        }
        return new HeapNode[]{min, last};
    }

    public void cut(HeapNode x) {
        System.out.println("x = "+x.getKey());
        HeapNode y = x.parent;
        x.parent = null;
        x.mark = false;
        y.rank--;
        if (x.next == x) {
            y.child = null;
        } else {
            y.child = x.next;
            x.prev.next = x.next;
            x.next.prev = x.prev;
        }
        System.out.println("x = "+x.getKey());
        insertAfter(x,last.next);
    }

    public void cascadingCut(HeapNode x) {
        HeapNode y = x.parent;
        cut(x);
        if (y.parent != null) {
            if (!y.mark) {
                y.mark = true;
            } else {
                cascadingCut(y);
            }
        }
    }

    /**
     * public HeapNode findMin()
     * <p>
     * Return the node of the heap whose key is minimal.
     */
    public HeapNode findMin() {
        return min;// should be replaced by student code
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Meld the heap with heap2
     */
    public void meld(FibonacciHeap heap2) {
        if (heap2.isEmpty()) {
            return;
        } else if (this.isEmpty()) {
            min = heap2.min;
            last = heap2.last;
            return;
        }
        insertAfter(last, heap2.last.next);

        //setting new last:
        last = heap2.last;

        //setting new min:
        if (heap2.min.getKey() < min.getKey()) {
            min = heap2.min;
        }
        size += heap2.size();
    }

    /**
     * public int size()
     * <p>
     * Return the number of elements in the heap
     */
    public int size() {
        return size; // should be replaced by student code
    }


    /**
     * public int[] countersRep()
     * <p>
     * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap.
     */
    public int[] countersRep() {
        int[] arr = new int[42];
        return arr; //	 to be replaced by student code
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     */
    public void delete(HeapNode x) {
        return; // should be replaced by student code
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        x.key -= delta;
        if (x.parent == null) {
            if (x.getKey() < min.getKey()) {
                min = x;
            }
            return;
        }
        if (x.getKey() < x.parent.getKey()) {
            return;
        }
        cascadingCut(x);
    }

    /**
     * public int potential()
     * <p>
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap.
     */
    public int potential() {
        return 0; // should be replaced by student code
    }

    /**
     * public static int totalLinks()
     * <p>
     * This static function returns the total number of link operations made during the run-time of the program.
     * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of
     * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value
     * in its root.
     */
    public static int totalLinks() {
        return 0; // should be replaced by student code
    }

    /**
     * public static int totalCuts()
     * <p>
     * This static function returns the total number of cut operations made during the run-time of the program.
     * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts() {
        return 0; // should be replaced by student code
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * <p>
     * This static function returns the k minimal elements in a binomial tree H.
     * The function should run in O(k*deg(H)).
     * You are not allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k) {
        int[] arr = new int[42];
        return arr; // should be replaced by student code
    }

    /**
     * public class HeapNode
     * <p>
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in
     * another file
     */
    public class HeapNode {

        public int key;
        public int rank = 0; // change to private before submitting
        public boolean mark = false; // change to private before submitting
        public HeapNode next = this; // change to private before submitting
        public HeapNode prev = this; // change to private before submitting
        public HeapNode parent = null; // change to private before submitting
        public HeapNode child = null; // change to private before submitting


        public HeapNode(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

    }
}
