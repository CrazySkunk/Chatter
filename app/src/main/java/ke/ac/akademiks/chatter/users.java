package ke.ac.akademiks.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import ke.ac.akademiks.chatter.R;

public class users extends AppCompatActivity {
    ListView userList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        userList = findViewById(R.id.userList);
        noUsersText = findViewById(R.id.noUserText);

        pd = new ProgressDialog(users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://chatter-882f5.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                doOnSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("" + error);
            }
    });
        RequestQueue requestQueue = Volley.newRequestQueue(users.this);
        requestQueue.add(request);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(users.this,chat.class));
            }
        });
    }

    public void doOnSuccess(String string){
        try {
            JSONObject obj = new JSONObject(string);

            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()){
                key = i.next().toString();
                if (!key.equals(UserDetails.username)){
                    al.add(key);
                }
                totalUsers++;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        if (totalUsers <= 1){
            noUsersText.setVisibility(View.VISIBLE);
            userList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            userList.setVisibility(View.VISIBLE);
            userList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al));
        }
        pd.dismiss();
    }
}
