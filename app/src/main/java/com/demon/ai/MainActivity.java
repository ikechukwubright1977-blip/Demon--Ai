package com.demon.ai;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private LinearLayout chatLayout;
    private EditText messageInput;
    private ScrollView scrollView;

    private int red = Color.rgb(255, 45, 65);
    private int background = Color.rgb(12, 12, 16);
    private int panel = Color.rgb(25, 25, 32);
    private int white = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(background);
        getWindow().setNavigationBarColor(background);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(background);
        root.setPadding(20, 16, 20, 12);

        TextView title = new TextView(this);
        title.setText("DEMON AI");
        title.setTextColor(red);
        title.setTextSize(28);
        title.setTypeface(null, Typeface.BOLD);

        TextView subtitle = new TextView(this);
        subtitle.setText("YOUR PERSONAL AI ASSISTANT");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(11);

        root.addView(title);
        root.addView(subtitle);

        scrollView = new ScrollView(this);
        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);

        scrollParams.topMargin = 24;
        scrollView.setLayoutParams(scrollParams);

        chatLayout = new LinearLayout(this);
        chatLayout.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(chatLayout);
        root.addView(scrollView);

        addMessage("DEMON",
                "I am DEMON, your personal AI assistant. " +
                "Send a message to begin. This version is the first interface.");

        LinearLayout inputRow = new LinearLayout(this);
        inputRow.setOrientation(LinearLayout.HORIZONTAL);
        inputRow.setGravity(Gravity.CENTER_VERTICAL);

        messageInput = new EditText(this);
        messageInput.setSingleLine(false);
        messageInput.setHint("Message DEMON...");
        messageInput.setHintTextColor(Color.GRAY);
        messageInput.setTextColor(white);
        messageInput.setTextSize(15);
        messageInput.setPadding(14, 10, 14, 10);
        messageInput.setBackgroundColor(panel);

        LinearLayout.LayoutParams inputParams =
                new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        inputParams.rightMargin = 8;
        inputRow.addView(messageInput, inputParams);

        Button sendButton = new Button(this);
        sendButton.setText("SEND");
        sendButton.setTextColor(white);
        sendButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(red));

        inputRow.addView(sendButton);
        root.addView(inputRow);

        sendButton.setOnClickListener(v -> sendMessage());

        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });

        setContentView(root);
    }

    private void sendMessage() {
        String message = messageInput.getText().toString().trim();

        if (message.isEmpty()) {
            return;
        }

        addMessage("YOU", message);
        messageInput.setText("");

        InputMethodManager imm =
                (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(
                    messageInput.getWindowToken(), 0);
        }

        addMessage("DEMON",
                "Your message has been received. " +
                "AI connection will be added in the next stage.");
    }

    private void addMessage(String sender, String message) {
        TextView bubble = new TextView(this);

        bubble.setText(sender + "\n" + message);
        bubble.setTextColor(white);
        bubble.setTextSize(15);
        bubble.setPadding(16, 14, 16, 14);
        bubble.setBackgroundColor(panel);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        params.bottomMargin = 12;

        chatLayout.addView(bubble, params);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}
