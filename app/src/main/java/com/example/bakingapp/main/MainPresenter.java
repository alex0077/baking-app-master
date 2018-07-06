package com.example.bakingapp.main;


import android.util.Log;

import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.network.NetworkClient;
import com.example.bakingapp.network.NetworkInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainPresenterInterface {

    private static final String TAG = "MainPresenter" ;
    private MainViewInterface mainViewInterface;
    public MainPresenter(MainActivity mainViewInterface) {
        this.mainViewInterface = mainViewInterface;
    }

    public void getRecipes() {
        getObservable().subscribe(getObserver());
    }

    private Observable<List<Recipe>> getObservable(){
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observer<List<Recipe>> getObserver(){
        return new Observer<List<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Recipe> recipes) {
                mainViewInterface.displayRecipes(recipes);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.getMessage());
                Log.i(TAG, "onError: "+e.getLocalizedMessage());
                e.printStackTrace();
                mainViewInterface.displayError("Error fetching Recipe data");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: Completed");
            }
        };
    }
}
