package jarvizz.project.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles roles = Roles.ROLE_USER;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Food> basket;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles.name()));
        return authorities;
    }
    private boolean isAccountNonExpired = true;
    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    private boolean isAccountNonLocked = true;
    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    private boolean isCredentialsNonExpired = true;
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    private boolean isEnabled = true;
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
