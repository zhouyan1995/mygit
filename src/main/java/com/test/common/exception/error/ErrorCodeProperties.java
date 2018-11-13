package com.test.common.exception.error;

import com.alibaba.druid.util.StringUtils;
import javax.annotation.PostConstruct;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * 错误代码及错误提示语的数据提取公共类
 */
@Component
public class ErrorCodeProperties {

  public static final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
  private static final String ERROR_MESSAGE_PATH = "classpath:errorMessage";
  private static ErrorCodeProperties errorCodeProperties = new ErrorCodeProperties();

  @PostConstruct
  private static void getErrorProperties() {
    messageSource.setBasename(ERROR_MESSAGE_PATH);
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setUseCodeAsDefaultMessage(true);
  }

  public static ErrorCodeProperties init() {
    return errorCodeProperties;
  }

  public static String getErrorMessage(String errorCode) {
    getErrorProperties();//临时设置
    String errorMessage = messageSource.getMessage(errorCode, null, null);
    if (!StringUtils.isEmpty(errorMessage)) {
      return errorMessage;
    }
    return "";
  }
}
