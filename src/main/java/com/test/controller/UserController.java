package com.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.model.User;
import com.test.service.UserService;

@Controller
@RequestMapping("api/{apiVersion}/user/")
public class UserController {


        private final Logger log = org.slf4j.LoggerFactory
                        .getLogger(UserController.class);

        @Autowired
        UserService service;

        /*Restful风格*/
        /*get 查询*/
        @RequestMapping(value="/{userId}",method=RequestMethod.GET)
        @ResponseBody
        public Object getUser(@PathVariable String apiVersion, @PathVariable Long userId) {
                List<User> list =service.getUser();
                return list;
        }
        /*put 修改*/
        @RequestMapping(value="User",method=RequestMethod.PUT,produces={"application/json;charset=UTF-8"})
        @ResponseBody
        public Object updateUser(String id ,String username) {
                User u =new User();
                u.setId(id);
                u.setUsername(username);
                int result =service.updateUser(u);
                return result;

        }
        /*post 插入*/
        @RequestMapping(value="User",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
        @ResponseBody
        public Object insertUser(String id ,String username,String password) {
                User u =new User();
                u.setId(id);
                u.setUsername(username);
                u.setPasword(password);
                int result =service.insertUser(u);
                return result;

        }
        /*post 插入*/
        @RequestMapping(value="User",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
        @ResponseBody
        public Object deleteUser(String id ) {
                User u =new User();
                u.setId(id);
                int result =service.deleteUser(u);
                return result;

        }

}
