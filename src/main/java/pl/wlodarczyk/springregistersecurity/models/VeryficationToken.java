package pl.wlodarczyk.springregistersecurity.models;

import javax.persistence.*;

@Entity
public class VeryficationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String value;
    @OneToOne
    private User user;
    private boolean isAdmin=false;

    public VeryficationToken() {
    }

    public VeryficationToken(String value, User user) {
        this.user = user;
        this.value = value;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
