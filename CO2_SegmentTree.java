import java.util.*;

/**
 * CO2 Topic: Segment Tree
 * This program demonstrates range-sum query and point update operations
 * using a Segment Tree.
 */
public class CO2_SegmentTree {
    static class SegmentTree {
        private int[] tree;
        private int[] array;
        private int n;

        SegmentTree(int[] input) {
            this.array = input.clone();
            this.n = input.length;
            this.tree = new int[4 * n];
            build(1, 0, n - 1);
        }

        private void build(int node, int start, int end) {
            if (start == end) {
                tree[node] = array[start];
                return;
            }

            int mid = (start + end) / 2;
            build(2 * node, start, mid);
            build(2 * node + 1, mid + 1, end);

            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }

        public int rangeSum(int left, int right) {
            if (left < 0 || right >= n || left > right) {
                throw new IllegalArgumentException("Invalid query range.");
            }

            return query(1, 0, n - 1, left, right);
        }

        private int query(int node, int start, int end, int left, int right) {
            // No overlap
            if (right < start || end < left) {
                return 0;
            }

            // Total overlap
            if (left <= start && end <= right) {
                return tree[node];
            }

            // Partial overlap
            int mid = (start + end) / 2;
            int leftSum = query(2 * node, start, mid, left, right);
            int rightSum = query(2 * node + 1, mid + 1, end, left, right);

            return leftSum + rightSum;
        }

        public void update(int index, int newValue) {
            if (index < 0 || index >= n) {
                throw new IllegalArgumentException("Invalid update index.");
            }

            array[index] = newValue;
            update(1, 0, n - 1, index, newValue);
        }

        private void update(int node, int start, int end, int index, int newValue) {
            if (start == end) {
                tree[node] = newValue;
                return;
            }

            int mid = (start + end) / 2;

            if (index <= mid) {
                update(2 * node, start, mid, index, newValue);
            } else {
                update(2 * node + 1, mid + 1, end, index, newValue);
            }

            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }

        public void displayArray() {
            System.out.println(Arrays.toString(array));
        }

        public void displayTreeArray() {
            System.out.println(Arrays.toString(tree));
        }
    }

    public static void main(String[] args) {
        int[] values = {12, 7, 9, 15, 6, 11, 10, 8};
        SegmentTree segmentTree = new SegmentTree(values);

        System.out.println("CO2 - Segment Tree Demonstration");
        System.out.println("--------------------------------");

        System.out.print("Original Array: ");
        segmentTree.displayArray();

        System.out.println("Range Sum [2, 6]: " + segmentTree.rangeSum(2, 6));
        System.out.println("Range Sum [0, 3]: " + segmentTree.rangeSum(0, 3));

        System.out.println("\nUpdating index 3 from 15 to 4...");
        segmentTree.update(3, 4);

        System.out.print("Updated Array : ");
        segmentTree.displayArray();

        System.out.println("Range Sum [2, 6] after update: " + segmentTree.rangeSum(2, 6));

        System.out.println("\nTime Complexity:");
        System.out.println("Build       : O(n)");
        System.out.println("Range Query : O(log n)");
        System.out.println("Point Update: O(log n)");
    }
}
