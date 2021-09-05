package ru.lihogub;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("STARTED");
        Room<String> room = new Room<>();

        while (true) {
            Socket socket = serverSocket.accept();
            Client<String> client = new SocketClient(socket);

            System.out.println(client);

            client
                    .receive()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            room::send,
                            Throwable::printStackTrace,
                            () -> System.out.println("room disconnected " + room)
                    );

            room
                    .receive()
                    .subscribeOn(Schedulers.io())
                    .flatMapCompletable(client::send)
                    .subscribe();

        }
    }
}
