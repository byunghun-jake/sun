package com.sun.tingle.member.api.service;

import com.sun.tingle.member.api.dto.MemberDto;
import com.sun.tingle.member.db.entity.MemberEntity;
import com.sun.tingle.member.db.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public MemberEntity registMember(MemberDto member) {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(member.getMemberId())
                .password(passwordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .build();

        return memberRepository.save(memberEntity);
    }

    @Override
    public void duplicateId(String id) {
        memberRepository.findByMemberId(id)
                .ifPresent(m -> {
                    throw new IllegalStateException("중복되는 아이디 입니다");
                });


    }

    @Override
    public void duplicateEmail(String email) {
//        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);

//        MemberEntity memberEntity = memberRepository.findByEmail(email);
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalStateException("중복되는 이메일 입니다");
                });
    }

    @Override
    public Optional<MemberEntity> getMemberById(String id) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberId(id);
        return memberEntity;
    }
}