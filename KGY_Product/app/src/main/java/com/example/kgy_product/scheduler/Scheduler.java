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
    private Queue<ScheduleNode> _queue;

    public Scheduler()
    {
        _queue = new Queue<ScheduleNode>()
        {
            private ArrayList<ScheduleNode> _list = new ArrayList<>();

            @Override
            public boolean add(ScheduleNode scheduleNode)
            {
                if( _list == null )
                    return false;

                _list.add(scheduleNode);
                return true;
            }

            @Override
            public boolean offer(ScheduleNode scheduleNode)
            {
                return false;
            }

            @Override
            public ScheduleNode remove()
            {
                if( _list == null || _list.isEmpty() )
                    return null;

                return _list.remove(_list.size() - 1);
            }

            @Override
            public ScheduleNode poll()
            {
                if( _list == null || _list.isEmpty() )
                    return null;

                return _list.remove(_list.size() - 1);
            }

            @Override
            public ScheduleNode element() {
                return null;
            }

            @Override
            public ScheduleNode peek()
            {
                if( _list == null || _list.isEmpty() )
                    return null;

                return _list.remove(0);
            }

            @Override
            public int size()
            {
                if( _list == null || _list.isEmpty() )
                    return 0;

                return _list.size();
            }

            @Override
            public boolean isEmpty()
            {
                if( _list == null || _list.isEmpty() )
                    return true;

                return false;
            }

            @Override
            public boolean contains(Object o)
            {
                if( _list != null )
                {
                    return _list.contains( o );
                }

                return false;
            }

            @NonNull
            @Override
            public Iterator<ScheduleNode> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends ScheduleNode> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear()
            {

            }
        };
    }

    public void add(ScheduleNode node)
    {
        _queue.add(node);
    }

    public void start()
    {
        onNextNode();
    }

    private void onNextNode()
    {
        if( _queue.isEmpty() )
        {
            return;
        }

        ScheduleNode currentNode = _queue.peek();
        currentNode.callback = new ScheduleNode.ScheduleCallback() {
            @Override
            public void onComplete(String key)
            {
                System.out.println("[Scheduler] onComplete schedule // key = " + key);
                onNextNode();
            }
        };

        currentNode.start();
    }
}
