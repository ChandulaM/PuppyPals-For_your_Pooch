package com.example.puppypals_foryourpooch.model;

public class Chat {

    private String senderId;
    private String chatId;
    private String message;
    private String time;

    public Chat() {
    }

    public Chat(String senderId, String chatId, String message, String time) {
        this.senderId = senderId;
        this.chatId = chatId;
        this.message = message;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
