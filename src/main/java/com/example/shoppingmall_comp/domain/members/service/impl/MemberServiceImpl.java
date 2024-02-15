package com.example.shoppingmall_comp.domain.members.service.Impl;

import com.example.shoppingmall_comp.domain.members.dto.MemberSignInRequest;
import com.example.shoppingmall_comp.domain.members.dto.MemberSignInResponse;
import com.example.shoppingmall_comp.domain.members.dto.MemberSignUpRequest;
import com.example.shoppingmall_comp.domain.members.dto.MemberSignUpResponse;
import com.example.shoppingmall_comp.domain.members.entity.Member;
import com.example.shoppingmall_comp.domain.members.entity.Role;
import com.example.shoppingmall_comp.domain.members.repository.MemberRepository;
import com.example.shoppingmall_comp.domain.members.repository.RoleRepository;
import com.example.shoppingmall_comp.domain.members.service.MemberService;
import com.example.shoppingmall_comp.global.exception.BusinessException;
import com.example.shoppingmall_comp.global.exception.ErrorCode;
import com.example.shoppingmall_comp.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MemberSignUpResponse saveMember(MemberSignUpRequest request) {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }

        Role role = roleRepository.save(Role.builder().roleName(request.roleName()).build());

        Member member = Member.builder()
                .email(request.email())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .role(role)
                .build();

        memberRepository.save(member);

        return new MemberSignUpResponse(member.getMemberId(), member.getEmail(), member.getPoint(), member.getConsumePrice(), member.getVipState(), member.getDeletedState(), member.getRole().getRoleName());
    }

    @Transactional
    public MemberSignInResponse signIn(MemberSignInRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password()); //// 1. username + password 를 기반으로 Authentication 객체 생성. 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        Authentication authentication;

        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); //2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행// authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }

        String accessToken = jwtTokenProvider.createAccessToken(authentication); // 3. 인증정보를 기반으로 JWT 토큰 생성
        Member member = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        return new MemberSignInResponse(member.getMemberId(), member.getEmail(), member.getPoint(), member.getConsumePrice(), member.getVipState(), member.getDeletedState(), member.getRole().getRoleName(), accessToken);
    }
}