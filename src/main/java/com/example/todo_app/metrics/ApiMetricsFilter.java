package com.example.todo_app.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiMetricsFilter implements Filter {

    private final MeterRegistry meterRegistry;

    public ApiMetricsFilter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();

        if (uri.startsWith("/api/todos")) {
            String simplifiedUri = uri.replaceAll(
                    "/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}",
                    "/{id}"
            );

            meterRegistry.counter("api.calls",
                    "method", method,
                    "endpoint", simplifiedUri
            ).increment();
        }

        chain.doFilter(request, response);
    }
}