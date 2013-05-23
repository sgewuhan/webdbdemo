package com.sg.vim.webdbdemo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.Gson;

public class CertificateDemo {
    
    public static final String URL_CERT="http://localhost/find";
    
    private static final String PARA_UID = "userid";
    
    private static final String PARA_PSW = "password";

    private static final String PARA_DB = "db";
    
    private static final String PARA_COL = "collection";

    private static final String PARA_QUERY = "query";

    private static final String PARA_FIELDS = "fields";
    
    private static final String PARA_SORT = "sort";
    
    private static final String PARA_SKIP = "skip";

    private static final String PARA_LIMIT = "limit";

    // 合格证查询接口
    public static void main(String[] args) {
        
        
        Map<String, String> parameters = getQueryParameter();
        System.out.println(parameters);
        try {
            ResultSet rs = getResultSet(URL_CERT,parameters);
            int resultCount = rs.getCount();
            System.out.println(resultCount);
            List<Certificate> result = rs.getResult();
            for(int i = 0 ; i<result.size();i++){
                System.out.println(result.get(i).getVeh_Clxh());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private static Map<String, String> getQueryParameter() {
        Map<String, String> parameter = new HashMap<String,String>();
        //设置访问的userid以及password以下两个参数必须设置。如果不知道用户名和密码，可以通过注册的方式获得用户名和密码
        //vim系统目前已经开放了自由用户对数据库的访问权限。注册用户即可使用本接口。
        parameter.put(PARA_UID, "zhonghua");
        parameter.put(PARA_PSW, "1");
        //设置数据库和集合
        parameter.put(PARA_DB, "appportal");
        parameter.put(PARA_COL, "certificate");
        
        //设置查询条件
        StringBuffer cond = new StringBuffer();
        cond.append("{");
        cond.append(CertificateFields.Veh_Clxh+":\"BJ6400AJZ1A1\"");//查询车辆类型为DP的数据，多个条件使用,分割
        cond.append("}");
        parameter.put(PARA_QUERY, cond.toString());
        
        //设置返回字段，如果不设置将返回全部字段
        cond = new StringBuffer();
        cond.append("{");
        String[] fields = CertificateFields.getFields();
        for(int i=0;i<fields.length;i++){
            if(i!=0){
                cond.append(","+fields[i]+":1");
            }else{
                cond.append(fields[i]+":1");
            }
        }
        cond.append("}");
        parameter.put(PARA_FIELDS, cond.toString());
        
        //设置排序
//        cond = new StringBuffer();
//        cond.append("{");
//        cond.append(CertificateFields.Veh_Clxh+":1");//1升序，-1降序，多个条件使用,分割
//        cond.append("}");
//        parameter.put(PARA_SORT, cond.toString());
        
        //设置查询返回记录的条数和起始行数，通过这两个参数可以实现分页的功能
        parameter.put(PARA_SKIP, ""+0);
        parameter.put(PARA_LIMIT, ""+2);

        
        return parameter;
    }

    /**
     * @param urlCert 访问地址
     * @param parameters 参数
     * @return 结果字符串
     * @throws IOException
     */
    private static ResultSet getResultSet(String url, Map<String, String> parameters) throws Exception {
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(url);
        //设置参数
        Iterator<String> iter = parameters.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            method.addParameter(key, parameters.get(key));
        }
        
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
            return g.fromJson(json , ResultSet.class);
        } finally {
            // 释放连接
            method.releaseConnection();
        }
    }
}
