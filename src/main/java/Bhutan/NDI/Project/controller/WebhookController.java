package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.services.NdiService;

import org.springframework.web.bind.annotation.*;

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
}