package Bhutan.NDI.Project.dto;

import java.util.List;

public class ProofRequestPayload {

    private String proofName;

    private List<ProofAttribute> proofAttributes;

    private String purpose;

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public List<ProofAttribute> getProofAttributes() {
        return proofAttributes;
    }

    public void setProofAttributes(
            List<ProofAttribute> proofAttributes) {
        this.proofAttributes = proofAttributes;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}