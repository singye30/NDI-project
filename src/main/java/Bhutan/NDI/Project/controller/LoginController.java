package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.dto.ProofAttribute;
import Bhutan.NDI.Project.dto.ProofRequestPayload;
import Bhutan.NDI.Project.dto.ProofRequestResponse;
import Bhutan.NDI.Project.dto.Restriction;
import Bhutan.NDI.Project.services.NdiService;
import Bhutan.NDI.Project.services.NdiVerificationStore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

        private final NdiService ndiService;
        private final NdiVerificationStore verificationStore;

        public LoginController(NdiService ndiService, NdiVerificationStore verificationStore) {

                this.ndiService = ndiService;
                this.verificationStore = verificationStore;
        }

        // =========================================================
        // LOGIN PAGE
        // =========================================================

        @GetMapping("/")
        public String login(Model model, 
                           @RequestParam(required = false) String redirect) {

                try {

                        // =====================================================
                        // FOUNDATIONAL ID SCHEMA
                        // =====================================================

                        String schemaName = "https://dev-schema.ngotag.com/schemas/"
                                        + "c7952a0a-e9b5-4a4b-a714-"
                                        + "1e5d0a1ae076";

                        // =====================================================
                        // ID NUMBER
                        // =====================================================

                        ProofAttribute idNumber = new ProofAttribute(
                                        "ID Number",
                                        List.of(
                                                        new Restriction(
                                                                        schemaName)));

                        // =====================================================
                        // FULL NAME
                        // =====================================================

                        ProofAttribute fullName = new ProofAttribute(
                                        "Full Name",
                                        List.of(
                                                        new Restriction(
                                                                        schemaName)));

                        // =====================================================
                        // PROOF REQUEST
                        // =====================================================

                        ProofRequestPayload payload = new ProofRequestPayload();

                        payload.setProofName(
                                        "Verify Foundational ID");

                        payload.setProofAttributes(
                                        List.of(
                                                        idNumber,
                                                        fullName));

                        payload.setPurpose(
                                        "login");

                        // =====================================================
                        // CREATE PROOF REQUEST
                        // =====================================================

                        ProofRequestResponse response = ndiService.createProofRequest(
                                        payload);

                        // =====================================================
                        // CHECK RESPONSE
                        // =====================================================

                        if (response == null
                                        || response.getData() == null) {

                                throw new RuntimeException(
                                                "Invalid response from NDI");
                        }

                        // =====================================================
                        // GET QR URL
                        // =====================================================

                        String qrUrl = response
                                        .getData()
                                        .getProofRequestURL();

                        // =====================================================
                        // GET DEEP LINK
                        // =====================================================

                        String deepLinkUrl = response
                                        .getData()
                                        .getDeepLinkURL();

                        // =====================================================
                        // GET THREAD ID
                        // =====================================================

                        String threadId = response
                                        .getData()
                                        .getProofRequestThreadId();

                        if (threadId == null
                                        || threadId.isBlank()) {

                                throw new RuntimeException(
                                                "NDI proof request thread ID is empty");
                        }

                        // =====================================================
                        // LOG INFORMATION
                        // =====================================================

                        System.out.println();
                        System.out.println(
                                        "========================================");

                        System.out.println(
                                        "NDI LOGIN PROOF REQUEST CREATED");

                        System.out.println(
                                        "========================================");

                        System.out.println(
                                        "QR URL:");

                        System.out.println(qrUrl);

                        System.out.println(
                                        "Deep Link:");

                        System.out.println(deepLinkUrl);

                        System.out.println(
                                        "Thread ID:");

                        System.out.println(threadId);

                        System.out.println(
                                        "========================================");

                        // =====================================================
                        // SUBSCRIBE THREAD TO WEBHOOK
                        // =====================================================

                        String subscriptionResponse = ndiService.subscribeWebhook(
                                        threadId);

                        System.out.println(
                                        "Webhook subscription completed:");

                        System.out.println(
                                        subscriptionResponse);

                        // =====================================================
                        // SEND DATA TO JSP
                        // =====================================================

                        // =====================================================
                        // STORE REDIRECT URL IF PROVIDED
                        // =====================================================

                        if (redirect != null && !redirect.isBlank()) {

                                verificationStore.saveRedirectUrl(
                                                threadId,
                                                redirect);
                        }

                        // =====================================================
                        // SEND DATA TO JSP
                        // =====================================================

                        model.addAttribute(
                                        "qrUrl",
                                        qrUrl);

                        model.addAttribute(
                                        "deepLinkUrl",
                                        deepLinkUrl);

                        model.addAttribute(
                                        "threadId",
                                        threadId);

                } catch (Exception e) {

                        System.err.println();
                        System.err.println(
                                        "========================================");

                        System.err.println(
                                        "NDI LOGIN ERROR");

                        System.err.println(
                                        "========================================");

                        e.printStackTrace();

                        model.addAttribute(
                                        "error",
                                        "Unable to create NDI login request.");
                }

                return "login";
        }
}