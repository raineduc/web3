package raineduc.web3.filters;

import raineduc.web3.beans.CurrentDateBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimezoneFilter implements Filter {
    @Inject
    private CurrentDateBean currentDateBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length > 0) {
            Optional<Cookie> cookieOpt = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("TIMEZONE_COOKIE"))
                    .findFirst();
            if (cookieOpt.isPresent()) {
                String timezoneID = "GMT";
                int offset = -1 * Integer.parseInt(cookieOpt.get().getValue()) / 60;
                if (offset >= 0) {
                    timezoneID = timezoneID + "+" + String.valueOf(offset);
                } else {
                    timezoneID = timezoneID + String.valueOf(offset);
                }
                currentDateBean.setTimezone(timezoneID);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
