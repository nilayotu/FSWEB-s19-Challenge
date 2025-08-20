package com.workintech.twitter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
public class TwitterErrorResponse {
    private String message;
    private int status;
    private long timestamp;
    private LocalDateTime localDateTime;
}
