package com.imooc.user.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.imooc.thrift.user.dto.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public abstract class LoginFilter implements Filter {

    private static Cache<String, UserDTO> cache =
            CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(3, TimeUnit.MINUTES).build();

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getParameter("token");
        // token或许在cookie中
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        UserDTO userDTO = null;
        if (StringUtils.isNotBlank(token)) {
            userDTO = cache.getIfPresent(token);
            if (userDTO == null) {
                userDTO = requestUserInfo(token);
                if (userDTO != null) {
                    cache.put(token, userDTO);
                }
            }
        }
        if (userDTO == null) {
//            response.sendRedirect("http://127.0.0.1:8082/user/login");
            response.sendRedirect("http://www.isacc.com/user/login");
            return;
        }

        login(request, response, userDTO);
        filterChain.doFilter(request, response);
    }

    private UserDTO requestUserInfo(String token) {
        String url = "http://127.0.0.1:8082/user/authentication";
//        String url = "http://user-edge-service:8082/user/authentication";
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("token", token);
        InputStream inputStream = null;
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("request user info failed! StatusLine:" + response.getStatusLine());
            }
            inputStream = response.getEntity().getContent();
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            UserDTO userDTO = new ObjectMapper().readValue(sb.toString(), UserDTO.class);
            return userDTO;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void destroy() {

    }
}
