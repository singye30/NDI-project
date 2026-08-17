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

    @Value("${ndi.webhook.id}")
    private String webhookId;

    @Value("${ndi.webhook.url}")
    private String webhookUrl;

    @Value("${ndi.webhook.register.url}")
    private String webhookRegisterUrl;

    @Value("${ndi.webhook.subscribe.url}")
    private String webhookSubscribeUrl;

    @Value("${ndi.webhook.unsubscribe.url}")
    private String webhookUnsubscribeUrl;

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

    public String getWebhookId() {
        return webhookId;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getWebhookRegisterUrl() {
        return webhookRegisterUrl;
    }

    public String getWebhookSubscribeUrl() {
        return webhookSubscribeUrl;
    }

    public String getWebhookUnsubscribeUrl() {
        return webhookUnsubscribeUrl;
    }
}