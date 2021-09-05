package ru.lihogub;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.UUID;

public class Room<T> implements Receiver<T>, Sender<T> {
    private final UUID uuid = UUID.randomUUID();
    private final Subject<T> room = ReplaySubject.createWithSize(10);

    @Override
    public Observable<T> receive() {
        return room;
    }

    @Override
    public Completable send(T payload) {
        System.out.println(payload);
        room.onNext(payload);
        return Completable.complete();
    }
}
