package com.noair.easip.batch.crawler;

import com.noair.easip.web.exception.DomainException;

import static com.noair.easip.web.config.ErrorCode.GPT_ANSWER_DESERIALIZATION_ERROR;

public class GptAnswerDeserializationException extends DomainException {
    public GptAnswerDeserializationException() {
        super(GPT_ANSWER_DESERIALIZATION_ERROR);
    }
}
