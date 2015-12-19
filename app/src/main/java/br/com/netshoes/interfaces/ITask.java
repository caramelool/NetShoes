package br.com.netshoes.interfaces;

import android.os.AsyncTask;

/**
 * Created by Caramelo on 17/12/2015.
 */
public interface ITask<T> {
    void onTaskDone(AsyncTask task, T arg);
    void onTaskErro(AsyncTask task);
}
