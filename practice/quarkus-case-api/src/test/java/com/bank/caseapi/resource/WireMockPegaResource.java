package com.bank.caseapi.resource;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.util.HashMap;
import java.util.Map;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * Starts an in-process WireMock server standing in for the external Pega
 * case API and rewires {@code quarkus.rest-client.pega-case-api.url} to
 * point at it for the duration of the test.
 */
public class WireMockPegaResource implements QuarkusTestResourceLifecycleManager {

    private static WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        Map<String, String> config = new HashMap<>();
        config.put("quarkus.rest-client.pega-case-api.url", wireMockServer.baseUrl());
        return config;
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    public static WireMockServer getWireMockServer() {
        return wireMockServer;
    }
}
