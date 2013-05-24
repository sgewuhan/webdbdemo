package com.sg.vim.webdbdemo.certificate;

import java.util.List;

public class CertificateResultSet {
    private int count;
    private List<Certificate> result;
    public void setCount(int count) {
        this.count = count;
    }
    public void setResult(List<Certificate> result) {
        this.result = result;
    }
    public int getCount() {
        return count;
    }
    public List<Certificate> getResult() {
        return result;
    }
    
    
}
