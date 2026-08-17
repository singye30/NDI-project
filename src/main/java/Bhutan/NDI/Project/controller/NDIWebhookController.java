package Bhutan.NDI.Project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class NDIWebhookController {

    @PostMapping("/ndi/webhook")
    public void receiveWebhook(
            @RequestBody String body
    ) {

        System.out.println("==============================");
        System.out.println("NDI WEBHOOK RECEIVED");
        System.out.println("==============================");

        System.out.println("Webhook Body:");
        System.out.println(body);

        System.out.println("==============================");
    }
}