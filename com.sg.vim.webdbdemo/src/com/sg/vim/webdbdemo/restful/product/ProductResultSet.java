package com.sg.vim.webdbdemo.restful.product;

import java.util.List;

public class ProductResultSet {
    private int count;
    private List<Product> result;
    public void setCount(int count) {
        this.count = count;
    }
    public void setResult(List<Product> result) {
        this.result = result;
    }
    public int getCount() {
        return count;
    }
    public List<Product> getResult() {
        return result;
    }
    
    
}
