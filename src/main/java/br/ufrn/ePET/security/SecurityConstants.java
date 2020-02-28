package br.ufrn.ePET.security;

import java.util.concurrent.TimeUnit;

public class SecurityConstants {

    static final String SECRET = "";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/sign-in/";
    static final Long EXPIRATION_TIME = 10800000L;

}
