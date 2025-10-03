package project.intern.demo.dto.response;


public class IntrospectResponse {
    private boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public IntrospectResponse() {
    }

    public IntrospectResponse(boolean valid) {
        this.valid = valid;
    }
}
