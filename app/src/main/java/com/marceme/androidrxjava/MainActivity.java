package com.marceme.androidrxjava;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.marceme.androidrxjava.adapter.MessageListAdapter;
import com.marceme.androidrxjava.model.Message;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    public String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private Subscription subscription;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new MessageListAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // Get reference to Button and EditText
        Button sendMsgBtn = (Button) findViewById(R.id.sendBtn);
        final EditText userText = (EditText) findViewById(R.id.chat_user_message);


        // No chaining operators and subscriber
//        // T - the type of the items emitted by the Observable
//        // R - the result type
//
//        // We bind the sendMsgButton (a button) to the RxJava using RxBinding library: RxView.clicks(Button)
//        // By binding, we create a source observable which emits an item when button sendMsgBtn is clicked.
//        // The type of the item emitted is of type Void, that's why we have Observable of type Void: Observable<Void>
//        Observable<Void> clickObservable = RxView.clicks(sendMsgBtn);
//
//        // Operators are used to manipulate emitted item between the source Observable and the subscriber
//        // We use the operator map to transform an emitted item into another item
//        // At this step, the emitted item from source Observable is of type Void, and we want to
//        // transform it into another item of type String, after transformation this item of type String is emitted.
//        Observable<String> observableAfterMap = clickObservable.map(new Func1<Void, String>() {
//            @Override
//            public String call(Void aVoid) {
//                return userText.getText().toString(); // Return string
//            }
//        });
//
//        // At this stage, we have an Observable that emits item of type String: Observable<String> observableAfterMap
//        // We add another operator called filter to manipulate the emitted item of type "String".
//        // The operator filter emits only items ("String" item) from observableAfterMap that pass the condition defined
//        // in call(String s). If the emitted items do not pass the condition, they are not emitted to the subscriber.
//        // Our filter operator checks if the emitted item of type String is not empty. If the String item is not empty,
//        // the String items is emitted to the subscriber
//
//        Observable<String> observableAfterFilter = observableAfterMap.filter(new Func1<String, Boolean>() {
//            @Override
//            public Boolean call(String s) {
//                return !TextUtils.isEmpty(s);
//            }
//        });
//
//        // At this stage, we subscribe to the observable observableAfterFilter and we provide a callBack Action1<String>{}
//        // to handle the emitted items. When an item of type String is received, the function
//        // call(String message)is called (or invoked. Once the function call(String message) is called, we create
//        // a new message object, and add it to the list inside the adapter, and finally clear the current text in the EditText.
//        subscription = observableAfterFilter.subscribe(new Action1<String>() {
//
//            @Override
//            public void call(String emittedMessage) {
//
//                Message newMessage = new Message(emittedMessage);
//
//                adapter.addMessageInList(newMessage);
//
//                //Clear editText
//                userText.setText("");
//
//            }
//        });


        // Chaining all method together
        subscription = RxView.clicks(sendMsgBtn).map(new Func1<Void, String>() {
            @Override
            public String call(Void aVoid) {
                return userText.getText().toString();
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return !TextUtils.isEmpty(s);
            }
        }).subscribe(new Action1<String>() {

            @Override
            public void call(String emittedMessage) {

                Message newMessage = new Message(emittedMessage);

                adapter.addMessageInList(newMessage);

                //Clear editText
                userText.setText("");

            }
        });



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister subscriber
        subscription.unsubscribe();
    }

}
