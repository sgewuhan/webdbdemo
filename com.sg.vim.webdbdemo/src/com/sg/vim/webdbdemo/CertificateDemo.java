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

    // �ϸ�֤��ѯ�ӿ�
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
        //���÷��ʵ�userid�Լ�password�������������������á������֪���û��������룬����ͨ��ע��ķ�ʽ����û���������
        //vimϵͳĿǰ�Ѿ������������û������ݿ�ķ���Ȩ�ޡ�ע���û�����ʹ�ñ��ӿڡ�
        parameter.put(PARA_UID, "zhonghua");
        parameter.put(PARA_PSW, "1");
        //�������ݿ�ͼ���
        parameter.put(PARA_DB, "appportal");
        parameter.put(PARA_COL, "certificate");
        
        //���ò�ѯ����
        StringBuffer cond = new StringBuffer();
        cond.append("{");
        cond.append(CertificateFields.Veh_Clxh+":\"BJ6400AJZ1A1\"");//��ѯ��������ΪDP�����ݣ��������ʹ��,�ָ�
        cond.append("}");
        parameter.put(PARA_QUERY, cond.toString());
        
        //���÷����ֶΣ���������ý�����ȫ���ֶ�
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
        
        //��������
//        cond = new StringBuffer();
//        cond.append("{");
//        cond.append(CertificateFields.Veh_Clxh+":1");//1����-1���򣬶������ʹ��,�ָ�
//        cond.append("}");
//        parameter.put(PARA_SORT, cond.toString());
        
        //���ò�ѯ���ؼ�¼����������ʼ������ͨ����������������ʵ�ַ�ҳ�Ĺ���
        parameter.put(PARA_SKIP, ""+0);
        parameter.put(PARA_LIMIT, ""+2);

        
        return parameter;
    }

    /**
     * @param urlCert ���ʵ�ַ
     * @param parameters ����
     * @return ����ַ���
     * @throws IOException
     */
    private static ResultSet getResultSet(String url, Map<String, String> parameters) throws Exception {
        // ����HttpClient��ʵ��
        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(url);
        //���ò���
        Iterator<String> iter = parameters.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            method.addParameter(key, parameters.get(key));
        }
        
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
            return g.fromJson(json , ResultSet.class);
        } finally {
            // �ͷ�����
            method.releaseConnection();
        }
    }
}
