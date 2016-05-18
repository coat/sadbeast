package com.sadbeast.web.security;

import io.undertow.security.api.SecurityContext;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class SBAuthenticationCallHandler implements HttpHandler {

    private final HttpHandler next;

    public SBAuthenticationCallHandler(final HttpHandler next) {
        this.next = next;
    }

    /**
     * Only allow the request through if successfully authenticated or if authentication is not required.
     *
     * @see io.undertow.server.HttpHandler#handleRequest(io.undertow.server.HttpServerExchange)
     */
    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        if(exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        Boolean authOptional = exchange.getAttachment(SBAuthenticationConstraintHandler.AUTH_OPTIONAL_ATTACHMENT_KEY);
        if (authOptional != null && authOptional) {
            if(!exchange.isComplete()) {
                next.handleRequest(exchange);
            }
        }
        SecurityContext context = exchange.getSecurityContext();
        if (context.authenticate()) {
            if(!exchange.isComplete()) {
               next.handleRequest(exchange);
            }
        } else {
            exchange.endExchange();
        }
    }

}
