package Bhutan.NDI.Project.dto;

public class Restriction {

    private String schema_name;

    public Restriction() {
    }

    public Restriction(String schema_name) {
        this.schema_name = schema_name;
    }

    public String getSchema_name() {
        return schema_name;
    }

    public void setSchema_name(String schema_name) {
        this.schema_name = schema_name;
    }
}