package com.workintech.twitter.exception;
public class TweetNotFoundException extends RuntimeException {
    public TweetNotFoundException(String message) { super(message); }
}