import java.util.*;

/**
 * CO1 Topic: Binary Search Tree and AVL Tree
 * This program demonstrates insertion, inorder traversal, searching,
 * height calculation, and AVL balancing using rotations.
 */
public class CO1_BST_AVL {
    static class Node {
        int key;
        int height;
        Node left, right;

        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    static class AVLTree {
        private Node root;

        private int height(Node node) {
            return node == null ? 0 : node.height;
        }

        private int balanceFactor(Node node) {
            return node == null ? 0 : height(node.left) - height(node.right);
        }

        private void updateHeight(Node node) {
            if (node != null) {
                node.height = 1 + Math.max(height(node.left), height(node.right));
            }
        }

        private Node rotateRight(Node y) {
            Node x = y.left;
            Node t2 = x.right;

            x.right = y;
            y.left = t2;

            updateHeight(y);
            updateHeight(x);

            return x;
        }

        private Node rotateLeft(Node x) {
            Node y = x.right;
            Node t2 = y.left;

            y.left = x;
            x.right = t2;

            updateHeight(x);
            updateHeight(y);

            return y;
        }

        public void insert(int key) {
            root = insert(root, key);
        }

        private Node insert(Node node, int key) {
            if (node == null) {
                return new Node(key);
            }

            if (key < node.key) {
                node.left = insert(node.left, key);
            } else if (key > node.key) {
                node.right = insert(node.right, key);
            } else {
                return node; // duplicate keys are not inserted
            }

            updateHeight(node);
            int balance = balanceFactor(node);

            // LL case
            if (balance > 1 && key < node.left.key) {
                return rotateRight(node);
            }

            // RR case
            if (balance < -1 && key > node.right.key) {
                return rotateLeft(node);
            }

            // LR case
            if (balance > 1 && key > node.left.key) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }

            // RL case
            if (balance < -1 && key < node.right.key) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }

            return node;
        }

        public boolean search(int key) {
            return search(root, key);
        }

        private boolean search(Node node, int key) {
            if (node == null) {
                return false;
            }

            if (key == node.key) {
                return true;
            }

            return key < node.key ? search(node.left, key) : search(node.right, key);
        }

        public void inorder() {
            inorder(root);
            System.out.println();
        }

        private void inorder(Node node) {
            if (node == null) {
                return;
            }

            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }

        public void preorder() {
            preorder(root);
            System.out.println();
        }

        private void preorder(Node node) {
            if (node == null) {
                return;
            }

            System.out.print(node.key + " ");
            preorder(node.left);
            preorder(node.right);
        }

        public int getTreeHeight() {
            return height(root);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        int[] values = {40, 20, 60, 10, 30, 50, 70, 25, 35, 5, 15, 27, 45};

        for (int value : values) {
            tree.insert(value);
        }

        System.out.println("CO1 - BST and AVL Tree Demonstration");
        System.out.println("------------------------------------");

        System.out.print("Inorder Traversal  : ");
        tree.inorder();

        System.out.print("Preorder Traversal : ");
        tree.preorder();

        System.out.println("Height of AVL Tree : " + tree.getTreeHeight());

        int searchKey = 27;
        if (tree.search(searchKey)) {
            System.out.println(searchKey + " found in the AVL Tree.");
        } else {
            System.out.println(searchKey + " not found in the AVL Tree.");
        }

        System.out.println("\nTime Complexity:");
        System.out.println("Search   : O(log n)");
        System.out.println("Insert   : O(log n)");
        System.out.println("Traversal: O(n)");
    }
}
