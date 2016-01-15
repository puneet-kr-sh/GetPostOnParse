package com.example.getpostonparse;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PostData extends AsyncTask<String,Void,String> {
    private JSONObject request;
    private Context context;
    private String requestMethod="POST";
    OnPostDataListener postDataListener;



    HttpURLConnection connection;
    ProgressDialog progress;

    int status;

    public PostData(JSONObject request,Context context) {
        this.request = request;
        this.postDataListener = (OnPostDataListener)context;
        this.context = context;

    }

    public PostData(JSONObject request,Context context,String requestMethod) {
        this.request = request;
        this.context = context;
        this.requestMethod = requestMethod;
        this.postDataListener = (OnPostDataListener)context;
    }
    public interface OnPostDataListener{
        public void onPostDataComplete(String result,int responseCode);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress=new ProgressDialog(context);
        progress.setMessage("Please ! Wait.. \n Data is being Uploaded.");
        progress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        return postToServer(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        postDataListener.onPostDataComplete(s,status);
        progress.cancel();
    }

    private String postToServer(String urlString) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url= new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("X-Parse-Application-Id", "cEsY9jNhtNLpohevWCF8OLO7fw7FHDKZRNH8efym");
            connection.setRequestProperty("X-Parse-REST-API-Key", "D1JCIVzahIFFhJ3B7P7ntDHRFfNb8decCbEe49dG");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(300000);
            connection.setDefaultUseCaches(false);
            connection.connect();

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(request.toString());
            writer.flush();
            writer.close();

            status = connection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }

        return sb.toString();
    }
}
