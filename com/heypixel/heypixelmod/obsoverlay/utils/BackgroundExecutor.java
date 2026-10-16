/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.NonNull;

public class BackgroundExecutor {
    private static final ScheduledExecutorService b = Executors.newScheduledThreadPool(3, new ThreadFactory(){
        private final AtomicInteger a = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException("r is marked non-null but is null");
            }
            return new Thread(runnable, "Multithreading Thread " + this.a.incrementAndGet());
        }
    });
    public static ExecutorService a = Executors.newCachedThreadPool(new ThreadFactory(){
        private final AtomicInteger a = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException("r is marked non-null but is null");
            }
            return new Thread(runnable, "Multithreading Thread " + this.a.incrementAndGet());
        }
    });

    public static void a(Runnable runnable, long l2, long l3, TimeUnit timeUnit) {
        b.scheduleAtFixedRate(runnable, l2, l3, timeUnit);
    }

    public static ScheduledFuture<?> b(Runnable runnable, long l2, long l3, TimeUnit timeUnit) {
        return b.scheduleAtFixedRate(runnable, l2, l3, timeUnit);
    }

    public static ScheduledFuture<?> a(Runnable runnable, long l2, TimeUnit timeUnit) {
        return b.schedule(runnable, l2, timeUnit);
    }

    public static int a() {
        return ((ThreadPoolExecutor)a).getActiveCount();
    }

    public static void a(Runnable runnable) {
        a.execute(runnable);
    }
}

