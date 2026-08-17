package Bhutan.NDI.Project.services;

import Bhutan.NDI.Project.config.NdiConfig;
import Bhutan.NDI.Project.dto.NdiAuthRequest;
import Bhutan.NDI.Project.dto.NdiAuthResponse;
import Bhutan.NDI.Project.dto.ProofRequestPayload;
import Bhutan.NDI.Project.dto.ProofRequestResponse;
import Bhutan.NDI.Project.dto.WebhookRegistrationRequest;
import Bhutan.NDI.Project.dto.WebhookSubscribeRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NdiService {

        private final NdiConfig config;

        private final RestTemplate restTemplate;

        public NdiService(NdiConfig config) {

                this.config = config;

                this.restTemplate = new RestTemplate();
        }

        // =========================================================
        // GET NDI ACCESS TOKEN
        // =========================================================

        public String getAccessToken() {

                System.out.println();
                System.out.println(
                                "========================================");

                System.out.println(
                                "CALLING NDI AUTHENTICATION API");

                System.out.println(
                                "========================================");

                NdiAuthRequest request = new NdiAuthRequest();

                request.setClientId(
                                config.getClientId());

                request.setClientSecret(
                                config.getClientSecret());

                request.setGrantType(
                                "client_credentials");

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(
                                MediaType.APPLICATION_JSON);

                HttpEntity<NdiAuthRequest> entity = new HttpEntity<>(
                                request,
                                headers);

                ResponseEntity<NdiAuthResponse> response = restTemplate.postForEntity(
                                config.getAuthUrl(),
                                entity,
                                NdiAuthResponse.class);

                if (response.getBody() == null) {

                        throw new RuntimeException(
                                        "Empty response from NDI authentication API");
                }

                String accessToken = response.getBody()
                                .getAccessToken();

                if (accessToken == null
                                || accessToken.isBlank()) {

                        throw new RuntimeException(
                                        "NDI access token is empty");
                }

                System.out.println(
                                "NDI authentication successful");

                return accessToken;
        }

        // =========================================================
        // CREATE PROOF REQUEST
        // =========================================================

        public ProofRequestResponse createProofRequest(
                        ProofRequestPayload payload) {

                System.out.println();
                System.out.println(
                                "========================================");

                System.out.println(
                                "CREATING NDI PROOF REQUEST");

                System.out.println(
                                "========================================");

                String token = getAccessToken();

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(
                                MediaType.APPLICATION_JSON);

                headers.setBearerAuth(
                                token);

                HttpEntity<ProofRequestPayload> entity = new HttpEntity<>(
                                payload,
                                headers);

                ResponseEntity<ProofRequestResponse> response = restTemplate.postForEntity(
                                config.getVerifierUrl(),
                                entity,
                                ProofRequestResponse.class);

                if (response.getBody() == null) {

                        throw new RuntimeException(
                                        "Empty response from NDI proof request API");
                }

                ProofRequestResponse proofResponse = response.getBody();

                if (proofResponse.getData() == null) {

                        throw new RuntimeException(
                                        "NDI proof response does not contain data");
                }

                System.out.println(
                                "NDI Proof Request Created");

                System.out.println(
                                "Proof Request URL: "
                                                + proofResponse
                                                                .getData()
                                                                .getProofRequestURL());

                System.out.println(
                                "Deep Link URL: "
                                                + proofResponse
                                                                .getData()
                                                                .getDeepLinkURL());

                System.out.println(
                                "Proof Request Thread ID: "
                                                + proofResponse
                                                                .getData()
                                                                .getProofRequestThreadId());

                System.out.println(
                                "========================================");

                return proofResponse;
        }

        // =========================================================
        // REGISTER WEBHOOK
        // =========================================================

        public String registerWebhook() {

                System.out.println();
                System.out.println(
                                "========================================");

                System.out.println(
                                "REGISTERING NDI WEBHOOK");

                System.out.println(
                                "========================================");

                String token = getAccessToken();

                WebhookRegistrationRequest request = new WebhookRegistrationRequest();

                request.setWebhookId(
                                config.getWebhookId());

                request.setWebhookURL(
                                config.getWebhookUrl());

                // =====================================================
                // OAUTH2 VERSION 1
                // =====================================================

                WebhookRegistrationRequest.Authentication authentication = new WebhookRegistrationRequest.Authentication();

                authentication.setType(
                                "OAuth2");

                authentication.setVersion(
                                "v1");

                Map<String, Object> authenticationData = new HashMap<>();

                authenticationData.put(
                                "url",
                                config.getAuthUrl());

                authenticationData.put(
                                "grant_type",
                                "client_credentials");

                authenticationData.put(
                                "client_id",
                                config.getClientId());

                authenticationData.put(
                                "client_secret",
                                config.getClientSecret());

                authentication.setData(
                                authenticationData);

                request.setAuthentication(
                                authentication);

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(
                                MediaType.APPLICATION_JSON);

                headers.setBearerAuth(
                                token);

                HttpEntity<WebhookRegistrationRequest> entity = new HttpEntity<>(
                                request,
                                headers);

                ResponseEntity<String> response = restTemplate.postForEntity(
                                config.getWebhookRegisterUrl(),
                                entity,
                                String.class);

                System.out.println(
                                "NDI WEBHOOK REGISTRATION RESPONSE:");

                System.out.println(
                                response.getBody());

                System.out.println(
                                "========================================");

                return response.getBody();
        }

        // =========================================================
        // SUBSCRIBE WEBHOOK
        // =========================================================

        public String subscribeWebhook(
                        String threadId) {

                System.out.println();
                System.out.println(
                                "========================================");

                System.out.println(
                                "SUBSCRIBING TO NDI WEBHOOK");

                System.out.println(
                                "========================================");

                System.out.println(
                                "Webhook ID: "
                                                + config.getWebhookId());

                System.out.println(
                                "Thread ID: "
                                                + threadId);

                String token = getAccessToken();

                WebhookSubscribeRequest request = new WebhookSubscribeRequest();

                request.setWebhookId(
                                config.getWebhookId());

                request.setThreadId(
                                threadId);

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(
                                MediaType.APPLICATION_JSON);

                headers.setBearerAuth(
                                token);

                HttpEntity<WebhookSubscribeRequest> entity = new HttpEntity<>(
                                request,
                                headers);

                ResponseEntity<String> response = restTemplate.postForEntity(
                                config.getWebhookSubscribeUrl(),
                                entity,
                                String.class);

                System.out.println(
                                "NDI WEBHOOK SUBSCRIPTION RESPONSE:");

                System.out.println(
                                response.getBody());

                System.out.println(
                                "========================================");

                return response.getBody();
        }

        // =========================================================
        // UNSUBSCRIBE WEBHOOK
        // =========================================================

        public String unsubscribeWebhook(
                        String threadId) {

                System.out.println();
                System.out.println(
                                "========================================");

                System.out.println(
                                "UNSUBSCRIBING FROM NDI WEBHOOK");

                System.out.println(
                                "========================================");

                String token = getAccessToken();

                Map<String, String> request = new HashMap<>();

                request.put(
                                "threadId",
                                threadId);

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(
                                MediaType.APPLICATION_JSON);

                headers.setBearerAuth(
                                token);

                HttpEntity<Map<String, String>> entity = new HttpEntity<>(
                                request,
                                headers);

                ResponseEntity<String> response = restTemplate.postForEntity(
                                config.getWebhookUnsubscribeUrl(),
                                entity,
                                String.class);

                System.out.println(
                                "NDI WEBHOOK UNSUBSCRIBE RESPONSE:");

                System.out.println(
                                response.getBody());

                System.out.println(
                                "========================================");

                return response.getBody();
        }
}