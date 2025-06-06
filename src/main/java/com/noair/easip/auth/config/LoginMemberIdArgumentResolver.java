package com.noair.easip.auth.config;

import com.noair.easip.auth.controller.LoginMemberId;
import com.noair.easip.auth.exception.TokenNotValidException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (memberRepository.isDeletedMember(memberId)) {
//            throw new TokenNotValidException();
//        }

        return memberId;
    }
}
