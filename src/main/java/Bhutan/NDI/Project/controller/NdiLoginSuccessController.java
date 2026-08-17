package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.services.NdiVerificationStore;
import Bhutan.NDI.Project.services.NdiVerificationStore.VerificationResult;
import Bhutan.NDI.Project.services.NdiClientService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class NdiLoginSuccessController {

    private final NdiVerificationStore verificationStore;
    private final NdiClientService clientService;

    public NdiLoginSuccessController(
            NdiVerificationStore verificationStore,
            NdiClientService clientService) {

        this.verificationStore = verificationStore;
        this.clientService = clientService;
    }

    @GetMapping("/ndi-success")
    public Object success(
            @RequestParam String threadId,
            Model model) {

        VerificationResult result = verificationStore.getResult(
                threadId);

        // =====================================================
        // NO RESULT
        // =====================================================

        if (result == null
                || !result.isVerified()) {

            return "redirect:/";
        }

        // =====================================================
        // GET CLIENT ID
        // =====================================================

        String clientId = verificationStore.getClientId(threadId);

        // =====================================================
        // CHECK IF EXTERNAL REDIRECT IS NEEDED
        // =====================================================

        if (clientId != null && !clientId.isBlank()) {

            String redirectUrl = clientService.getClientRedirectUrl(clientId);

            if (redirectUrl != null && !redirectUrl.isBlank()) {

                // Redirect external project with user data
                String callbackUrl = redirectUrl + 
                        "?threadId=" + threadId +
                        "&idNumber=" + result.getIdNumber() +
                        "&fullName=" + result.getFullName() +
                        "&verified=true";

                return new RedirectView(callbackUrl);
            }
        }

        // =====================================================
        // SEND DATA TO JSP (FOR INTERNAL USE)
        // =====================================================

        model.addAttribute(
                "idNumber",
                result.getIdNumber());

        model.addAttribute(
                "fullName",
                result.getFullName());

        model.addAttribute(
                "relationshipDid",
                result.getRelationshipDid());

        model.addAttribute(
                "threadId",
                threadId);

        return "ndi-success";
    }
}