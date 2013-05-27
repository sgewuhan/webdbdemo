package com.sg.vim.webdbdemo.restful.product;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.google.gson.Gson;

public class ProductDemo {
    
    public static final String URL="http://localhost/vim/product/info/";
    

    
    // Product查询接口
    public static void main(String[] args) {
        
        
        try {
            ProductResultSet result = getResultSet(URL);
            for(int i = 0 ; i<result.getResult().size();i++){
                System.out.println(((Product)result.getResult().get(i)).getNoticeCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }


    /**
     * @param urlCert 访问地址
     * @param parameters 参数
     * @return 结果字符串
     * @throws IOException
     */
    private static ProductResultSet getResultSet(String url) throws Exception {
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(url);
        
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                throw new Exception("Method failed: " + method.getStatusLine());
            }
            // 读取内容
            byte[] responseBody = method.getResponseBody();
            
            Gson g = new Gson();
            String json = new String(responseBody,"utf8");
            System.out.println(json);
            return g.fromJson(json , ProductResultSet.class);
        } finally {
            // 释放连接
            method.releaseConnection();
        }
    }
}
