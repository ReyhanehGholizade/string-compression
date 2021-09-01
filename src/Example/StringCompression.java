//package Example;

import java.util.*;

/**
 * Node class
 */
class Node {
    char character;
    int key;
    Node right;
    Node left;

    public Node(char character, int data, Node right, Node left) {
        this.character = character;
        this.key = data;
        this.right = right;
        this.left = left;
    }

    public Node() {
    }
}

class MyComparator implements Comparator<Node> {
    public int compare(Node x, Node y) {
        return x.key - y.key;
    }
}

class StringCompression {

    /**
     * gets a string and make a huffman tree
     * @param input input string
     * @return returns an integer representing minimum number of bits
     */
    public static int compression(String input) {
        ArrayList<Character> list = new ArrayList<>();
        ArrayList<Integer> frequency = new ArrayList<>();
        Node root = null;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ')
                continue;

            else {
                if (list.contains(input.charAt(i))) {
                    continue;
                } else {
                    list.add(input.charAt(i));
                    int counter = 0;
                    for (int j = 0; j < input.length(); j++) {
                        if (input.charAt(j) == input.charAt(i))
                            counter++;
                    }
                    frequency.add(counter);
                }
            }
        }

        //we need a priority queue to keep nodes in it
        PriorityQueue<Node> queue = new PriorityQueue<Node>(list.size(), new MyComparator());

        //make nodes of characters and their frequency and add them to queue
        for (int i = 0; i < list.size(); i++) {
            Node node = new Node();

            node.character = list.get(i);
            node.key = frequency.get(i);

            node.left = null;
            node.right = null;

            queue.add(node);
        }

        //building the huffman tree
        while (queue.size() > 1) {

            Node x = queue.peek();
            queue.poll();

            Node y = queue.peek();
            queue.poll();

            Node internalNode = new Node();

            //internal node's key should be the children's key sum
            assert y != null;
            internalNode.key = x.key + y.key;
            internalNode.character = '-';

            internalNode.left = x;
            internalNode.right = y;

            root = internalNode;

            // add this node to the priority queue.
            queue.add(internalNode);
        }
        System.out.println(traversTree(root));
        return traversTree(root);
    }

    /**
     * a level order traversal for finding internal nodes and calculate their key's sum
     * @param node the root of huffman tree
     * @return returns an integer number which represents minimum number of bits
     */
    static int traversTree(Node node) {
        Queue<Node> nodeQueue = new LinkedList<Node>();
        int counter = 0;

        nodeQueue.add(node);

        while (!nodeQueue.isEmpty()) {
            Node current = nodeQueue.peek();
            nodeQueue.remove();

            boolean isInternal = false;

            if (current.left != null) {
                isInternal = true;
                nodeQueue.add(current.left);
            }

            if (current.right != null) {
                isInternal = true;
                nodeQueue.add(current.right);
            }

            if (isInternal)
                counter += current.key;
        }
        return counter;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        if (string.length() <= 100000)
        compression(string);
    }

}
