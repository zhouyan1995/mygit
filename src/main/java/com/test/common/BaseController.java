package com.test.common;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
@RequestMapping(produces={"application/json;charset=UTF-8"})
public abstract class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
}
