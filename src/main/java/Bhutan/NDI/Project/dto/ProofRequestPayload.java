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

    public static class ProofAttribute {

        private String name;

        private List<Restriction> restrictions;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Restriction> getRestrictions() {
            return restrictions;
        }

        public void setRestrictions(
                List<Restriction> restrictions) {
            this.restrictions = restrictions;
        }
    }
}