/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asposewords.restfulapi;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author abdulbasit.ali
 */
public class HttpClientClass {

    public String httpPostMethod(String fileName) {
        HttpClient client = new DefaultHttpClient();
        List<NameValuePair> nameValuePairs;
        HttpPost post = null;
        String line = "";
        post = new HttpPost("http://localhost:8080/AsposeWords/rest/words/protection");

        try {
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("src", fileName));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            while ((line = rd.readLine()) != null) {
                line=""+line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public String httpPutMethod(String converstionFormat, String fileName) throws UnsupportedEncodingException {
        HttpClient client = new DefaultHttpClient();
        HttpPut put = null;
        String line = "";
        put = new HttpPut("http://localhost:8080/AsposeWords/rest/words/convert");
        put.addHeader("content-type", "text/plain");
        put.addHeader("src", fileName);
        put.addHeader("format", converstionFormat);
        try {
            HttpResponse response = client.execute(put);
            line = "" + response.getStatusLine().getStatusCode();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HttpClientClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClientClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }

    public String httpGetMethod(String fileName) {
        String line = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8080/AsposeWords/rest/words/protect");
        try {
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while ((line = rd.readLine()) != null) {
                line += line;
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpClientClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }
}
