package com.netflix.ribbon.examples;

import java.util.concurrent.Future;

import com.netflix.client.BufferedResponseCallback;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.httpasyncclient.RibbonHttpAsyncClient;

public class AsyncClientSampleApp {
    
    public static void main(String[] args) throws Exception {
        RibbonHttpAsyncClient client = new RibbonHttpAsyncClient();
        HttpRequest request = HttpRequest.newBuilder().uri("http://www.google.com/").build();
        Future<HttpResponse> future = client.execute(request, new BufferedResponseCallback<HttpResponse>() {
            @Override
            public void failed(Throwable e) {
                System.err.println("failed: " + e);
            }
            
            @Override
            public void completed(HttpResponse response) {
                System.out.println("Get response: " + response.getStatus());
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void cancelled() {
                System.err.println("cancelled");
            }
        });
        HttpResponse response = future.get();
        System.out.println("Status from response " + response.getStatus());
    }
}
