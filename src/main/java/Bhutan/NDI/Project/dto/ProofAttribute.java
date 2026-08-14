package Bhutan.NDI.Project.dto;

import java.util.List;

public class ProofAttribute {

    private String name;

    private List<Restriction> restrictions;

    public ProofAttribute() {
    }

    public ProofAttribute(
            String name,
            List<Restriction> restrictions
    ) {
        this.name = name;
        this.restrictions = restrictions;
    }

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
            List<Restriction> restrictions
    ) {
        this.restrictions = restrictions;
    }

    @Override
    public String toString() {
        return "ProofAttribute{" +
                "name='" + name + '\'' +
                ", restrictions=" + restrictions +
                '}';
    }
}