package Bhutan.NDI.Project.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NdiVerificationStore {

    private final Map<String, VerificationResult> results = new ConcurrentHashMap<>();

    public void saveVerifiedUser(
            String threadId,
            String idNumber,
            String fullName,
            String relationshipDid,
            String holderDid) {

        VerificationResult result = new VerificationResult();

        result.setVerified(true);
        result.setIdNumber(idNumber);
        result.setFullName(fullName);
        result.setRelationshipDid(relationshipDid);
        result.setHolderDid(holderDid);

        results.put(threadId, result);
    }

    public void saveRejected(String threadId) {

        VerificationResult result = new VerificationResult();

        result.setVerified(false);

        results.put(threadId, result);
    }

    public VerificationResult getResult(String threadId) {

        return results.get(threadId);
    }

    public static class VerificationResult {

        private boolean verified;

        private String idNumber;

        private String fullName;

        private String relationshipDid;

        private String holderDid;

        public boolean isVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getRelationshipDid() {
            return relationshipDid;
        }

        public void setRelationshipDid(String relationshipDid) {
            this.relationshipDid = relationshipDid;
        }

        public String getHolderDid() {
            return holderDid;
        }

        public void setHolderDid(String holderDid) {
            this.holderDid = holderDid;
        }
    }
}