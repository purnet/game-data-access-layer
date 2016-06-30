package com.github.purnet.gamedataaccesslayer;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadId {

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<String> threadId =
        new ThreadLocal<String>() {
            @Override protected String initialValue() {
                return UUID.randomUUID().toString();
        }
    };


    // Returns the current thread's unique ID, assigning it if necessary
    public static String get() {
        return threadId.get();
    }
    
}
