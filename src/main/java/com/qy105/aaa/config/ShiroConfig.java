package com.qy105.aaa.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.qy105.aaa.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> filterMap = new LinkedHashMap<>(10);
        filterMap.put("/book/getAllBook", "anon");
        filterMap.put("/user/logout", "logout");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/auth");
        filterMap.put("/book/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public CustomRealm getCustomRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customRealm;
    }

    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
