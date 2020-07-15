package com.mhs66.utis;

import com.google.common.collect.Lists;
import com.mhs66.config.ILogBase;
import com.mhs66.utis.lambda.ILambdaUtil;
import com.mhs66.utis.lambda.functional.IFunction;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 *description:
 * bean操作类
 *@author 76442
 *@date 2020-07-15 20:10
 */
public class IBeanUtil extends BeanUtils implements ILogBase {

    /**
     * 判断对象是否为null
     *
     * @param t   入参
     * @param <T> object
     * @return booleaan
     */
    public static <T> boolean isNull(T t) {
        return null == t;
    }

    /**
     * 效验参数是否为空
     *
     * @param obj 效验参数
     * @return boolean
     */
    public static boolean isRequired(Object... obj) {
        for (Object o : obj) {
            if (o == null) {
                return true;
            }
            if (o instanceof Integer) {
                int value = (Integer) o;
                if (value == 0) {
                    return true;
                }
            } else if (o instanceof String) {
                String s = (String) o;
                if (IStringUtil.isBlank(s)) {
                    return true;
                }
            } else if (o instanceof Double) {
                double d = (Double) o;
                if (d == 0) {
                    return true;
                }
            } else if (o instanceof Float) {
                float f = (Float) o;
                if (f == 0) {
                    return true;
                }
            } else if (o instanceof Long) {
                long l = (Long) o;
                if (l == 0) {
                    return true;
                }
            } else if (o instanceof Boolean) {
                boolean b = (Boolean) o;
                if (b) {
                    return true;
                }
            } else if (o instanceof Date) {
                Date d = (Date) o;
                if (d.getTime() > 0) {
                    return true;
                }
            }
            if (o instanceof Optional) {
                if (!((Optional) o).isPresent()) {
                    return true;
                }
            }
            if (o instanceof CharSequence) {
                if (((CharSequence) o).length() == 0) {
                    return true;
                }
            }
            if (o.getClass().isArray()) {
                if (Array.getLength(o) == 0) {
                    return true;
                }
            }
            if (o instanceof Collection) {
                if (((Collection) o).isEmpty()) {
                    return true;
                }
            }
            if (o instanceof Map) {
                if (((Map) o).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNotRequired(Object... obj) {
        return !isRequired(obj);
    }

    /**
     * 判断传入对象指定的属性不为空
     *
     * @param f         传入对象
     * @param functions 属性方法数组
     * @param <F>       传入参数类
     * @param <T>       获得属性值
     * @return boolean
     * SuppressWarnings unchecked 忽略执行了未检查的转换时的警告
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <F, T> boolean isValueNotBank(F f, Function<T, F>... functions) {
        if (isNull(f)) {
            return false;
        }
        for (Function function : functions) {
            Object value = function.apply(f);
            return !isRequired(value);
        }
        return true;
    }

    /**
     * @param f         传入对象
     * @param flag      是否检测忽略属性不为空
     * @param functions 忽略方法数组
     * @param <F>       传入参数类
     * @param <T>       获得属性值
     * @return boolean
     */
    @SafeVarargs
    public static <F, T> boolean isValueBankExclude(F f, boolean flag, IFunction<T, F>... functions) {
        if (ObjectUtils.isEmpty(f)) {
            return false;
        }
        List<String> fileNames = Lists.newArrayList();
        for (IFunction<T, F> function : functions) {
            fileNames.add(ILambdaUtil.convertToFieldName(function));
        }
        Field[] files = f.getClass().getDeclaredFields();
        for (Field field : files) {
            field.setAccessible(true);
            if (IStringUtil.equals("serialVersionUID", field.getName())) {
                continue;
            }
            try {
                if (fileNames.contains(field.getName())) {
                    if (flag) {
                        if (!IBeanUtil.isRequired(field.get(f))) {
                            return true;
                        }
                    }
                } else {
                    if (IBeanUtil.isRequired(field.get(f))) {
                        return true;
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("{}获取属性错误{}", f.getClass().getName(), field.getName(), e);
                return true;
            }
        }
        return false;
    }

    /**
     * 检测对象是否存在该属性
     *
     * @param fieldName 属性名
     * @param clazz     类对象
     * @return boolean
     */
    public static boolean isFieldExist(String fieldName, Class clazz) {
        if (IStringUtil.isBlank(fieldName)) {
            return false;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}

