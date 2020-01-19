package com.williamkosasih.parachutemessenger;

public class UserMessage {
    private String text;
    private MemberClass memberClass;
    private boolean isSentByCurrentUser;

    UserMessage(String text, MemberClass memberClass, boolean isSentByCurrentUser) {
        this.text = text;
        this.memberClass = memberClass;
        this.isSentByCurrentUser = isSentByCurrentUser;
    }

    public String getText() {
        return text;
    }

    public MemberClass getMemberClass() {
        return memberClass;
    }

    public boolean isSentByCurrentUser() {
        return isSentByCurrentUser;
    }
}
