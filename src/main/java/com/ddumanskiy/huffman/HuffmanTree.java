package com.ddumanskiy.huffman;

import com.ddumanskiy.utils.BitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 10:00 AM
 */
public class HuffmanTree {

    public Node root;
    public Node currentBranch;
    public List<Node> branches;

    public HuffmanTree() {
        this.root = new Node();
        this.currentBranch = root;
        branches = new ArrayList<>();
        branches.add(root);
    }

    public void fillLevel() {
        int level = currentBranch.level();
        while (nextBranch().level() == level) {
            addNewBranch();
        }
    }

    public HuffmanTree addNewBranch() {
        Node branch = new Node();
        nextBranch().addChild(branch);
        branch.index = branches.size();
        branches.add(branch);
        return this;
    }

    public HuffmanTree addLeaf(int code) {
        Node leaf = new Node(code);
        nextBranch().addChild(leaf);
        return this;
    }

    public Node nextBranch() {
        if (currentBranch.isFilled()) {
            currentBranch = branches.get(currentBranch.index + 1);
        }
        return currentBranch;
    }

    public static class Node {
        public Node node0;
        public Node node1;
        public Node parent;
        public int index;
        public int code;

        //those 2 fields only for performance reasons. to avoid calculation on every block inside huffman.decode()
        public int first4Bits;
        public int last4Bits;

        protected Node() {
            this.code = -1;
        }

        public Node(int code) {
            this.code = code;
            this.first4Bits = BitUtil.first4Bits(code);
            this.last4Bits = BitUtil.last4Bits(code);
        }

        public boolean isFilled() {
            return node0 != null && node1 != null;
        }

        public boolean isLeaf() {
            return code > -1;
        }

        public void addChild(Node node) {
            if (node0 == null) {
                node0 = node;
            } else if (node1 == null) {
                node1 = node;
            } else {
                throw new IllegalStateException("Node full.");
            }
            node.parent = this;
        }

        public int level() {
            if (parent == null) {
                return 0;
            }
            return parent.level() + 1;
        }
    }
}
