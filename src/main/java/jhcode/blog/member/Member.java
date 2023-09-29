package jhcode.blog.member;

import jakarta.persistence.*;
import jhcode.blog.comment.Comment;
import jhcode.blog.common.BaseTimeEntity;
import jhcode.blog.common.Role;
import jhcode.blog.member.dto.MemberInfoDTO;
import jhcode.blog.member.dto.request.MemberLoginDto;
import jhcode.blog.member.dto.request.MemberUpdateDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor // 기본 생성자
public class Member extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    // 이메일로 로그인함
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role roles;

    @OneToMany(mappedBy = "member")
    public List<Comment> comments = new ArrayList<>();


    //========== 생성자 Builder ============//
    @Builder
    public Member(String email, String password, String username, Role roles) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    //========== Dirty Checking ===========//
    public void update(String password, String username) {
        this.password = password;
        this.username = username;
    }

    //========== to DTO ==========//
    public MemberUpdateDto toMemberUpdateDTO() {
        return MemberUpdateDto.builder()
                .email(this.email)
                .password(this.password)
                .username(this.username)
                .build();
    }

    public MemberLoginDto toMemberLoginDTO(String token) {
        return MemberLoginDto.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }

    public MemberInfoDTO toMemberInfoDTO() {
        return MemberInfoDTO.builder()
                .memberId(this.id)
                .email(this.email)
                .password(this.password)
                .username(this.username)
                .role(this.roles.toString())
                .build();
    }

    //========== UserDetails implements ==========//

    /**
     * Token을 고유한 Email 값으로 생성합니다
     * @return email;
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.roles.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
