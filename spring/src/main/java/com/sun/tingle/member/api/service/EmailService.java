package com.sun.tingle.member.api.service;

public interface EmailService {

    public void sendId(String email, String memberId);

    public String SendPasswordCode(String email, String name);
}
 