package com.liyue.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liyue
 */
@ControllerAdvice
public class ExceptionHandler {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 自定义异常处理
     * @param request
     * @param e
     * @return ModelAndView
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e){
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url",request.getRequestURL());
        modelAndView.addObject("exception",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
