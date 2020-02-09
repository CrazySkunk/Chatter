package ke.ac.akademiks.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class message_area extends AppCompatActivity {
    EditText message;
    ImageView send;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_area);

        this.message = findViewById(R.id.messageArea);
        this.send = findViewById(R.id.sendButton);
        text = message.getText().toString();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(text);
            }
        });

    }

    public void sendMessage(String text) {
        Intent send = new Intent(this,chat.class);
        send.putExtra(text,0);
        startActivity(send);
    }
}
