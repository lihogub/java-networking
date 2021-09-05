package ru.lihogub;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class SocketClient implements Client<String> {
    private final UUID uuid = UUID.randomUUID();
    private final BufferedReader in;
    private final PrintWriter out;

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocketClient socketClient = (SocketClient) o;
        return uuid.equals(socketClient.uuid);
    }

    @Override
    public String toString() {
        return uuid.toString();
    }

    public SocketClient(Socket socket) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Observable<String> receive() {
        return Observable.create(source -> {
            try {
                while (true) {
                    String s = in.readLine();
                    if (s == null) {
                        break;
                    }
                    source.onNext(s);
                }
                source.onComplete();
            } catch (Exception e) {
                source.onError(e);
            } finally {
                source.onComplete();
            }
        });
    }

    public Completable send(String text) {
        return Completable.create(source -> out.println(text));
    }
}