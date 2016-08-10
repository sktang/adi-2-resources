package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        BinaryTree binaryTree = new BinaryTree(5);
        binaryTree.insert(1);
        binaryTree.insert(3);
        binaryTree.insert(12);
        binaryTree.insert(52);
        binaryTree.insert(39);

        if(binaryTree.depthFirstIterative(1)){
            System.out.println("Tree contains 1");
        } else {
            System.out.println("Tree does not contains 1");
        }

        if(binaryTree.depthFirstIterative(39)){
            System.out.println("Tree contains 39");
        } else {
            System.out.println("Tree does not contains 39");
        }

        if(binaryTree.depthFirstIterative(2)){
            System.out.println("Tree contains 2");
        } else {
            System.out.println("Tree does not contains 2");
        }

        if(binaryTree.depthFirstIterative(41)){
            System.out.println("Tree contains 41");
        } else {
            System.out.println("Tree does not contains 41");
        }

    }
}
