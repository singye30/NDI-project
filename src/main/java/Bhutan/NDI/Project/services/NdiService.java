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

import java.util.List;

@Service
public class NdiService {

    private final NdiConfig config;
    private final RestTemplate restTemplate;

    public NdiService(NdiConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Authenticate with Bhutan NDI.
     */
    private String getAccessToken() {

        NdiAuthRequest request = new NdiAuthRequest();

        request.setClientId(
                config.getClientId()
        );

        request.setClientSecret(
                config.getClientSecret()
        );

        request.setGrantType(
                "client_credentials"
        );

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        headers.setAccept(
                List.of(MediaType.APPLICATION_JSON)
        );

        HttpEntity<NdiAuthRequest> entity =
                new HttpEntity<>(request, headers);

        System.out.println(
                "======================================"
        );

        System.out.println(
                "NDI Authentication"
        );

        ResponseEntity<NdiAuthResponse> response =
                restTemplate.postForEntity(
                        config.getAuthUrl(),
                        entity,
                        NdiAuthResponse.class
                );

        if (response.getBody() == null) {
            throw new RuntimeException(
                    "Empty response from NDI authentication"
            );
        }

        String accessToken =
                response.getBody().getAccessToken();

        if (accessToken == null ||
                accessToken.isBlank()) {

            throw new RuntimeException(
                    "NDI access token was not returned"
            );
        }

        System.out.println(
                "NDI authentication successful"
        );

        return accessToken;
    }

    /**
     * Create NDI proof request.
     */
    public ProofRequestResponse createProofRequest(
            ProofRequestPayload payload) {

        try {

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "Creating NDI Proof Request"
            );

            String token = getAccessToken();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            headers.setAccept(
                    List.of(MediaType.APPLICATION_JSON)
            );

            headers.setBearerAuth(token);

            HttpEntity<ProofRequestPayload> entity =
                    new HttpEntity<>(
                            payload,
                            headers
                    );

            ResponseEntity<ProofRequestResponse> response =
                    restTemplate.postForEntity(
                            config.getVerifierUrl(),
                            entity,
                            ProofRequestResponse.class
                    );

            if (response.getBody() == null) {

                throw new RuntimeException(
                        "Empty response from NDI verifier"
                );
            }

            ProofRequestResponse result =
                    response.getBody();

            if (result.getData() == null) {

                throw new RuntimeException(
                        "NDI verifier response contains no data"
                );
            }

            System.out.println(
                    "NDI Proof Request created successfully"
            );

            System.out.println(
                    "Thread ID: "
                            + result.getData()
                            .getProofRequestThreadId()
            );

            System.out.println(
                    "QR URL: "
                            + result.getData()
                            .getProofRequestURL()
            );

            return result;

        } catch (Exception e) {

            System.err.println(
                    "Failed to create NDI proof request"
            );

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to create NDI proof request",
                    e
            );
        }
    }
}