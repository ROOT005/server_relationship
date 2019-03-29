package com.relationship.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.relationship.domain.UserSessionEntity;
import com.relationship.repository.mysql.UserSessionDao;
import com.relationship.utils.message.ResponseResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.LoginException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebFilter(filterName = "jwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Autowired
    private UserSessionDao userSessionDao;

    //放行白名单
    private static List<String> whiteList = new ArrayList<String>();
    static {
        whiteList.add("/login");
        whiteList.add("/register");
        whiteList.add("/testapi");
        whiteList.add("/sendverificationcode");
        whiteList.add("/reset_password");
        whiteList.add("/id_card_image");
        whiteList.add("/logo");
    }

    /**
     *  Reserved claims（保留），它的含义就像是编程语言的保留字一样，属于JWT标准里面规定的一些claim。JWT标准里面定好的claim有：
     iss(Issuser)：代表这个JWT的签发主体；
     sub(Subject)：代表这个JWT的主体，即它的所有人；
     aud(Audience)：代表这个JWT的接收对象；
     exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
     nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
     iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
     jti(JWT ID)：是JWT的唯一标识。
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        MyRequestWrapper request = new MyRequestWrapper((HttpServletRequest)req);
        HttpServletResponse response = (HttpServletResponse) res;
        String thisUrl = request.getRequestURL().toString();
        for (String url : JwtFilter.whiteList) {
            if (thisUrl.indexOf(url) > 0) {
                chain.doFilter(request, response);
                return;
            }
         }
        //等到请求头信息authorization信息
        final String authHeader = request.getHeader("Authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        } else {
            try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new LoginException("未授权");
            }
            final String token = authHeader.substring(7);
                final Claims claims = JwtHelper.parseJWT(token,"MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=");
                if(claims == null){
                    throw new LoginException("授权验证失败");
                }
                String userId = claims.get("user_id").toString();
                String userSession = claims.get("session_key").toString();
                Optional<UserSessionEntity> userSessionEntity;
                userSessionEntity = userSessionDao.findById(Long.valueOf(userId));
                if (!userSessionEntity.get().getSession().toString().equals(userSession)) {
                    throw new LoginException("用户登录已失效");
                }
                long exf = Long.valueOf(claims.get("exp").toString());
                long now = System.currentTimeMillis()/1000;
                if ((exf - now) <= (7200))
                {
                    String str = JwtHelper.createJWT(userId, userSession, 864000 * 1000,
                            "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=");
                    ((HttpServletResponse) res).addHeader("Authorization", "Bearer " + str);
                }
                if (thisUrl.indexOf("/api/file_upload") <= 0) {
                    if ("POST".equals(request.getMethod().toUpperCase())) {
                        String body = HttpHelper.getBodyString(request);
                        if (body.isEmpty()) {
                            body = "{}";
                        }
                        Gson gson = new Gson();
                        Map<String, String> parameters = gson.fromJson(body, new TypeToken<Map<String, String>>() {
                        }.getType());
                        parameters.put("user_id", userId);
                        request.setBody(gson.toJson(parameters));
                    } else if ("GET".equals(request.getMethod().toUpperCase())) {
                        request.addParameter("user_id", userId);
                    }
                }
            } catch (Exception e) {
                response.addHeader("content-type", "application/json;charset=UTF-8");
                ResponseResult result = new ResponseResult(401, e.getMessage(), null);
                Gson gson = new Gson();
                PrintWriter writer = response.getWriter();
                writer.write(gson.toJson(result));
                writer.flush();
                return;
            }
            chain.doFilter(request, response);
        }
    }
}



