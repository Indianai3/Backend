//package com.service.security;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseToken;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.ServletException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Objects;
//
//public class FirebaseTokenFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Remove "Bearer " prefix
//        }
//
//        if(Objects.nonNull(token)) {
//            // Validate the Firebase JWT token
//            try {
//                FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
//
//                // Set Firebase UID as a request header
//                request.setAttribute("Firebase-UID", firebaseToken.getUid());
//
//                // Continue with the filter chain
//                filterChain.doFilter(request, response);
//            } catch (Exception e) {
//                // Token validation failed
//                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
//            }
//        }
//    }
//}
