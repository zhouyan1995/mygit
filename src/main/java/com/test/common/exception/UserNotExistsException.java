package com.test.common.exception;

public class UserNotExistsException extends BaseException {

	    public static final String code = "404";
	    private static final long serialVersionUID = -7941771010651138242L;

	    public UserNotExistsException(String identity) {
	        super(code, "用户不存在:" + identity);
	    }

	    public UserNotExistsException() {
	        super(code, "用户不存在");
	    }
	    
}
