package com.actor.testapplication.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.testapplication.R;
import com.actor.testapplication.databinding.FragmentTestBinding;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TestFragment extends BaseFragment<FragmentTestBinding> {

    private static final String TAG = "TestFragment";

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn1 = viewBinding.btn1;

        //io.reactivex.rxjava3.core.Flowable: 0..N flows, supporting Reactive-Streams and backpressure
        //io.reactivex.rxjava3.core.Observable: 0..N flows, no backpressure,
        //io.reactivex.rxjava3.core.Single: a flow of exactly 1 item or an error,
        //io.reactivex.rxjava3.core.Completable: a flow without items but only a completion or error signal,
        //io.reactivex.rxjava3.core.Maybe: a flow with no items, exactly one item or an error.
        Flowable.just("hello workd!").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                LogUtils.errorFormat("accept: " + s);
            }
        });


        Flowable.fromCallable(new Callable<Object>() {//计算/耗时网络请求
            @Override
            public Object call() throws Exception {
                Thread.sleep(1000); //  imitate expensive computation
                return "Done";
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {
                        LogUtils.errorFormat("accept: %s\n", o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        throwable.printStackTrace();
                    }
                });

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Throwable {
                while (!emitter.isDisposed()) {
                    long time = System.currentTimeMillis();
                    emitter.onNext(time);
                    if (time % 2 != 0) {
                        emitter.onError(new IllegalStateException("Odd millisecond!"));
                        break;
                    }
                }
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                LogUtils.errorFormat("accept: %s\n", o);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                throwable.printStackTrace();
            }
        });
//        Observable.create(emitter -> {
//            while (!emitter.isDisposed()) {
//                long time = System.currentTimeMillis();
//                emitter.onNext(time);
//                if (time % 2 != 0) {
//                    emitter.onError(new IllegalStateException("Odd millisecond!"));
//                    break;
//                }
//            }
//        })
//                .subscribe(System.out::println, Throwable::printStackTrace);

        setOnClickListeners(R.id.btn_1);
    }

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                break;
            default:
                break;
        }
    }
}
