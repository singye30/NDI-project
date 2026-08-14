package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.services.NdiVerificationStore;
import Bhutan.NDI.Project.services.NdiVerificationStore.VerificationResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NdiLoginSuccessController {

    private final NdiVerificationStore verificationStore;

    public NdiLoginSuccessController(
            NdiVerificationStore verificationStore) {

        this.verificationStore = verificationStore;
    }

    @GetMapping("/ndi-success")
    public String success(
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
        // SEND DATA TO JSP
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