package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.services.NdiVerificationStore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class NdiWebhookController {

        private final ObjectMapper objectMapper;

        private final NdiVerificationStore verificationStore;

        public NdiWebhookController(
                        NdiVerificationStore verificationStore) {

                this.objectMapper = new ObjectMapper();

                this.verificationStore = verificationStore;
        }

        @PostMapping
        public ResponseEntity<Void> receiveWebhook(
                        @RequestBody String body) {

                System.out.println();
                System.out.println(
                                "========================================");

                System.out.println(
                                "NDI WEBHOOK RECEIVED");

                System.out.println(
                                "========================================");

                System.out.println("Raw Webhook:");
                System.out.println(body);

                try {

                        JsonNode root = objectMapper.readTree(body);

                        String type = root.path("type")
                                        .asText();

                        String threadId = root.path("thid")
                                        .asText();

                        System.out.println(
                                        "Webhook Type: "
                                                        + type);

                        System.out.println(
                                        "Thread ID: "
                                                        + threadId);

                        // =====================================================
                        // PROOF REJECTED
                        // =====================================================

                        if ("present-proof/rejected"
                                        .equals(type)) {

                                System.out.println(
                                                "NDI PROOF REJECTED");

                                verificationStore.saveRejected(
                                                threadId);

                                return ResponseEntity
                                                .status(202)
                                                .build();
                        }

                        // =====================================================
                        // PROOF PRESENTATION RESULT
                        // =====================================================

                        if ("present-proof/presentation-result"
                                        .equals(type)) {

                                String verificationResult = root.path(
                                                "verification_result").asText();

                                System.out.println(
                                                "Verification Result: "
                                                                + verificationResult);

                                // =================================================
                                // PROOF VALIDATED
                                // =================================================

                                if ("ProofValidated"
                                                .equals(verificationResult)) {

                                        JsonNode revealedAttrs = root.path(
                                                        "requested_presentation").path(
                                                                        "revealed_attrs");

                                        String idNumber = getAttributeValue(
                                                        revealedAttrs,
                                                        "ID Number");

                                        String fullName = getAttributeValue(
                                                        revealedAttrs,
                                                        "Full Name");

                                        String relationshipDid = root.path(
                                                        "relationship_did").asText();

                                        String holderDid = root.path(
                                                        "holder_did").asText();

                                        System.out.println();
                                        System.out.println(
                                                        "========================================");

                                        System.out.println(
                                                        "NDI PROOF VALIDATED");

                                        System.out.println(
                                                        "========================================");

                                        System.out.println(
                                                        "ID Number: "
                                                                        + idNumber);

                                        System.out.println(
                                                        "Full Name: "
                                                                        + fullName);

                                        System.out.println(
                                                        "Relationship DID: "
                                                                        + relationshipDid);

                                        System.out.println(
                                                        "Holder DID: "
                                                                        + holderDid);

                                        System.out.println(
                                                        "Thread ID: "
                                                                        + threadId);

                                        System.out.println(
                                                        "========================================");

                                        verificationStore.saveVerifiedUser(
                                                        threadId,
                                                        idNumber,
                                                        fullName,
                                                        relationshipDid,
                                                        holderDid);
                                }
                        }

                } catch (Exception e) {

                        System.err.println(
                                        "Error processing NDI webhook");

                        e.printStackTrace();
                }

                // NDI expects HTTP 202
                return ResponseEntity
                                .status(202)
                                .build();
        }

        private String getAttributeValue(
                        JsonNode revealedAttrs,
                        String attributeName) {

                JsonNode attribute = revealedAttrs.path(
                                attributeName);

                if (attribute.isArray()
                                && attribute.size() > 0) {

                        return attribute
                                        .get(0)
                                        .path("value")
                                        .asText();
                }

                return null;
        }
}