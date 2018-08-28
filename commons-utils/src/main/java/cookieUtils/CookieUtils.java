package cookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @auther 陈郑游
 * @create 2016/3/20 0020
 * @功能 Cookie 工具类
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public final class CookieUtils {

    /**
     * Description：得到Cookie的值, 不编码
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * Description：得到Cookie的值
     *
     * @param request
     * @param cookieName
     * @param isDecoder
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {

        //获取网站客户的的缓存
        Cookie[] cookieList = request.getCookies();

        if (cookieList == null || cookieName == null) {
            return null;
        }

        String retValue = null;

        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        //编码UTF-8
                        retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        retValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * Description：得到Cookie的值
     *
     * @param request
     * @param cookieName
     * @param encodeString
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {

        //获取网站客户的的缓存
        Cookie[] cookieList = request.getCookies();

        if (cookieList == null || cookieName == null) {
            return null;
        }

        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }


    /**
     * Description：设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @return
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response,
                                 String cookieName, String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }


    /**
     * Description：设置Cookie的值 在指定时间内生效,但不编码
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @return
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * Description：设置Cookie的值,不设置生效时间,但编码
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param isEncode
     * @return
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * Description：设置Cookie的值 在指定时间内生效, 编码参数
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param isEncode
     * @return
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }


    /**
     * Description：设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param encodeString
     * @return
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * Description：删除Cookie带cookie域名
     *
     * @param request
     * @param response
     * @param cookieName
     * @return
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName) {
        doSetCookie(request, response, cookieName, "", -1, false);
    }


    /**
     * Description：设置Cookie的值，并使其在指定时间内生效
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieMaxage cookie生效的最大秒数
     * @param isEncode
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                          String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }

            Cookie cookie = new Cookie(cookieName, cookieValue);

            if (cookieMaxage > 0) {
                cookie.setMaxAge(cookieMaxage);
            }

            if (null != request) {
                //设置域名的cookie
                String domainName = getDomainName(request);
                //控制台输出
                System.out.println(domainName);

                if (!"localhost".equals(domainName)) {
                    cookie.setDomain(domainName);
                }
            }

            cookie.setPath("/");
            response.addCookie(cookie);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description：设置Cookie的值，并使其在指定时间内生效
     *
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage cookie生效的最大秒数
     * @param encodeString
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                          String cookieName, String cookieValue,
                                          int cookieMaxage, String encodeString) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }

            Cookie cookie = new Cookie(cookieName, cookieValue);

            if (cookieMaxage > 0) {
                cookie.setMaxAge(cookieMaxage);
            }

            if (null != request) {
                // 设置域名的cookie
                String domainName = getDomainName(request);
                //控制台输出
                System.out.println(domainName);

                if (!"localhost".equals(domainName)) {
                    cookie.setDomain(domainName);
                }
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description：得到cookie的域名
     *
     * @param request
     */
    private static final String getDomainName(HttpServletRequest request) {

        String domainName = null;
        String serverName = request.getRequestURL().toString();

        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }

}
