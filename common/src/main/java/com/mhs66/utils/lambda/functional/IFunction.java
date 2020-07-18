package com.mhs66.utils.lambda.functional;

import java.io.Serializable;
import java.util.function.Function;

/**
 *description:
 * 自定义序列化函数接口
 *@author 76442
 *@date 2020-07-15 20:13
 */
@FunctionalInterface
public interface IFunction<T, R> extends Function<T, R>, Serializable {

}
