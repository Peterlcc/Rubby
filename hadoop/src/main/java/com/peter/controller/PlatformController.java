package com.peter.controller;

import com.peter.bean.Platform;
import com.peter.message.Result;
import com.peter.service.PlatformService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lcc
 * @date 2020/9/1 下午2:35
 */
@Controller
@ResponseBody
@RequestMapping("platform")
public class PlatformController {
    private final static Log LOG= LogFactory.getLog(PlatformController.class);

    @Autowired
    private PlatformService platformService;

    @GetMapping("list")
    public Result platforms(){
        List<Platform> platforms = platformService.getAll();
        return Result.success(platforms);
    }
}
