package com.ddumanskiy.segments;

import com.ddumanskiy.huffman.HuffmanTree;

/**
 * User: ddumanskiy
 * Date: 8/13/2014
 * Time: 9:36 AM
 */
public class DHTTable {

    private int id;
    
    private int tableClass;

    private HuffmanTree tree;

    public DHTTable(int tableClass, int id, HuffmanTree huffmanTree) {
        this.tableClass = tableClass;
        this.id = id;
        this.tree = huffmanTree;
    }

    public int getId() {
        return id;
    }

    public int getTableClass() {
        return tableClass;
    }

    public HuffmanTree getTree() {
        return tree;
    }
}
