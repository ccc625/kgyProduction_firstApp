package com.example.kgy_product.scheduler;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by ccc62 on 2017-11-12.
 */

public class Scheduler
{
    private ScheduleQueue _queue;
    private OnCompleteSchedulerListener _onCompleteSchedulerListener;
    private ScheduleNode _currentNode;

    public Scheduler(OnCompleteSchedulerListener onCompleteSchedulerListener)
    {
        _onCompleteSchedulerListener = onCompleteSchedulerListener;

        _queue = new ScheduleQueue();
    }

    public void add(ScheduleNode node)
    {
        _queue.add(node);
    }

    public void start()
    {
        onNextNode();
    }

    public void completeSchedule(String key)
    {
        if( _queue.containsByKey(key) )
        {
            _queue.removeByKey(key);
        }
        else if( _currentNode.getKey() == key )
        {
            _currentNode.complete();
        }
    }

    private void onNextNode()
    {
        if( _queue.isEmpty() )
        {
            if( _onCompleteSchedulerListener != null)
            {
                _currentNode = null;
                _onCompleteSchedulerListener.onComplete();
            }
            return;
        }

        _currentNode = _queue.peek();
        _currentNode.callback = new ScheduleNode.ScheduleCallback() {
            @Override
            public void onComplete(String key)
            {
                System.out.println("[Scheduler] onComplete schedule // key = " + key);
                onNextNode();
            }
        };

        _currentNode.start();
    }

    public interface OnCompleteSchedulerListener
    {
        void onComplete();
    }
}
