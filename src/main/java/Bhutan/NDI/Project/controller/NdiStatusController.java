package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.services.NdiVerificationStore;
import Bhutan.NDI.Project.services.NdiVerificationStore.VerificationResult;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ndi")
public class NdiStatusController {

    private final NdiVerificationStore verificationStore;

    public NdiStatusController(
            NdiVerificationStore verificationStore) {

        this.verificationStore = verificationStore;
    }

    @GetMapping("/status/{threadId}")
    public ResponseEntity<StatusResponse> getStatus(
            @PathVariable String threadId) {

        VerificationResult result = verificationStore.getResult(
                threadId);

        // =====================================================
        // NOT COMPLETED YET
        // =====================================================

        if (result == null) {

            return ResponseEntity.ok(
                    new StatusResponse(
                            false,
                            false,
                            null,
                            null,
                            null));
        }

        // =====================================================
        // COMPLETED BUT REJECTED
        // =====================================================

        if (!result.isVerified()) {

            return ResponseEntity.ok(
                    new StatusResponse(
                            true,
                            false,
                            null,
                            null,
                            null));
        }

        // =====================================================
        // SUCCESS
        // =====================================================

        return ResponseEntity.ok(
                new StatusResponse(
                        true,
                        true,
                        result.getIdNumber(),
                        result.getFullName(),
                        result.getRelationshipDid()));
    }

    // =========================================================
    // RESPONSE DTO
    // =========================================================

    public static class StatusResponse {

        private boolean completed;

        private boolean verified;

        private String idNumber;

        private String fullName;

        private String relationshipDid;

        public StatusResponse(
                boolean completed,
                boolean verified,
                String idNumber,
                String fullName,
                String relationshipDid) {

            this.completed = completed;
            this.verified = verified;
            this.idNumber = idNumber;
            this.fullName = fullName;
            this.relationshipDid = relationshipDid;
        }

        public boolean isCompleted() {
            return completed;
        }

        public boolean isVerified() {
            return verified;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public String getRelationshipDid() {
            return relationshipDid;
        }
    }
}