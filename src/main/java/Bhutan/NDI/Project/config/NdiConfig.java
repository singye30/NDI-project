package Bhutan.NDI.Project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NdiConfig {

    @Value("${ndi.auth.url}")
    private String authUrl;

    @Value("${ndi.client.id}")
    private String clientId;

    @Value("${ndi.client.secret}")
    private String clientSecret;

    @Value("${ndi.verifier.url}")
    private String verifierUrl;

    @Value("${ndi.foundation.schema}")
    private String foundationSchema;


    public String getAuthUrl() {
        return authUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getVerifierUrl() {
        return verifierUrl;
    }

    public String getFoundationSchema() {
        return foundationSchema;
    }
}