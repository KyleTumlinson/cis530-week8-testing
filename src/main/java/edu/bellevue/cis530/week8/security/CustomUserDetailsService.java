/*
    Professor provided citation
 */

package edu.bellevue.cis530.week8.security;

import edu.bellevue.cis530.week8.entity.Member;
import edu.bellevue.cis530.week8.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // Load a UserDetails object by getting a member through our repository
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load a member by username from members table
        Member member =
                memberRepository.findByUsername(username).orElseThrow(
                        () -> new UsernameNotFoundException("Member not found: " + username));

        String[] authorities = {member.getRole()};
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .disabled(!Boolean.TRUE.equals(member.isEnabled()))
                .authorities(authorities)
                .build();

    }
}
