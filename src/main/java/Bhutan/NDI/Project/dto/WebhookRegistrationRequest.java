package Bhutan.NDI.Project.dto;

import java.util.Map;

public class WebhookRegistrationRequest {

    private String webhookId;

    private String webhookURL;

    private Authentication authentication;

    public String getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(String webhookId) {
        this.webhookId = webhookId;
    }

    public String getWebhookURL() {
        return webhookURL;
    }

    public void setWebhookURL(String webhookURL) {
        this.webhookURL = webhookURL;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(
            Authentication authentication) {

        this.authentication = authentication;
    }

    public static class Authentication {

        private String type;

        private String version;

        private Map<String, Object> data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(
                Map<String, Object> data) {

            this.data = data;
        }
    }
}