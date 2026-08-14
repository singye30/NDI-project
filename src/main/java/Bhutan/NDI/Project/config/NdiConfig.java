package Bhutan.NDI.Project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NdiConfig {

    @Value("${ndi.auth.url}")
    private String authUrl;

    @Value("${ndi.verifier.url}")
    private String verifierUrl;

    @Value("${ndi.client.id}")
    private String clientId;

    @Value("${ndi.client.secret}")
    private String clientSecret;

    public String getAuthUrl() {
        return authUrl;
    }

    public String getVerifierUrl() {
        return verifierUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}