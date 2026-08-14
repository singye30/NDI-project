package Bhutan.NDI.Project.services;

import Bhutan.NDI.Project.config.NdiConfig;
import Bhutan.NDI.Project.dto.NdiAuthRequest;
import Bhutan.NDI.Project.dto.NdiAuthResponse;
import Bhutan.NDI.Project.dto.ProofRequestPayload;
import Bhutan.NDI.Project.dto.ProofRequestResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NdiService {

    private final NdiConfig config;

    private final RestTemplate restTemplate =
            new RestTemplate();

    public NdiService(NdiConfig config) {
        this.config = config;
    }

    /**
     * Get NDI access token
     */
    public String getAccessToken() {

        System.out.println("================================");
        System.out.println("Calling NDI Authentication API");
        System.out.println("================================");

        NdiAuthRequest request =
                new NdiAuthRequest();

        request.setClientId(
                config.getClientId()
        );

        request.setClientSecret(
                config.getClientSecret()
        );

        request.setGrantType(
                "client_credentials"
        );

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<NdiAuthRequest> entity =
                new HttpEntity<>(
                        request,
                        headers
                );

        ResponseEntity<NdiAuthResponse> response =
                restTemplate.postForEntity(
                        config.getAuthUrl(),
                        entity,
                        NdiAuthResponse.class
                );

        if (response.getBody() == null) {

            throw new RuntimeException(
                    "Empty response from NDI authentication API"
            );
        }

        String accessToken =
                response
                        .getBody()
                        .getAccessToken();

        if (accessToken == null ||
                accessToken.isBlank()) {

            throw new RuntimeException(
                    "NDI access token is empty"
            );
        }

        System.out.println(
                "NDI authentication successful"
        );

        return accessToken;
    }


    /**
     * Create NDI Proof Request
     */
    public ProofRequestResponse createProofRequest(
            ProofRequestPayload payload
    ) {

        System.out.println("================================");
        System.out.println("Creating NDI Proof Request");
        System.out.println("================================");

        /*
         * Get access token
         */
        String token =
                getAccessToken();


        /*
         * Prepare headers
         */
        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        headers.setBearerAuth(token);


        /*
         * Prepare request
         */
        HttpEntity<ProofRequestPayload> entity =
                new HttpEntity<>(
                        payload,
                        headers
                );


        /*
         * Call NDI Verifier API
         */
        ResponseEntity<ProofRequestResponse> response =
                restTemplate.postForEntity(
                        config.getVerifierUrl(),
                        entity,
                        ProofRequestResponse.class
                );


        /*
         * Validate response
         */
        if (response.getBody() == null) {

            throw new RuntimeException(
                    "Empty response from NDI proof request API"
            );
        }


        ProofRequestResponse proofResponse =
                response.getBody();


        if (proofResponse.getData() == null) {

            throw new RuntimeException(
                    "NDI proof response does not contain data"
            );
        }


        /*
         * Print only useful information
         */
        System.out.println(
                "NDI Proof Request Created"
        );

        System.out.println(
                "Proof Request URL: "
                        + proofResponse
                        .getData()
                        .getProofRequestURL()
        );


        System.out.println(
                "================================"
        );


        return proofResponse;
    }
}