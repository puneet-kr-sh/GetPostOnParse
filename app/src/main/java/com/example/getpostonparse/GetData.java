package com.example.getpostonparse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetData extends AsyncTask<String, Void, String > {
    private Context context;
    private String requestMethod="GET";
    OnGetDataListener getDataListener;

    HttpURLConnection connection;
    ProgressDialog progress;
    int status;

    public GetData(Context context) {
        this.context = context;
        this.getDataListener=(OnGetDataListener)context;
    }

    public GetData(Context context, String requestMethod) {
        this.context = context;
        this.requestMethod=requestMethod;
        this.getDataListener=(OnGetDataListener)context;
    }

    public interface OnGetDataListener{
        public void onGetDataComplete(String result, int responseCode);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress=new ProgressDialog(context);
        progress.setMessage("Please ! Wait.. \n Data is being Downloaded...");
        progress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        return getFromServer(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progress.cancel();
        getDataListener.onGetDataComplete(s,status);
    }

    private String getFromServer(String urlString) {
        StringBuilder sb = new StringBuilder();

        try{
            URL url = new URL(urlString);
            connection= (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(50000);
            connection.setRequestProperty("X-Parse-Application-Id", "cEsY9jNhtNLpohevWCF8OLO7fw7FHDKZRNH8efym");
            connection.setRequestProperty("X-Parse-REST-API-Key", "D1JCIVzahIFFhJ3B7P7ntDHRFfNb8decCbEe49dG");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            status = connection.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = reader.readLine())!=null){
                sb.append(line);
               // sb.append("\n\n\n\n\t");
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
                    //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        return sb.toString();
    }

}
