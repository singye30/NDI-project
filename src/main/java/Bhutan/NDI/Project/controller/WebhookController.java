package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.dto.ProofAttribute;
import Bhutan.NDI.Project.dto.ProofRequestPayload;
import Bhutan.NDI.Project.dto.ProofRequestResponse;
import Bhutan.NDI.Project.dto.Restriction;
import Bhutan.NDI.Project.services.NdiService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ndi")
public class WebhookController {

    private final NdiService ndiService;

    public WebhookController(
            NdiService ndiService) {

        this.ndiService = ndiService;
    }

    // =========================================================
    // REGISTER WEBHOOK
    // =========================================================

    @PostMapping("/register-webhook")
    public String registerWebhook() {

        return ndiService.registerWebhook();
    }

    // =========================================================
    // SUBSCRIBE WEBHOOK
    // =========================================================

    @PostMapping("/subscribe-webhook")
    public String subscribeWebhook(
            @RequestParam String threadId) {

        return ndiService.subscribeWebhook(
                threadId);
    }

    // =========================================================
    // UNSUBSCRIBE WEBHOOK
    // =========================================================

    @PostMapping("/unsubscribe-webhook")
    public String unsubscribeWebhook(
            @RequestParam String threadId) {

        return ndiService.unsubscribeWebhook(
                threadId);
    }

    // =========================================================
    // CREATE LOGIN REQUEST - FOR EXTERNAL PROJECTS
    // =========================================================

    @GetMapping("/api/login-request")
    public ResponseEntity<?> createLoginRequest() {
        try {
            String schemaName = "https://dev-schema.ngotag.com/schemas/"
                    + "c7952a0a-e9b5-4a4b-a714-"
                    + "1e5d0a1ae076";

            ProofAttribute idNumber = new ProofAttribute(
                    "ID Number",
                    List.of(new Restriction(schemaName)));

            ProofAttribute fullName = new ProofAttribute(
                    "Full Name",
                    List.of(new Restriction(schemaName)));

            ProofRequestPayload payload = new ProofRequestPayload();
            payload.setProofName("Verify Foundational ID");
            payload.setProofAttributes(List.of(idNumber, fullName));
            payload.setPurpose("login");

            ProofRequestResponse response = ndiService.createProofRequest(payload);

            if (response == null || response.getData() == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid response from NDI"));
            }

            String threadId = response.getData().getProofRequestThreadId();
            ndiService.subscribeWebhook(threadId);

            return ResponseEntity.ok(Map.of(
                "qrUrl", response.getData().getProofRequestURL(),
                "deepLinkUrl", response.getData().getDeepLinkURL(),
                "threadId", threadId
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", e.getMessage()));
        }
    }
}