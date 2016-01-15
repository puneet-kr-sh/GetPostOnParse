package com.example.getpostonparse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
*/

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PostData.OnPostDataListener, GetData.OnGetDataListener {
    String id;
    String serverURL = "https://api.parse.com/1/classes";
    String tableName,tableObjectId;
    String strReq;
    TextView resultView;
    JSONObject jsonRequestObject;
    EditText request,objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Parse.initialize(this, "cEsY9jNhtNLpohevWCF8OLO7fw7FHDKZRNH8efym", "hZ4bncMFHEvaBL8LolWfaA0j4dYp4mKfPNAbShjd");
        setContentView(R.layout.activity_main);
        final TextView urlView = (TextView)findViewById(R.id.urlView);
        request = (EditText)findViewById(R.id.request);
        objectId=(EditText)findViewById(R.id.object_id);
        resultView= (TextView)findViewById(R.id.result);

        Button insert = (Button)findViewById(R.id.insert);
        Button read = (Button)findViewById(R.id.read);
        Button update = (Button)findViewById(R.id.update);
        Button delete = (Button)findViewById(R.id.delete);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView.setText("");
                strReq = request.getText().toString();
                tableName="/UserTable";
                String url = serverURL + tableName;
                urlView.setText("URL: "+url);
                getJSONRequest();
                new PostData(jsonRequestObject,MainActivity.this).execute(url);


               /* final ParseObject parseObject = new ParseObject("post");
                parseObject.put("name",request.getText().toString());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            id=parseObject.getObjectId();
                            Toast.makeText(getApplicationContext(),"SAVED Successfully !!",Toast.LENGTH_LONG).show();
                            resultView.setText("INSERTED : " + e.getMessage());
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not Saved :"+e+"\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                            result.setText("NOT INSERTed : " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });*/
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName="/UserTable";
                tableObjectId="/"+objectId.getText().toString();
                String url = serverURL + tableName +tableObjectId;
                urlView.setText("URL :"+url);
                new GetData(MainActivity.this).execute(url);

                /*ParseQuery<ParseObject> query= ParseQuery.getQuery("post");
                query.getInBackground(id, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            resultView.setText(object.get("name").toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Object Not Found to read : " + e.getMessage(), Toast.LENGTH_LONG).show();
                            resultView.setText("!READ getInBackgrground Error: "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });*/

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView.setText("");
                strReq = request.getText().toString();
                tableName="/UserTable";
                tableObjectId="/"+objectId.getText().toString();
                String url = serverURL + tableName +tableObjectId;
                urlView.setText("URL: "+url);
                getJSONRequest();
                new PostData(jsonRequestObject,MainActivity.this,"PUT").execute(url);

                /*ParseQuery<ParseObject> query= ParseQuery.getQuery("post");
                query.getInBackground(id, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(e==null){
                            object.put("new_name",request.getText().toString());
                            object.put(" name", result.getText().toString());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(), "Updated new old", Toast.LENGTH_LONG).show();
                                        resultView.setText("UPDATED : " + e.getMessage());
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Not Updated :"+e.getMessage(), Toast.LENGTH_LONG).show();
                                        resultView.setText("! UPDATE - saveInBackground Error: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        else {
                            Toast.makeText(getApplicationContext(), "Object Not Found in getInBackground to UPDATE:"+e.getMessage(), Toast.LENGTH_LONG).show();
                            resultView.setText("! UPDATE - getInBackground Error : "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });*/
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName="/UserTable";
                tableObjectId="/"+objectId.getText().toString();
                String url = serverURL + tableName +tableObjectId;
                urlView.setText("URL :"+url);
                new GetData(MainActivity.this,"DELETE").execute(url);
                /*ParseQuery<ParseObject> query= ParseQuery.getQuery("post");
                query.getInBackground(id, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(e==null){
                            object.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e==null){
                                        Toast.makeText(getApplicationContext(), "Deleted :", Toast.LENGTH_LONG).show();
                                        resultView.setText("DELETED : " + e.getMessage());
                                        e.printStackTrace();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Can't be deleted :"+e.getMessage(), Toast.LENGTH_LONG).show();
                                        resultView.setText("! Delete - deleteInBackground Error : "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        else {
                            Toast.makeText(getApplicationContext(), "Object Not Found to delete :"+e.getMessage(), Toast.LENGTH_LONG).show();
                            resultView.setText("! DELETE - getInBackground Error: "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });*/
            }
        });




    }



    @Override
    public void onGetDataComplete(String result, int responseCode) {
        resultView.setText(result+" \n\n\t Status : "+responseCode);
    }

    @Override
    public void onPostDataComplete(String result, int responseCode) {
        resultView.setText(result+" \n\n\t Status : "+responseCode);
    }

    public JSONObject getJSONRequest() {
        jsonRequestObject = new JSONObject();
        try {
            jsonRequestObject.put("name",strReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonRequestObject;
    }

}
