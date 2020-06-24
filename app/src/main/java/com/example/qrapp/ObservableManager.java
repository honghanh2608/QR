package com.example.qrapp;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class ObservableManager {
    public static Subject<Boolean> subject = PublishSubject.create();//Tao observable
}
