package ru.lihogub;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Lol");
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();
        Client client = new Client(socket);
        client
                .observeMsg()
                .doOnEach(System.out::println)
                .subscribe(it -> {
                    client
                            .sendMsg(it)
                            .subscribe();
                });
    }
}
