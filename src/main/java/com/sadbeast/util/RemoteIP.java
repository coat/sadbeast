package com.sadbeast.util;

import com.typesafe.config.ConfigFactory;
import io.undertow.attribute.ExchangeAttributes;
import io.undertow.server.HttpServerExchange;

import java.util.List;

public class RemoteIP {
    private RemoteIP() {
    }

    public static final String REAL_IP_HEADER = "X-Real-IP";

    private static final List<String> TRUSTED_PROXIES = ConfigFactory.load().getStringList("web.trustedProxies");

    public static String getIp(HttpServerExchange exchange) {
        String remoteIp = ExchangeAttributes.remoteIp().readAttribute(exchange);
        if (inProxy(exchange)) {
            return exchange.getRequestHeaders().get(REAL_IP_HEADER).getLast();
        }

        return remoteIp;
    }

    public static boolean inProxy(HttpServerExchange exchange) {
        String remoteIp = ExchangeAttributes.remoteIp().readAttribute(exchange);
        return TRUSTED_PROXIES.contains(remoteIp) && exchange.getRequestHeaders().contains(REAL_IP_HEADER);
    }
}
