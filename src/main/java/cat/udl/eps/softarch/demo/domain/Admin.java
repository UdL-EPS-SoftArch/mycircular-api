package cat.udl.eps.softarch.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Entity;
import java.util.Collection;

@Entity
@Data
public class Admin extends User {
    @Override
    @JsonValue(value = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
    }
}
