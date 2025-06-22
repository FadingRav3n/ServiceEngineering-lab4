package com.krowfeather.controller;

import com.krowfeather.entity.Blog;
import com.krowfeather.entity.Const;
import com.krowfeather.entity.Result;
import com.krowfeather.entity.User;
import com.krowfeather.feign.UserFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Resource
    private UserFeignClient userFeignClient;
    
    @GetMapping("/{id}")
    @CircuitBreaker(name = "backendA", fallbackMethod = "getUserByIdFallback")
    public Result<Blog> getBlogById(@PathVariable(name = "id") Integer id){
        Blog blog = new Blog();
        blog.setId(id);
        blog.setTitle("hello");
        blog.setContent("my first blog 16001");
        blog.setLikes(10);
        int uid = 2;
        User user = userFeignClient.getUserById(uid); 
        blog.setUser(user);
        return new Result<>(Const.CODE_SUCCESS,Const.MSG_SUCCESS,blog);
    }
    
    public Result<User> getUserByIdFallback(Integer id, Throwable t) {
        return new Result<>(Const.CODE_ERROR, "断路器A降级响应：服务不可用", null);
    }
}
