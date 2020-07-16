package com.mhs66.utis;

import com.mhs66.consts.CookieConsts;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


/**
 * description:
 * cookie工具类
 *
 * @author 76442
 * @date 2020-07-15 21:03
 */
@Slf4j
public class CookieUtil {

    /**
     * 获得登录token
     *
     * @param request 请求
     * @return token  String
     */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (IStringUtil.equals(ck.getName(), CookieConsts.getToken())) {
                    log.debug("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 写入token
     *
     * @param response 返回
     */
    public static String writeLoginToken(HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        Cookie ck = new Cookie(CookieConsts.getToken(), token);
        //设置共享域
        ck.setDomain(CookieConsts.getDomain());
        //设置共享路径
        ck.setPath(CookieConsts.getPath());
        //设置只读
        ck.setHttpOnly(true);
        //单位是秒。
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。如果是-1，代表永久
        ck.setMaxAge(60 * 60 * 24 * 365);
        log.debug("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
        return token;
    }

    /**
     * 删除 Cookie
     *
     * @param request  请求
     * @param response 返回
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (IStringUtil.equals(ck.getName(), CookieConsts.getToken())) {
                    ck.setDomain(CookieConsts.getDomain());
                    ck.setPath(CookieConsts.getPath());
                    //设置成0，代表删除此cookie。
                    ck.setMaxAge(0);
                    log.debug("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }


    /**
     * @param request    请求对象
     * @param cookieName 设置cookie的name
     * @return 设置cookie的value
     * Description: 得到Cookie的值, 不解码
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return doGetCookieValue(request, cookieName, false, null);
    }

    /**
     * @param request    请求对象
     * @param cookieName 设置cookie的name
     * @return 设置cookie的value
     * Description: 得到Cookie的值, 解码
     */
    public static String getCookieValueDecoder(HttpServletRequest request, String cookieName) {
        return doGetCookieValue(request, cookieName, true, StandardCharsets.UTF_8.toString());
    }

    /**
     * @param request      请求对象
     * @param cookieName   设置cookie的name
     * @param decodeString 解码类型
     * @return 设置cookie的value
     * Description: 得到Cookie的值, 解码
     */
    public static String getCookieValueDecoder(HttpServletRequest request, String cookieName, String decodeString) {
        return doGetCookieValue(request, cookieName, true, decodeString);
    }

    /**
     * Description:设置Cookie的值 不设置生效时间默认浏览器关闭即失效, 也不编码
     *
     * @param request     请求对象
     * @param response    响应对象
     * @param cookieName  设置cookie的name
     * @param cookieValue 设置cookie的value
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, null);
    }

    /**
     * Description: 设置Cookie的值 在指定时间内生效, 但不编码
     * path为配置文件读取
     * domain为读取的请求域
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param cookieName   设置cookie的name
     * @param cookieValue  设置cookie的value
     * @param cookieMaxage cookie生效的最大秒数 大于0 则设置
     *                     cookie生效的最大秒数 设置值大于0, 将cookie存储于本地磁盘, 过期后删除设
     *                     置值小于0, cookie不会保存于本地, 浏览器会话结束后, 将会删除,
     *                     设置值等于0, 立即删除cookie
     *                     为null不设置 默认-1
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, Integer cookieMaxage) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, false, null);
    }


    /**
     * Description: 设置Cookie的值 不设置生效时间, 但编码
     * path为配置文件读取
     * domain为读取的请求域
     *
     * @param request     请求对象
     * @param response    响应对象
     * @param cookieName  设置cookie的name
     * @param cookieValue 设置cookie的value
     */
    public static void setCookieEncode(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                       String cookieValue) {
        setCookieEncode(request, response, cookieName, cookieValue, null);
    }

    /**
     * Description:  设置Cookie的值 在指定时间内生效, 编码参数 默认 UTF-8
     * path为配置文件读取
     * domain为读取的请求域
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param cookieName   设置cookie的name
     * @param cookieValue  设置cookie的value
     * @param cookieMaxage cookie生效的最大秒数 大于0 则设置
     *                     cookie生效的最大秒数 设置值大于0, 将cookie存储于本地磁盘, 过期后删除设
     *                     置值小于0, cookie不会保存于本地, 浏览器会话结束后, 将会删除,
     *                     设置值等于0, 立即删除cookie
     *                     为null不设置 默认-1
     */
    public static void setCookieEncode(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                       String cookieValue, Integer cookieMaxage) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, true, StandardCharsets.UTF_8.toString());
    }


    /**
     * Description: 设置Cookie的值，并使其在指定时间内生效 设置编码格式
     * path为配置文件读取
     * domain为读取的请求域
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param cookieName   设置cookie的name
     * @param cookieValue  设置cookie的value
     * @param cookieMaxage cookie生效的最大秒数 大于0 则设置
     *                     cookie生效的最大秒数 设置值大于0, 将cookie存储于本地磁盘, 过期后删除设
     *                     置值小于0, cookie不会保存于本地, 浏览器会话结束后, 将会删除,
     *                     设置值等于0, 立即删除cookie
     *                     为null不设置 默认-1
     * @param encodeString 编码格式
     */
    public static void setCookieEncode(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                       String cookieValue, Integer cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, true, encodeString);
    }


    /**
     * 删除cookie
     *
     * @param request    请求
     * @param response   响应
     * @param cookieName name
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName) {
        doSetCookie(request, response, cookieName, null, 0, false, null);
    }


    /**
     * 得到Cookie的值
     *
     * @param request      请求头
     * @param cookieName   name
     * @param isDecoder    是否编码
     * @param decodeString 编码格式
     * @return 值
     */
    private static String doGetCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder, String decodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (Cookie cookie : cookieList) {
                if (cookie.getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookie.getValue(), decodeString);
                    } else {
                        retValue = cookie.getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("cookie值解析失败{}", cookieName, e);
        }
        return retValue;
    }


    /**
     * Description: 设置Cookie的值，并使其在指定时间内生效
     * path为配置文件读取
     * domain为读取的请求域
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param cookieName   设置cookie的name
     * @param cookieValue  设置cookie的value
     * @param cookieMaxage cookie生效的最大秒数 设置值大于0, 将cookie存储于本地磁盘, 过期后删除设
     *                     置值小于0, cookie不会保存于本地, 浏览器会话结束后, 将会删除,
     *                     设置值等于0, 立即删除cookie
     *                     为null不设置 默认-1
     * @param isEncode     是否编码
     * @param encodeString 编码格式
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName, String cookieValue, Integer cookieMaxage, boolean isEncode, String encodeString) {
        try {
            //值为null则转为空值
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                //字符集编码
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            //创建cookie
            Cookie cookie = new Cookie(cookieName, cookieValue);
            //生效时间大于0则设置
            if (null != cookieMaxage) {
                cookie.setMaxAge(cookieMaxage);
            }

            //判断请求对象是否为空 设置域名的cookie
            if (null != request) {
                String domainName = getDomainName(request);
                if (!"localhost".equals(domainName)) {
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath(CookieConsts.getPath());
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error("cookie值设置失败 name{} value{} isEncode{} encodeString{}", cookieName, cookieValue, isEncode, encodeString, e);
        }
    }


    /**
     * Description: 得到cookie的域名
     *
     * @return domain信息
     */
    private static String getDomainName(HttpServletRequest request) {
        String domainName = null;
        //获得请求路径 全路径 http://localhost:8080/test/test.jsp
        String serverName = request.getRequestURL().toString();

        if (IStringUtil.isBlank(serverName)) {
            domainName = IStringUtil.EMPTY;
        } else {
            //字符串转换为小写
            serverName = serverName.toLowerCase();
            //字符串的子字符串 第7位 http:// 协议号后面的开始  localhost:8080/test/test.jsp
            serverName = serverName.substring(7);
            //截取后字符串的第一个 / 位置
            final int end = serverName.indexOf("/");
            //截取域名  localhost:8080
            serverName = serverName.substring(0, end);
            //判断:前面是否有值 拆分端口获取端口之前数据  localhost
            if (serverName.indexOf(":") > 0) {
                String[] ary = serverName.split(":");
                serverName = ary[0];
            }
            //转换域名为数组 [localhost]
            final String[] domains = serverName.split("\\.");
            //判断域名类型
            int len = domains.length;
            //大于3段切不是ip
            if (len > 3 && !isIp(serverName)) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }
        return domainName;
    }

    /**
     * 去掉IP字符串前后所有的空格
     *
     * @param iP 传入ip
     * @return ip
     */
    public static String trimSpaces(String iP) {
        while (iP.startsWith(IStringUtil.SPACE)) {
            iP = iP.substring(1).trim();
        }
        while (iP.endsWith(IStringUtil.SPACE)) {
            iP = iP.substring(0, iP.length() - 1).trim();
        }
        return iP;
    }

    /**
     * 判断是否为ip
     *
     * @param iP 传入域名
     * @return 布尔
     */
    public static boolean isIp(String iP) {
        boolean b = false;
        iP = trimSpaces(iP);
        if (iP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = iP.split("\\.");
            if (Integer.parseInt(s[0]) < 255) {
                if (Integer.parseInt(s[1]) < 255) {
                    if (Integer.parseInt(s[2]) < 255) {
                        if (Integer.parseInt(s[3]) < 255) {
                            b = true;
                        }
                    }
                }
            }
        }
        return b;
    }

}
