package com.kein.ktech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kein.ktech.constant.AuthProvider;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String fullName;
    private String sdt;
    private boolean active;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthProvider authProvider;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    @Column(name = "verification_code")
    private  String verificationCode;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Invoice> invoices;


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public User(String email, String password, String fullName, String sdt, boolean active, AuthProvider authProvider, Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.sdt = sdt;
        this.active = active;
        this.authProvider = authProvider;
        this.roles = roles;
    }

    public User(String mail, String fullName, boolean active, AuthProvider authProvider)
    {
        this.email = mail;
        this.fullName = fullName;
        this.active = active;
        this.authProvider = authProvider;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    public User() {
    }

    public User(String email, String password, String fullName, String sdt, boolean active, AuthProvider authProvider) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.sdt = sdt;
        this.active = active;
        this.authProvider = authProvider;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
