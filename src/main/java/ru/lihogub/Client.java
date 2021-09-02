package ru.lihogub;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Observable<String> observeMsg() {
        return Observable.create(source -> {
            while (true) {
                String s = in.readLine();
                if (s == null) {
                    break;
                }
                source.onNext(s);
            }
            source.onComplete();
        });
    }

    public Completable sendMsg(String msg) {
        return Completable.create(source -> {
           out.println(msg);
        });
    }
}
