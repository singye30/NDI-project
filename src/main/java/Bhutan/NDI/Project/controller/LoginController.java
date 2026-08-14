package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.config.NdiConfig;
import Bhutan.NDI.Project.dto.ProofRequestPayload;
import Bhutan.NDI.Project.dto.ProofRequestResponse;
import Bhutan.NDI.Project.dto.Restriction;
import Bhutan.NDI.Project.services.NdiService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    private final NdiService ndiService;

    private final NdiConfig config;

    public LoginController(
            NdiService ndiService,
            NdiConfig config) {

        this.ndiService = ndiService;
        this.config = config;
    }

    @GetMapping("/")
    public String login(Model model) {

        try {

            // ==========================================
            // Create Proof Request
            // ==========================================

            ProofRequestPayload payload = new ProofRequestPayload();

            payload.setProofName(
                    "Verify Foundational ID");

            // ==========================================
            // ID Number
            // ==========================================

            ProofRequestPayload.ProofAttribute idAttr = new ProofRequestPayload.ProofAttribute();

            idAttr.setName(
                    "ID Number");

            Restriction idRestriction = new Restriction(
                    config.getFoundationSchema());

            idAttr.setRestrictions(
                    List.of(idRestriction));

            // ==========================================
            // Full Name
            // ==========================================

            ProofRequestPayload.ProofAttribute nameAttr = new ProofRequestPayload.ProofAttribute();

            nameAttr.setName(
                    "Full Name");

            Restriction nameRestriction = new Restriction(
                    config.getFoundationSchema());

            nameAttr.setRestrictions(
                    List.of(nameRestriction));

            // ==========================================
            // Add Attributes
            // ==========================================

            payload.setProofAttributes(
                    List.of(
                            idAttr,
                            nameAttr));

            payload.setPurpose(
                    "login");

            // ==========================================
            // Call NDI
            // ==========================================

            System.out.println(
                    "======================================");

            System.out.println(
                    "Creating NDI Proof Request");

            ProofRequestResponse response = ndiService.createProofRequest(
                    payload);

            // ==========================================
            // Get QR URL
            // ==========================================

            String qrUrl = response
                    .getData()
                    .getProofRequestURL();

            // ==========================================
            // Send QR URL to JSP
            // ==========================================

            model.addAttribute(
                    "qrUrl",
                    qrUrl);

            model.addAttribute(
                    "threadId",
                    response
                            .getData()
                            .getProofRequestThreadId());

            model.addAttribute(
                    "deepLink",
                    response
                            .getData()
                            .getDeepLinkURL());

            System.out.println(
                    "======================================");

            System.out.println(
                    "QR URL sent to login.jsp:");

            System.out.println(
                    qrUrl);

            System.out.println(
                    "======================================");

            return "login";

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Failed to initialize NDI login.");

            return "login";
        }
    }
}