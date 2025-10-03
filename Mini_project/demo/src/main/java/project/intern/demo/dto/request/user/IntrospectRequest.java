package project.intern.demo.dto.request.user;

import java.util.Objects;

public class IntrospectRequest {
    private String token;

    @Override
    public String toString() {
        return "IntrospectRequest{" +
                "token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IntrospectRequest that = (IntrospectRequest) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public IntrospectRequest() {
    }

    public IntrospectRequest(String token) {
        this.token = token;
    }
}
