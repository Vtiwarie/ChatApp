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
                Response response = handleRequest(requests[0]);
                return response;
            }
        }.execute(request);
    }

    public abstract Response handleRequest(Request request);

    public abstract void handleResponse(Response response);

    public abstract static class Request {
        public String url;
        public String method;
        public static final String POST = "POST";
        public static final String GET = "GET";

        public Request(String theUrl, String theMethod) {
            url = theUrl;
            method = theMethod;
        }
    }

    public static abstract class Response {
        public String connectionTime;

        Response(String theConnectionTime) {
            connectionTime = theConnectionTime;
        }
    }

}
