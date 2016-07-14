package com.marceme.androidrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.marceme.androidrxjava.adapter.MessageListAdapter;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MessageListAdapter adapter;
    private Subscription subscription;

    // Resources:
    // http://blog.feedpresso.com/2016/01/25/why-you-should-use-rxjava-in-android-a-short-introduction-to-rxjava.html
    // https://realm.io/news/donn-felker-reactive-android-ui-programming-with-rxbinding/
    // https://github.com/JakeWharton/RxBinding



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new MessageListAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        Button sendMsgBtn = (Button) findViewById(R.id.sendUserMessage);
        final EditText userText = (EditText) findViewById(R.id.chat_user_message);

        // T - the type of the items emitted by the Observable
        // R - the result type

        Observable<String> clickObservable = RxView.clicks(sendMsgBtn).map(new Func1<Void, String>() {
            @Override
            public String call(Void aVoid) {
                return userText.getText().toString();
            }
        });

        clickObservable.filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return !TextUtils.isEmpty(s);
            }
        });

        subscription = clickObservable.subscribe(new Action1<String>() {

            @Override
            public void call(String s) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
