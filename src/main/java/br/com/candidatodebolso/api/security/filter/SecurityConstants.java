package br.com.candidatodebolso.api.security.filter;

class SecurityConstants {

    static final String SECRET = "CandidatoDeBolso";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final long EXPIRATION_TIME = 86400000L;
}
