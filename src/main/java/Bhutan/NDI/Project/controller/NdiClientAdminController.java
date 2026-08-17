package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.entity.NdiClientDetail;
import Bhutan.NDI.Project.services.NdiClientService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ndi/admin/clients")
public class NdiClientAdminController {

    private final NdiClientService clientService;

    public NdiClientAdminController(NdiClientService clientService) {
        this.clientService = clientService;
    }

    // =========================================================
    // REGISTER NEW CLIENT
    // =========================================================

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(
            @RequestBody ClientRegistrationRequest request) {

        try {
            // Validate input
            if (request.getClientId() == null || request.getClientId().isBlank()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "clientId is required"));
            }

            if (request.getClientName() == null || request.getClientName().isBlank()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "clientName is required"));
            }

            if (request.getClientRedirectUrl() == null || request.getClientRedirectUrl().isBlank()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "clientRedirectUrl is required"));
            }

            // Check if client already exists
            var existingClient = clientService.getClient(request.getClientId());
            if (existingClient.isPresent()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Client with this ID already exists"));
            }

            // Create new client
            NdiClientDetail client = clientService.createClient(
                request.getClientId(),
                request.getClientName(),
                request.getClientRedirectUrl()
            );

            return ResponseEntity.ok(Map.of(
                "message", "Client registered successfully",
                "clientId", client.getClientId(),
                "clientName", client.getClientName()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", e.getMessage()));
        }
    }

    // =========================================================
    // UPDATE CLIENT
    // =========================================================

    @PutMapping("/{clientId}")
    public ResponseEntity<?> updateClient(
            @PathVariable String clientId,
            @RequestBody ClientRegistrationRequest request) {

        try {
            NdiClientDetail client = clientService.updateClient(
                clientId,
                request.getClientName(),
                request.getClientRedirectUrl()
            );

            if (client == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(Map.of(
                "message", "Client updated successfully",
                "clientId", client.getClientId()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", e.getMessage()));
        }
    }

    // =========================================================
    // GET CLIENT DETAILS
    // =========================================================

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClient(@PathVariable String clientId) {

        try {
            var clientOpt = clientService.getClient(clientId);

            if (clientOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            NdiClientDetail client = clientOpt.get();

            return ResponseEntity.ok(Map.of(
                "clientId", client.getClientId(),
                "clientName", client.getClientName(),
                "clientRedirectUrl", client.getClientRedirectUrl(),
                "clientStatus", client.getClientStatus()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", e.getMessage()));
        }
    }

    // =========================================================
    // DEACTIVATE CLIENT
    // =========================================================

    @PostMapping("/{clientId}/deactivate")
    public ResponseEntity<?> deactivateClient(@PathVariable String clientId) {

        try {
            clientService.deactivateClient(clientId);

            return ResponseEntity.ok(Map.of(
                "message", "Client deactivated successfully",
                "clientId", clientId
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", e.getMessage()));
        }
    }

    // =========================================================
    // REQUEST BODY DTO
    // =========================================================

    public static class ClientRegistrationRequest {

        private String clientId;
        private String clientName;
        private String clientRedirectUrl;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientRedirectUrl() {
            return clientRedirectUrl;
        }

        public void setClientRedirectUrl(String clientRedirectUrl) {
            this.clientRedirectUrl = clientRedirectUrl;
        }
    }
}
