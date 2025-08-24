package com.workintech.twitter.exception;

import java.time.Instant;

public record ExceptionResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) { }