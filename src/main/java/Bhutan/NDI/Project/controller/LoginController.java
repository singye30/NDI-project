package Bhutan.NDI.Project.controller;

import Bhutan.NDI.Project.dto.ProofAttribute;
import Bhutan.NDI.Project.dto.ProofRequestPayload;
import Bhutan.NDI.Project.dto.Restriction;
import Bhutan.NDI.Project.dto.ProofRequestResponse;
import Bhutan.NDI.Project.services.NdiService;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    private final NdiService ndiService;

    public LoginController(NdiService ndiService) {
        this.ndiService = ndiService;
    }

    @GetMapping("/")
    public String login(Model model) {

        try {

            String schemaName =
                    "https://dev-schema.ngotag.com/schemas/"
                    + "c7952a0a-e9b5-4a4b-a714-"
                    + "1e5d0a1ae076";


            ProofAttribute idNumber =
                    new ProofAttribute(
                            "ID Number",
                            List.of(
                                    new Restriction(schemaName)
                            )
                    );


            ProofAttribute fullName =
                    new ProofAttribute(
                            "Full Name",
                            List.of(
                                    new Restriction(schemaName)
                            )
                    );


            ProofRequestPayload payload =
                    new ProofRequestPayload();

            payload.setProofName(
                    "Verify Foundational ID"
            );

            payload.setProofAttributes(
                    List.of(
                            idNumber,
                            fullName
                    )
            );

            payload.setPurpose(
                    "login"
            );


            ProofRequestResponse response =
                    ndiService.createProofRequest(
                            payload
                    );


            String qrUrl =
        
                    response
                            .getData()
                            .getProofRequestURL();
                            
            model.addAttribute(
                "qrUrl", qrUrl
            );

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "REAL NDI QR URL:"
            );

            

            System.out.println(qrUrl);

            System.out.println(
                    "================================"
            );


        } catch (Exception e) {

            e.printStackTrace();

        }

        


        return "login";
    }
}