package Bhutan.NDI.Project.dto;

public class ProofRequestResponse {

    private int statusCode;

    private String message;

    private Data data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        private String proofRequestName;

        private String proofRequestThreadId;

        private String deepLinkURL;

        private String proofRequestURL;

        public String getProofRequestName() {
            return proofRequestName;
        }

        public void setProofRequestName(
                String proofRequestName
        ) {
            this.proofRequestName = proofRequestName;
        }

        public String getProofRequestThreadId() {
            return proofRequestThreadId;
        }

        public void setProofRequestThreadId(
                String proofRequestThreadId
        ) {
            this.proofRequestThreadId = proofRequestThreadId;
        }

        public String getDeepLinkURL() {
            return deepLinkURL;
        }

        public void setDeepLinkURL(
                String deepLinkURL
        ) {
            this.deepLinkURL = deepLinkURL;
        }

        public String getProofRequestURL() {
            return proofRequestURL;
        }

        public void setProofRequestURL(
                String proofRequestURL
        ) {
            this.proofRequestURL = proofRequestURL;
        }
    }
}