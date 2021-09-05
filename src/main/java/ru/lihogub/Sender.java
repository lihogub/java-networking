package ru.lihogub;

import io.reactivex.rxjava3.core.Completable;

public interface Sender<T> {
    Completable send(T payload);
}
