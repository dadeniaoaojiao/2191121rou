package com.qy105.aaa.controller;


import com.qy105.aaa.model.User;
import com.qy105.aaa.service.userservice.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("auth")
    public String auth(){
        return "401";
    }
    @RequestMapping("login")
    public String login(String userName, String userPassword, ModelMap modelMap) {

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, userPassword);
            try {
                //真正的认证，
                subject.login(token);
                return "redirect:/book/getAllBook";
            } catch (UnknownAccountException uae) {
                modelMap.addAttribute("msg", uae.getMessage());
            } catch (IncorrectCredentialsException ice) {
                modelMap.addAttribute("msg", "密码错误");
                System.out.println("密码错误");

            } catch (LockedAccountException lae) {
                modelMap.addAttribute("msg", lae.getMessage());
                System.out.println("该账户已经锁定");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {

                System.out.println("认证失败");
                modelMap.addAttribute("msg", "认证失败");
            }
            return "login";

        } else {
            return "redirect:/book/getAllBook";
        }
    }
    @RequestMapping("ins")
    @ResponseBody
    public String ins(String userName, String userPassword){
        SimpleHash simpleHash = new SimpleHash("SHA-256", userPassword, userName, 1024);
        String userPasswords = simpleHash.toString();
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(userPasswords);
        user.setUserStatus(1);
        userService.ins(user);
        return "成功";
    }
}
