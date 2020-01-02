package com.txtung.ailatrieuphu;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    //   private static final String BASE_URL =  "http://10.0.3.2:8000/api/"; // Genymotion
    private static final String BASE_URL =  "http://10.0.2.2:8000/api/";

    static String getJSONData(String uri, String method) {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        Uri builtURI = Uri.parse(BASE_URL + uri).buildUpon().build();

        try {

            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            jsonString = convertToString(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        Log.d("TEST", jsonString);
        return jsonString;
    }

    static String getJSONData(String uri, String method, Object[] nameParams, Object[] valueParams) {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        Uri.Builder builder =  Uri.parse(BASE_URL + uri).buildUpon();
        for(int i=0; i<nameParams.length; i++) {
            builder.appendQueryParameter(nameParams[i].toString(), valueParams[i].toString());
        }
        Uri builtURI = builder.build();

        try {

            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            jsonString = convertToString(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        Log.d(LOG_TAG, jsonString);
        return jsonString;
    }

    static String doRequest(String uri, String method, HashMap<String, String> params, String token) {
        HttpURLConnection urlConnection = null;
        String jsonString = null;
        Uri.Builder builder = Uri.parse(BASE_URL + uri).buildUpon();
        if(params != null) {
            for (Map.Entry<String, String> pa : params.entrySet()) {
                builder.appendQueryParameter(pa.getKey(), pa.getValue());
            }
        }
        Uri builtURI = builder.build();

        try {

            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);

            if(token != null) {
                // Dua token vao trong Header cua Request voi key Authorization
                urlConnection.setRequestProperty("Authorization", token);
            }

            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            jsonString = convertToString(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        Log.d(LOG_TAG, jsonString);
        return jsonString;
    }

    public static String postRequest(String uri, HashMap<String, String> postDataParams) {
        String jsonString = null;
        URL url;
        try {
            url = new URL(BASE_URL + uri);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Dua param vao body request
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                jsonString = convertToString(conn.getInputStream());
            } else {
                jsonString = "{'success': false}";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, jsonString);
        return jsonString;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        // hinh_anh=abcedffsf&ten_hinh=abc.jpg
        return result.toString();
    }
    static String convertToString(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (builder.length() == 0) {
            return null;
        }
        return builder.toString();
    }
}

