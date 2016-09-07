package com.apppartner.androidprogrammertest.classes;

import android.os.AsyncTask;

/**
 * Created by Vishaan on 9/2/2016.
 * <p/>
 * Class created in order to send requests to and from a server via
 * network calls.
 */
public abstract class AsyncClient {

    public void makeRequest(Request request) {

        new AsyncTask<Request, Void, Response>() {
            @Override
            protected void onPostExecute(AsyncClient.Response response) {
                handleResponse(response);
            }

            @Override
            protected AsyncClient.Response doInBackground(AsyncClient.Request... requests) {
                return handleRequest(requests[0]);
            }
        }.execute(request);
    }

    public abstract Response handleRequest(Request request);

    public abstract void handleResponse(Response response);

    /**
     * Template class used to make requests over the network.
     * This is a very basic request object.
     */
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

    /**
     * Template class used for the purpose of being extended
     * in order to handle the response from a network request.
     */
    public static abstract class Response {
        public String connectionTime;

        Response(String theConnectionTime) {
            connectionTime = theConnectionTime;
        }
    }

}
