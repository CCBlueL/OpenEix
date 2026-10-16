/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.heypixel.heypixelmod.obsoverlay.events.api;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.api.a.EventSubscription;
import com.heypixel.heypixelmod.obsoverlay.events.api.events.Event;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class EventManager {
    private static final Logger a = LogManager.getLogger(EventManager.class);
    private final Map<Class<? extends Event>, List<MethodData>> b = new ConcurrentHashMap<Class<? extends Event>, List<MethodData>>();

    public void a(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (this.a(method)) continue;
            this.a(method, object);
        }
    }

    public void a(Object object, Class<? extends Event> clazz) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (this.a(method, clazz)) continue;
            this.a(method, object);
        }
    }

    public void b(Object object) {
        for (List<MethodData> list : this.b.values()) {
            for (MethodData methodData : list) {
                if (!methodData.source().equals(object)) continue;
                list.remove(methodData);
            }
        }
        this.a(true);
    }

    public void b(Object object, Class<? extends Event> clazz) {
        if (this.b.containsKey(clazz)) {
            for (MethodData methodData : this.b.get(clazz)) {
                if (!methodData.source().equals(object)) continue;
                this.b.get(clazz).remove(methodData);
            }
            this.a(true);
        }
    }

    private void a(Method method, Object object) {
        Class<?> clazz = method.getParameterTypes()[0];
        final MethodData methodData = new MethodData(object, method, method.getAnnotation(EventTarget.class).a(), EventManager.b(method, object));
        if (!methodData.target().isAccessible()) {
            methodData.target().setAccessible(true);
        }
        if (this.b.containsKey(clazz)) {
            if (!this.b.get(clazz).contains(methodData)) {
                this.b.get(clazz).add(methodData);
                this.b(clazz);
            }
        } else {
            this.b.put(clazz, (List<MethodData>)new CopyOnWriteArrayList<MethodData>(){
                private static final long serialVersionUID = 666L;
                {
                    this.add(methodData);
                }
            });
        }
    }

    private static MethodHandle b(Method method, Object object) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(method.getDeclaringClass(), MethodHandles.lookup());
            return lookup.unreflect(method).bindTo(object).asType(MethodType.methodType(Void.TYPE, Event.class));
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public void a(Class<? extends Event> clazz) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> iterator = this.b.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getKey().equals(clazz)) continue;
            iterator.remove();
            break;
        }
    }

    public void a(boolean bl) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> iterator = this.b.entrySet().iterator();
        while (iterator.hasNext()) {
            if (bl && !iterator.next().getValue().isEmpty()) continue;
            iterator.remove();
        }
    }

    private void b(Class<? extends Event> clazz) {
        CopyOnWriteArrayList<MethodData> copyOnWriteArrayList = new CopyOnWriteArrayList<MethodData>();
        for (byte by : com.heypixel.heypixelmod.obsoverlay.events.api.EventSubscriberMetadata.b.f) {
            for (MethodData methodData : this.b.get(clazz)) {
                if (methodData.priority() != by) continue;
                copyOnWriteArrayList.add(methodData);
            }
        }
        this.b.put(clazz, copyOnWriteArrayList);
    }

    private boolean a(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    private boolean a(Method method, Class<? extends Event> clazz) {
        return this.a(method) || !method.getParameterTypes()[0].equals(clazz);
    }

    public Event a(Event event) {
        block4: {
            List<MethodData> list = this.b.get(event.getClass());
            if (list == null) break block4;
            if (event instanceof com.heypixel.heypixelmod.obsoverlay.events.api.events.EventFlagBase) {
                com.heypixel.heypixelmod.obsoverlay.events.api.events.EventFlagBase b2 = (com.heypixel.heypixelmod.obsoverlay.events.api.events.EventFlagBase)event;
                for (MethodData methodData : list) {
                    this.a(methodData, event);
                    if (!b2.b()) continue;
                    break;
                }
            } else {
                for (MethodData methodData : list) {
                    this.a(methodData, event);
                }
            }
        }
        return event;
    }

    private void a(MethodData methodData, Event event) {
        MethodHandle methodHandle = methodData.handle();
        if (methodHandle != null) {
            try {
                methodHandle.invoke(event);
            }
            catch (Throwable throwable) {
                a.error("\u4e8b\u4ef6\u5904\u7406\u5f02\u5e38: {}.{}", (Object)methodData.source().getClass().getSimpleName(), (Object)methodData.target().getName(), (Object)(throwable instanceof Exception ? throwable : throwable.getCause()));
            }
            return;
        }
        try {
            methodData.target().invoke(methodData.source(), event);
        }
        catch (InvocationTargetException invocationTargetException) {
            a.error("\u4e8b\u4ef6\u5904\u7406\u5f02\u5e38: {}.{}", (Object)methodData.source().getClass().getSimpleName(), (Object)methodData.target().getName(), (Object)invocationTargetException.getCause());
        }
        catch (Exception exception) {
            a.error("\u4e8b\u4ef6\u8c03\u7528\u5931\u8d25: {}.{}", (Object)methodData.source().getClass().getSimpleName(), (Object)methodData.target().getName(), (Object)exception);
        }
    }

    private record MethodData(Object source, Method target, byte priority, MethodHandle handle) {
    }
}

