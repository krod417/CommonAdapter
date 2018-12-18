package com.krod.adapter;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将数据类型跟样式绑定起来
 * Created by jian.wj on 15-11-25.
 */
public class AdapterUtil {

    public static <T> BaseViewHolder createItem(Class<? extends BaseViewHolder> c, T t) {
        try {
            Constructor<? extends BaseViewHolder> constructor;
            if (t == null) {
                constructor = c.getConstructor();
                return constructor.newInstance();
            } else {
                constructor = getMatchingAccessibleConstructor(c, t);
                if (constructor != null) {
                    return constructor.newInstance(t);
                }
            }
        } catch (InstantiationException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        }
        throw new RuntimeException("BaseViewHolder Missing default constructor");
    }

    private static <T> Constructor getMatchingAccessibleConstructor(Class<? extends BaseViewHolder> cls, @NonNull T t) {
        Constructor<?> c;
        Class temp = t.getClass();
        while (true) {
            try {
                c = cls.getConstructor(temp);
                return c;
            } catch (NoSuchMethodException e) {
                if (temp == Object.class) {
                    return null;
                }
                temp = temp.getSuperclass();
            }
        }
    }

    public static <T> List<BaseViewHolder> setList(Class<? extends BaseViewHolder> c, List<T> data) {
        List<BaseViewHolder> list = new ArrayList<>();
        int len = 0;
        if (data == null || (len = data.size()) <= 0) {
            return list;
        }
        try {
            Constructor<? extends BaseViewHolder> constructor = c.getConstructor(data.get(0).getClass());
            for (int i = 0; i < len; i++) {
                list.add(constructor.newInstance(new Object[]{data.get(i)}));
            }
        } catch (InstantiationException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("BaseViewHolder Missing default constructor");
        }
        return list;
    }

    /**
     * Check the cache timestamp.
     *
     * @param oldEntity
     * @param newEntity
     * @return true use the cache
     */
    public static boolean checkCache(BaseViewHolder oldEntity, BaseViewHolder newEntity) {
        return oldEntity != null && newEntity.isSingleton() && oldEntity.getTimestamp() == newEntity.getTimestamp();
    }
}
