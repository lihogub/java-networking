package ru.lihogub;

public class Messange {
    private String sender;
    private String receiver;
    private final String msg;

    public Messange(String msg, String sender, String receiver) {
        this.msg = msg;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "[" + sender + "] " + msg;
    }
}
