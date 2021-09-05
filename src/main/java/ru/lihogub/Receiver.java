package ru.lihogub;

import io.reactivex.rxjava3.core.Observable;

public interface Receiver<T> {
    Observable<T> receive();
}
