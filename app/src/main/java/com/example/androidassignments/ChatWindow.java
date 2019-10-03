package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    ArrayList<String> messages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate ");

        ListView chatView =  findViewById(R.id.chatView);
        Button send = findViewById(R.id.button4);
        final EditText textField = findViewById(R.id.editText3);
        final ChatAdapter messageAdapter = new ChatAdapter(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messages.add(textField.getText().toString());
                messageAdapter.notifyDataSetChanged();
                textField.setText("");
            }
        });


        chatView.setAdapter(messageAdapter);
    }

    private class ChatAdapter extends ArrayAdapter<String>{

         public ChatAdapter(Context ctx){
    super(ctx,0);
        }
        @Override
        public int getCount()
        {
            return messages.size();
        }
        @Override
        public String getItem(int position){
        return messages.get(position);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup Parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }
            else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));

    return result;
        }


    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
}
