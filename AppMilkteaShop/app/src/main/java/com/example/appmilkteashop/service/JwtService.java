package com.example.appmilkteashop.service;

import com.auth0.android.jwt.JWT;

public class JwtService {
    public static String getSubjectFromToken(String token) {
        JWT jwt = new JWT(token);
        return jwt.getSubject();
    }

    public static String getRoleFromToken(String token) {
        JWT jwt = new JWT(token);
        return jwt.getClaim("role").asString();
    }
}
