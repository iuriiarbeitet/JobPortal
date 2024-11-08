package com.dev.jobportal.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.dev.jobportal.util.UsersNameRole.*;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        if (getHasRole(JOB_SEEKER, authentication) || getHasRole(RECRUITER, authentication)) {
            response.sendRedirect("/dashboard/");
        }
    }

    private boolean getHasRole(String profileName, Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(profileName));
    }
}
