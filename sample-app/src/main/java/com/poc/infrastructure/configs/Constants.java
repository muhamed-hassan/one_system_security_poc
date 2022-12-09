package com.poc.infrastructure.configs;

public class Constants {

	private Constants() {}

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    public static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";
    
    public static final String AUTHENTICATION_URI = "/authenticate";
    
    public static final String INVALID_AUTHORIZATION_TOKEN = "Invalid authorization token";
    
    public static final String DEVICE_TYPE_HEADER_KEY = "Device-Type";
    
    public static final String JWT_HEADER_TYPE = "typ";
    public static final String JWT_PAYLOAD_CLAIM = "rol";
    
}
