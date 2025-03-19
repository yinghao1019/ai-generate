package com.howhow.ai_generate.exception;

public class ChatCompletionException extends RuntimeException {
    public ChatCompletionException() {
        super();
    }

    public ChatCompletionException(String message) {
        super(message);
    }

    public ChatCompletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatCompletionException(Throwable cause) {
        super(cause);
    }
}
