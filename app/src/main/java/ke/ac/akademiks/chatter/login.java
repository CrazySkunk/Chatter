package ke.ac.akademiks.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.Registrar;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {
    TextView registerUser;
    EditText username,email,password;
    Button loginBtn;
    String user,pass,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerUser = findViewById(R.id.registerButton);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Registrar.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                mail = email.getText().toString();
                pass = password.getText().toString();
                if (user.equals("")){
                    username.setError("Can't be blank");
                }
                else if (pass.equals("")){
                    password.setError("Can't be blank");
                }
                else{
                    String url = "https://chatter-882f5.firebaseio.com/users.json";
                    final ProgressDialog pd = new ProgressDialog(login.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Null")) {
                                Toast.makeText(login.this, "User not found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(response);

                                    if (!obj.has(user)) {
                                        Toast.makeText(login.this, "User not found", Toast.LENGTH_LONG).show();
                                    } else if (obj.getJSONObject(user).getString("password").equals(pass)) {
                                        UserDetails.username = user;
                                        UserDetails.email = mail;
                                        UserDetails.password = pass;
                                        startActivity(new Intent(login.this, users.class));
                                    } else {
                                        Toast.makeText(login.this, "Incorrect Password", Toast.LENGTH_LONG);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            pd.dismiss();
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("" + error);
                            pd.dismiss();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(login.this);
                    requestQueue.add(request);
                }
                }
            });
        }
    }

