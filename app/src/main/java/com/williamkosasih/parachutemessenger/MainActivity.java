package com.williamkosasih.parachutemessenger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements RoomListener {

    private String scaleDroneChannelId = "P2dKWgAAYrmNVSPM";
    private String scaleDroneRoomName = "observable-room";
    private String debugTag = "ParachuteMessenger";

    private EditText myUserMessageEditText;
    private ImageButton myUserMessageSendButton;
    private ListView myUserChatListView;

    private MessageAdapter messageAdapter;

    private Scaledrone scaledrone;

    private String getRandomColorHex() {
        String colorHex = "#";
        Random randy = new Random();

        while (colorHex.length() < 7) {
            colorHex += (Integer.toHexString(randy.nextInt()));
        }
        return colorHex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myUserMessageEditText = findViewById(R.id.userMessageEditText);
        myUserMessageSendButton = findViewById(R.id.userMessageSendButton);
        myUserChatListView = findViewById(R.id.chatListView);

        messageAdapter = new MessageAdapter(this);
        myUserChatListView.setAdapter(messageAdapter);

        myUserMessageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = myUserMessageEditText.getText().toString();
                if (userMessage.length() > 0) {
                    scaledrone.publish("observable-room", userMessage);
                    myUserMessageEditText.getText().clear();
                }
            }
        });

        MemberClass memberClass = new MemberClass("TestUser", getRandomColorHex());

        scaledrone = new Scaledrone(scaleDroneChannelId, memberClass);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                Log.d(debugTag, "Successfully connected to scaledrone chat room");
                scaledrone.subscribe(scaleDroneRoomName, MainActivity.this);

            }

            @Override
            public void onOpenFailure(Exception ex) {
                Log.e(debugTag, "Failed to open scaledrone chat rooom!");
            }

            @Override
            public void onFailure(Exception ex) {
                Log.e(debugTag, "Failed to connect to scaledrone chat rooom!");
            }

            @Override
            public void onClosed(String reason) {
                Log.e(debugTag, "Connection to scaledrone chat room closed - reason : " + reason);

            }
        });
    }

    @Override
    public void onOpen(Room room) {
        Log.d(debugTag, "succesfully oppened chat room...");
    }

    @Override
    public void onOpenFailure(Room room, Exception e) {
        Log.e(debugTag,
                "Failed to open chat room - exception" + e.toString());
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final MemberClass memberClass = objectMapper.treeToValue(receivedMessage.getMember().
                    getClientData(), MemberClass.class);
            boolean isSentByCurrentUser = receivedMessage.getClientID().equals(scaledrone.
                    getClientID());
            final UserMessage message = new UserMessage(receivedMessage.getData().asText(), memberClass,
                    isSentByCurrentUser);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(debugTag, "Message Received : " + message.getText());
                    messageAdapter.add(message);
                    myUserChatListView.setSelection(myUserChatListView.getCount() - 1);
                }

            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
