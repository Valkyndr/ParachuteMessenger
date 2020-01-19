package com.williamkosasih.parachutemessenger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {
    List<UserMessage> userMessages = new ArrayList<UserMessage>();
    Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void add(UserMessage userMessage) {
        userMessages.add(userMessage);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return userMessages.get(i);
    }

    @Override()
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);
        UserMessage userMessage = userMessages.get(i);

        if (userMessage.isSentByCurrentUser()) {
            convertView = layoutInflater.inflate(R.layout.my_message_bubble, null);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(userMessage.getText());
        } else {
            convertView = layoutInflater.inflate(R.layout.others_message_bubble, null);
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.name = convertView.findViewById(R.id.name);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag((holder));

            holder.name.setText(userMessage.getMemberClass().getName());
            holder.messageBody.setText(userMessage.getText());
            GradientDrawable gradientDrawable = (GradientDrawable) holder.avatar.getBackground();
            gradientDrawable.setColor(Color.parseColor(userMessage.getMemberClass().getColor()));
        }
        return convertView;
    }

    class MessageViewHolder {
        public View avatar;
        public TextView name;
        public TextView messageBody;
    }
}
