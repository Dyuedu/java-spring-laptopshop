package vn.quocdk.laptopshop.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import vn.quocdk.laptopshop.domain.User;
import vn.quocdk.laptopshop.service.UserService;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CustomSuccessHandler implements
        AuthenticationSuccessHandler {
 //add comment
    @Autowired
    private UserService userService;

    protected String determineTargetUrl(final Authentication authentication) {
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_User", "/");
        roleTargetUrlMap.put("ROLE_Admin", "/admin");
        roleTargetUrlMap.put("ROLE_Shipper", "/shipper");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }
        throw new IllegalStateException();
    }

    protected void clearAuthenticationAttributes(final Authentication authentication,
            HttpSession session) {
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        HttpSession session = request.getSession(false);
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        // Get email
        String email = authentication.getName();
        // Query user from database
        User user = userService.getUserByEmail(email);
        session.setAttribute("username", user.getFullName());
        session.setAttribute("avatar", user.getAvatar());
        session.setAttribute("id", user.getId());
        session.setAttribute("email", user.getEmail());
        if (user.getCart() != null) {
            session.setAttribute("cartSum", user.getCart().getSum());
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(authentication, session);
    }

}
