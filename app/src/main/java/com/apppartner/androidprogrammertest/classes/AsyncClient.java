package com.apppartner.androidprogrammertest.classes;

import android.os.AsyncTask;

/**
 * Created by Vishaan on 9/2/2016.
 */
public abstract class AsyncClient {

    public AsyncClient() {

    }

    public void makeRequest(Request request) {

       new AsyncTask<Request, Void, Response>() {
            @Override
            protected void onPostExecute(AsyncClient.Response response) {
                handleResponse(response);
            }

            @Override
            protected AsyncClient.Response doInBackground(AsyncClient.Request... requests) {
                long connectionStart = System.currentTimeMillis();
                Response response = handleRequest(requests[0]);
                response.connecitonTime = System.currentTimeMillis() - connectionStart;
                return response;
            }
        }.execute(request);
    }

    public abstract Response handleRequest(Request request);

    public abstract void handleResponse(Response response);

    public static class Request {
        public String url;
        public String method;
        public static final String POST = "POST";
        public static final String GET = "GET";

        public Request(String theUrl, String theMethod) {
            url = theUrl;
            method = theMethod;
        }
    }

    public static class Response {
        public String responseBody;
        public int responseCode;
        public long connecitonTime;

        public Response(String theResponseBody, int theResponseCode) {
            responseBody = theResponseBody;
            responseCode = theResponseCode;
        }
    }

}
