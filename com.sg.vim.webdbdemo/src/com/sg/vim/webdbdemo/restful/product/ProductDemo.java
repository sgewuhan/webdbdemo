package com.sg.vim.webdbdemo.restful.product;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.google.gson.Gson;

public class ProductDemo {
    
    public static final String URL="http://localhost/vim/product/info/";
    

    
    // Product��ѯ�ӿ�
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
     * @param urlCert ���ʵ�ַ
     * @param parameters ����
     * @return ����ַ���
     * @throws IOException
     */
    private static ProductResultSet getResultSet(String url) throws Exception {
        // ����HttpClient��ʵ��
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(url);
        
        try {
            // ִ��getMethod
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                throw new Exception("Method failed: " + method.getStatusLine());
            }
            // ��ȡ����
            byte[] responseBody = method.getResponseBody();
            
            Gson g = new Gson();
            String json = new String(responseBody,"utf8");
            System.out.println(json);
            return g.fromJson(json , ProductResultSet.class);
        } finally {
            // �ͷ�����
            method.releaseConnection();
        }
    }
}
