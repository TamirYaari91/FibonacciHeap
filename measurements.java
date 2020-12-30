import java.util.*;

public class measurements {

    public static void Q1(int power) {
        int m = (int) Math.pow(2.0, power);
        List<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        FibonacciHeap fibHeap = new FibonacciHeap();
        double startTime = System.nanoTime();
        for (int i = m; i >= 0; i--) {
            nodes.add(fibHeap.insert(i));
        }
        fibHeap.deleteMin();
        int delta = m + 1;
        for (int i = 0; i < power; i++) {
            double sigma = 0;
            for (int k = 1; k <= i; k++) {
                sigma += Math.pow(0.5, k);
            }
            int index = (int) (m - ((m * sigma)) - 2);
            fibHeap.decreaseKey(nodes.get(index), delta);
        }
        fibHeap.decreaseKey(nodes.get(1), delta);
        double endTime = System.nanoTime();
        System.out.println("totalLinks = " + FibonacciHeap.totalLinks());
        System.out.println("totalCuts = " + FibonacciHeap.totalCuts());
        System.out.println("potential = " + fibHeap.potential());
        System.out.println("runTime in milliseconds = "+(endTime - startTime) / 1000000.0);

    }

    public static void Q2(int m) {
        FibonacciHeap fibHeap = new FibonacciHeap();
        double startTime = System.nanoTime();
        for (int i = m; i > 0; i--) {
            fibHeap.insert(i);
        }
        for (int i = 0; i < (m/2); i++) {
            fibHeap.deleteMin();
        }
        double endTime = System.nanoTime();
        System.out.println("totalLinks = " + FibonacciHeap.totalLinks());
        System.out.println("totalCuts = " + FibonacciHeap.totalCuts());
        System.out.println("potential = " + fibHeap.potential());
        System.out.println("runTime in milliseconds = "+(endTime - startTime) / 1000000.0);

    }

    public static void main(String[] args) {
        Q2(2000);
    }
}
