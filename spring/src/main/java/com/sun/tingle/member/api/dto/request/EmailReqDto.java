package com.sun.tingle.member.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailReqDto {
    private String email;
    private String title;
    private String content;
}
 