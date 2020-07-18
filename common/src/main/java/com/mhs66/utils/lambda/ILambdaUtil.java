package com.mhs66.utils.lambda;


import com.mhs66.utils.IStringUtil;
import com.mhs66.utils.lambda.functional.IFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * description:
 * java labda工具类
 *
 * @author 76442
 * @date 2020-07-15 20:13
 */
public class ILambdaUtil {
    /**
     * lambda 表达式缓存
     */
    static Map<Class, SerializedLambda> CLASS_LAMDBA_CACHE = new ConcurrentHashMap<>();

    /***
     * 根据传入方法引用函数 推断属性名
     * @param fn 类get方法
     * @param flag 是否驼峰命名转下划线
     * @return 属性名
     */
    private static <T, R> String convertToFieldName(IFunction<T, R> fn, boolean flag) {
        SerializedLambda lambda = ILambdaUtil.getSerializedLambda(fn);
        // 获取方法名
        String methodName = lambda.getImplMethodName();
        String prefix = "get";
        // 截取get/is之后的字符串并转换首字母为小写
        String fieldName = IStringUtil.toLowerCaseFirstOne(methodName.replace(prefix, ""));
        return flag ? IStringUtil.camelToUnderline(fieldName) : fieldName;
    }

    /**
     * 根据传入方法引用函数 推断属性名 默认驼峰转下划线
     *
     * @param fn 类get方法
     * @return 属性名 nick_name
     */
    public static <T, R> String getFieldNameUnderline(IFunction<T, R> fn) {
        return convertToFieldName(fn, true);
    }

    /**
     * 根据传入方法引用函数 推断属性名
     *
     * @param fn 类get方法
     * @return 属性名 nick_name
     */
    public static <T, R> String getFieldName(IFunction<T, R> fn) {
        return convertToFieldName(fn, false);
    }

    /**
     * 获得lambda 序列化对象
     * SerializedLambda
     * <p>
     * jdk1.8提供的一个新的类，凡是继承了Serializable的函数式接口的实例都可以获取一个属于它的SerializedLambda实例，
     * 并且通过反射获取到方法的名称，根据我们标准的java bean的定义规则就可以通过方法名称来获取属性名称。
     *
     * @param fn 传入lambda表达式
     * @return 序列化lambda表达式
     */
    static <T, R> SerializedLambda getSerializedLambda(IFunction<T, R> fn) {
        SerializedLambda lambda = CLASS_LAMDBA_CACHE.get(fn.getClass());
        // 先检查缓存中是否已存在
        if (lambda == null) {
            try {
                // 提取SerializedLambda并缓存
                Method[] methods = fn.getClass().getMethods();
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMDBA_CACHE.put(fn.getClass(), lambda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lambda;
    }
}

