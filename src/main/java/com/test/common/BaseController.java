package com.test.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.test.common.exception.BaseException;
@Slf4j
@Controller
@RequestMapping(produces={"application/json;charset=UTF-8"})
public abstract class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	static {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }
	@ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> errorResponse(Exception e, HttpServletResponse response, HttpServletRequest request) {
        logger.error("", e);
        BaseResponseVo responseVo = new BaseResponseVo();
        responseVo.setCode("400");
        String message = "请求失败,请稍后重试";
        System.out.println(e.toString()+"11111111111111");
        if (e instanceof BaseException) {
        	BaseException baseException = (BaseException) e;
            responseVo.setCode(baseException.getCode());
            message = e.getMessage();
           /* if (baseException.getAttrs() != null) {
                responseVo.setAttrs(baseException.getAttrs());
            }*/
        }
        responseVo.setMessage(message);
        String body = JSON.toJSONStringWithDateFormat(responseVo, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
        return responseError(body);
    }
	protected ResponseEntity<String> responseError(String body) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<String>(body, headers, HttpStatus.BAD_REQUEST);
    }
	protected ResponseEntity<String> responseStr(String body) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        System.out.println("body"+body);
        return new ResponseEntity<String>(body, headers, HttpStatus.OK);
    }
	protected ResponseEntity<String> response(String key, Object resp) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        respMap.put(key, resp);
        System.out.println("key"+key);
        return response(respMap);
    }
	protected ResponseEntity<String> response(Object resp) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        String json = JSON.toJSONStringWithDateFormat(resp, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(json+"json");
        return new ResponseEntity<String>(json, headers, HttpStatus.OK);
    }
}

