package BinaryTree;

import java.util.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {
    private Node root;

    public BinaryTree(){ root = null; }

    public boolean isEmpty() { return root == null; }

    public int count() { return count(root); }

    public int count(Node node) {
        if (node == null){
            return 0;
        }

        int counter = 1;
        counter += count(node.getLeft());
        counter += count(node.getRight());
        return counter;
    }

    public boolean insert(int data){
        if (contains(data)) {
            return false;
        }

        if (this.root == null){
            this.root = new Node(data);
            return true;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);

        while (!queue.isEmpty()){
            Node node = queue.poll();
            if (node.getLeft() == null){
                node.setLeft(new Node(data));
                break;
            } else {
                queue.add(node.getLeft());
            }

            if  (node.getRight() == null){
                node.setRight(new Node(data));
                break;
            } else {
                queue.add(node.getRight());
            }
        }

        return true;
    }
    
    public void remove(){
        if (isEmpty()) return;

        Node[] nodes = levelOrder();
        if (nodes.length == 1) {
            this.root = null;
            return;
        }

        Node lastNode = nodes[nodes.length - 1];
        for (Node node : nodes){
            if (node.getLeft() == lastNode){
                node.setLeft(null);
                break;
            }
            
            if (node.getRight() == lastNode){
                node.setRight(null);
                break;
            }
        }
    }

    public Node[] levelOrder(){
        if (this.root == null){
            return new Node[0];
        }

        ArrayList<Node> list = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            Node newNode = queue.poll();
            list.add(newNode);
            if (newNode.getLeft() != null) {
                queue.add(newNode.getLeft());
            }
            if (newNode.getRight() != null) {
                queue.add(newNode.getRight());
            }
        }
        return list.toArray(new Node[0]);
    }

    public String displayLevelOrder() {
        if (isEmpty()){
            return "Tree is empty";
        }

        Node[] nodes = levelOrder();
        StringBuilder temp = new StringBuilder();
        for (Node node : nodes) {
            temp.append(node.getData()).append(" ");
        }

        return temp.toString();
    }

    public String displayInOrder() {
        if (this.root == null) {
            return "Tree is empty";
        }

        StringBuilder temp = new StringBuilder();
        Stack<Node> stack = new Stack<>();
        Node node = this.root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.getLeft();
            } else {
                Node value = stack.pop();
                temp.append(value.getData()).append(" ");
                node = value.getRight();
            }
        }

        return temp.toString();
    }

    public String displayPreOrder() {
        if (this.root == null) {
            return "Tree is empty";
        }

        StringBuilder temp = new StringBuilder();
        Stack<Node> stack =  new Stack<>();
        Node node = this.root;
        Node prevNode;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                temp.append(node.getData()).append(" ");
                stack.push(node);
                node = node.getLeft();
            } else {
                prevNode = stack.pop();
                node = prevNode.getRight();
            }
        }
        return temp.toString();
    }

    public String displayPostOrder() {
        if (this.root == null) {
            return "Tree is empty";
        }

        StringBuilder temp = new StringBuilder();
        Stack<Node> stack = new Stack<>();
        Node node = this.root;
        Node prevNode = null;
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.peek();

            if (prevNode == null 
                    || prevNode.getLeft() == node
                    || prevNode.getRight() == node) {
                
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                } else if (node.getRight() != null) {
                    stack.push(node.getRight());
                } else {
                    stack.pop();
                    temp.append(node.getData()).append(" ");
                }
            } else if (node.getLeft() == prevNode) {
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                } else {
                    stack.pop();
                    temp.append(node.getData()).append(" ");
                }
            }  else if (node.getRight() == prevNode) {
                stack.pop();
                temp.append(node.getData()).append(" ");
            }

            prevNode = node;
        }

        return temp.toString();
    }

    public boolean contains(int val) {
        return !isEmpty() && contains(root, val);
    }

    public boolean contains(Node node, int val) {
        return node.getData() == val    
                || node.getLeft() != null && contains(node.getLeft(), val)
                || node.getRight() != null && contains(node.getRight(), val);
    }

    public int getHeight() {
        if (isEmpty()) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        int height = 0;
        while (!queue.isEmpty()) {
            int nodeCount = queue.size();
            height++;

            while (nodeCount > 0) {
                Node node = queue.poll();
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }

                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }

                nodeCount--;
            }
        }

        return height;
    }
    
    public int getDepth() {
        int height = getHeight();
        return height == 0 ? 0 : height - 1;
    }

    public String displayLeaves() {
        if (isEmpty() || count() == 1) {
            return "";
        }

        Node[] nodes = levelOrder();
        StringBuilder temp = new StringBuilder();
        for (Node node : nodes) {
            if (node.getLeft() == null && node.getRight() == null) {
                temp.append(node.getData()).append(" ");
            }
        }

        return temp.toString();
    }

    public String displayInternalNodes() {
        System.out.println("Count: " + count());
        if (isEmpty()) {
            return "";
        }

        Node[] nodes = levelOrder();
        StringBuilder temp = new StringBuilder();
        for (Node node : nodes) {
            if (node != this.root 
                    && (node.getLeft() != null || node.getRight() != null)){
                temp.append(node.getData()).append(" ");
            }
        }

        return temp.toString();
    }

    public void cut() {
        this.root = null;
    }

    public String printInOrder() {
        return null;
    }
}
