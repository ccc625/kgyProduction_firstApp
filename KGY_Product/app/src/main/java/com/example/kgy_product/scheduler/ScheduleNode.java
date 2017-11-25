package com.example.kgy_product.scheduler;

/**
 * Created by ccc62 on 2017-11-12.
 */

public class ScheduleNode
{
    private String _key;
    private ScheduleAction _action;

    public ScheduleCallback callback;

    public String getKey()
    {
        return _key;
    }

    public ScheduleNode(String key, ScheduleAction action)
    {
        this._key = key;
        this._action = action;
    }

    public void start()
    {
        ScheduleAction.Callback callback  = new ScheduleAction.Callback() {
            @Override
            public void excute()
            {
                complete();
            }
        };

        _action.excute(callback);
    }

    public void complete()
    {
        callback.onComplete(_key);
    }

    public interface ScheduleAction
    {
        void excute(Callback callback);

        interface Callback
        {
            void excute();
        }
    }

    public interface ScheduleCallback
    {
        void onComplete(String key);
    }
}
