/**
 * FibonacciHeap
 * <p>
 * An implementation of fibonacci heap over integers.
 */

// Tamir Yaari - 304842990 - tamiryaari
// Shay Rozental - 313332181 - shayrozental

import java.util.*;

public class FibonacciHeap {

    private HeapNode min = null;
    private HeapNode last = null;
    private int size = 0;
    private static final double phi = (1 + Math.sqrt(5)) / 2;
    public int counterMarked = 0; // change to private before submitting
    private int counterTrees = 0;
    private static int counterLinks = 0;
    private static int counterCuts = 0;


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
        setMin(root);
        setLast(root);
//        setSize(1);
    }

    public boolean isEmpty() {
        return (findMin() == null);
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
        HeapNode res = toInsert.findMin();
        toInsert.meld(this);
        setMin(toInsert.findMin());
        setLast(toInsert.getLast());
        setSize(size() + 1);
        counterTrees++;
        return res; // should be replaced by student code
    }

    /**
     * public void deleteMin()
     * <p>
     * Delete the node containing the minimum key.
     */
    public void deleteMin() {
        HeapNode min = findMin();
        if (size() == 1) {
            setMin(null);
            setLast(null);
            setSize(0);
            return;
        }
        int numOfChildren = countChildren(min);
        if (findMin().getChild() == null) {
            HeapNode minNext = min.getNext();
            HeapNode minPrev = min.getPrev();
            minPrev.setNext(minNext);
            minNext.setPrev(minPrev);
            if (min.getKey() == getLast().getKey()) {
                setLast(findMin().getPrev());
            }
        } else {
            HeapNode minChild = min.getChild();
            HeapNode minLast = min.getChild().getPrev();

            min.getPrev().setNext(minChild);
            minChild.setPrev(min.getPrev());
            min.getNext().setPrev(minLast);
            minLast.setNext(min.getNext());

            if (min.getKey() == getLast().getKey()) {
                setLast(findMin().getChild().getPrev());
            }

            // delete parent for all children

            HeapNode firstList = minChild;
            do {
                minChild.setParent(null);
                minChild = minChild.getNext();
            } while (minChild.getKey() != firstList.getKey());
        }
        HeapNode[] newFields = consolidate(getLast());
        setSize(size() - 1);
        setMin(newFields[0]);
        setLast(newFields[1]);
        counterTrees--; // original tree with min root is removed from tree count
        counterTrees += numOfChildren; // min's children added to tree count
    }

    private int countChildren(HeapNode x) {
        int counter = 0;
        if (x.getChild() == null) {
            return 0;
        }
        HeapNode firstList = x.getChild();
        HeapNode child = x.getChild();
        do {
            counter++;
            child = child.getNext();
        } while (child.getKey() != firstList.getKey());
        return counter;
    }


    private HeapNode link(HeapNode firstNode, HeapNode secondNode) {
        HeapNode a;
        HeapNode b;
        if (firstNode.getKey() > secondNode.getKey()) {
            b = firstNode;
            a = secondNode;
        } else {
            a = firstNode;
            b = secondNode;
        } // a is the tree with the smaller key, b is the other tree

        if (a.getChild() != null) { // there are children to connect. changing relevant pointers
            insertAfter(b, a.getChild());
        }
        a.setChild(b); // adding b to the list of a's children.
        b.setParent(a);
        a.setRank(a.getRank() + 1); // a's rank is incremented by 1
        counterLinks++;
        counterTrees--;
        return a;
    }

    private void insertAfter(HeapNode before, HeapNode after) {
        HeapNode beforeListFirst = before.next;
        HeapNode afterListPrev = after.prev;

        //changing pointers:
        before.setNext(after);
        after.setPrev(before);
        afterListPrev.setNext(beforeListFirst);
        beforeListFirst.setPrev(afterListPrev);

    }

    private HeapNode[] consolidate(HeapNode x) {
        double bSizeDouble = Math.ceil(Math.log10(size()) / Math.log10(phi));
        int bSize = (int) bSizeDouble;
        HeapNode[] B = new HeapNode[bSize];
        toBuckets(x, B);
        return fromBuckets(B);
    }

    private void toBuckets(HeapNode x, HeapNode[] B) { // assuming x is the last node
        x = x.getNext();
        HeapNode firstList = x;
        Arrays.fill(B, null);

        do {
            HeapNode y = x;
            x = x.getNext();
            while (B[y.getRank()] != null) {
                emptyNode(y);
                y = link(y, B[y.getRank()]);
                B[y.getRank() - 1] = null;
            }
            B[y.getRank()] = y;
            emptyNode(y);
        } while (x.getKey() != firstList.getKey());
    }

    private void emptyNode(HeapNode node) {
        node.setNext(node);
        node.setPrev(node);
    }

    private HeapNode[] fromBuckets(HeapNode[] B) { // returns HeapNode array[], 0 = min, 1 = last
        HeapNode last = null;
        HeapNode min = null;
        for (HeapNode node : B) {
            if (node != null) {
                if (last == null) {
                    last = node;
                    min = node;
                    last.setNext(last);
                    last.setPrev(last);
                } else {
                    insertAfter(last, node);

                    last = node;
                    if (node.getKey() < min.getKey()) {
                        min = node;
                    }
                }
            }
        }
        return new HeapNode[]{min, last};
    }

    public void cut(HeapNode x) {
        HeapNode y = x.getParent();
        x.setParent(null);
        if (x.isMarked()) {
            x.mark = false;
            counterMarked--;
        }
        y.setRank(y.getRank() - 1);
        if (x.getNext() == x) {
            y.setChild(null);
        } else {
            y.setChild(x.getNext());
            x.getPrev().setNext(x.getNext());
            x.getNext().setPrev(x.getPrev());
        }
        emptyNode(x);
        insertAfter(x, getLast().getNext());
        counterCuts++;
        counterTrees++;
    }

    public void cascadingCut(HeapNode x) {
        HeapNode y = x.getParent();
        cut(x);
        if (y.getParent() != null) {
            if (!y.isMarked()) {
                y.mark = true;
                counterMarked++;

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
        return min;
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
            setMin(heap2.findMin());
            setLast(heap2.getLast());
            setSize(heap2.size());
            return;
        }
        insertAfter(getLast(), heap2.getLast().getNext());

        //setting new last:
        setLast(heap2.getLast());

        //setting new min:
        if (heap2.findMin().getKey() < findMin().getKey()) {
            setMin(heap2.findMin());
        }
        setSize(size() + heap2.size());
    }

    /**
     * public int size()
     * <p>
     * Return the number of elements in the heap
     */
    public int size() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    /**
     * public int[] countersRep()
     * <p>
     * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap.
     */
    public int[] countersRep() {
        int[] arr = new int[getMaxRank() + 1];
        HeapNode firstList = getLast();
        HeapNode x = getLast();
        do {
            arr[x.getRank()]++;
            x = x.getNext();
        } while (firstList.getKey() != x.getKey());
        return arr;
    }

    private int getMaxRank() {
        HeapNode firstList = getLast();
        HeapNode x = getLast();
        int maxRank = getLast().getRank();
        do {
            x = x.getNext();
            if (x.getRank() > maxRank) {
                maxRank = x.getRank();
            }
        } while (firstList.getKey() != x.getKey());
        return maxRank;
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     */
    public void delete(HeapNode x) {
        decreaseKey(x, (x.getKey() - findMin().getKey() + 1));
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        x.setKey(x.getKey() - delta);
        if (x.getKey() < findMin().getKey()) {
            setMin(x);
        }
        if (x.getParent() == null || x.getKey() > x.getParent().getKey()) {
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
        return counterTrees + 2 * counterMarked;
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
        return counterLinks;
    }

    /**
     * public static int totalCuts()
     * <p>
     * This static function returns the total number of cut operations made during the run-time of the program.
     * A cut operation is the operation which disconnects a subtree from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts() {
        return counterCuts;
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * <p>
     * This static function returns the k minimal elements in a binomial tree H.
     * The function should run in O(k*deg(H)).
     * You are not allowed to change H.
     */

    public static int[] kMin(FibonacciHeap H, int k)
    {
        // if heap is empty or k = 0 - return an empty array
        if (H.isEmpty() || k == 0) {
            return new int[0];
        }

        int[] res = new int[k];
        FibonacciHeap helper = new FibonacciHeap();
        HeapNode root = H.findMin();
        helper.insert(root.getKey()).setConnector(root); // what is refPointer? used to connect nodes from the two heaps?
        res[0] = root.getKey();
        // if root doesn't have any children
        if (root.getChild() == null) {
            return res;
        }
        HeapNode firstList = root.getChild();
        HeapNode child = firstList;
        helper.deleteMin(); // helper is now empty
        for (int i = 1; i < k; i++) {
            do {
                helper.insert(child.getKey()).setConnector(child);
                System.out.println("inserted node = " + child.getKey() + " | i = " + i);
                child = child.getNext();
            } while (child.getKey() != firstList.getKey());
            res[i] = helper.findMin().getKey(); // when i = 1: inserts minimum of level 1 to helper
            HeapNode helperMinConnector = helper.findMin().getConnector(); // pointer to representation of helper minimum in given tree
            helper.deleteMin(); // deletes minimum from tempHeap, so
            if (helperMinConnector.getChild() == null){
                System.out.println("reached if | helperMinRef = "+helperMinConnector.getKey());
                while(helperMinConnector.getChild() == null && helper.findMin() != null) {
                    helperMinConnector = helper.findMin().getConnector();
                    if (++i>=k) {
                        return res;
                    }
                    res[i] = helper.findMin().getKey();
                    System.out.println();
                    helper.deleteMin();
                }
            }
            firstList = helperMinConnector.getChild();
            child = firstList;
        }
        return res;
    }


    /**
     * public class HeapNode
     * <p>
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in
     * another file
     */

    public HeapNode getLast() {
        return last;
    }

    public void setLast(HeapNode last) {
        this.last = last;
    }

    public void setMin(HeapNode min) {
        this.min = min;
    }

    public class HeapNode {

        public int key;
        private int rank = 0;
        private boolean mark = false;
        private HeapNode next = this;
        private HeapNode prev = this;
        private HeapNode parent = null;
        private HeapNode child = null;
        public HeapNode connector = null;


        public HeapNode(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public HeapNode getChild() {
            return child;
        }

        public void setChild(HeapNode child) {
            this.child = child;
        }

        public HeapNode getNext() {
            return next;
        }

        public void setNext(HeapNode next) {
            this.next = next;
        }

        public HeapNode getPrev() {
            return prev;
        }

        public void setPrev(HeapNode prev) {
            this.prev = prev;
        }

        public HeapNode getParent() {
            return parent;
        }

        public void setParent(HeapNode parent) {
            this.parent = parent;
        }

        public boolean isMarked() {
            return mark;
        }

        public HeapNode getConnector() { return connector; }

        public void setConnector(HeapNode connector) { this.connector = connector; }
    }
}
