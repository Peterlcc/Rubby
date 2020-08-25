package com.peter.controller;

import com.peter.message.Result;
import com.peter.service.PlatformService;
import com.peter.service.YarnService;
import com.peter.service.YarnUtil;
import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lcc
 * @date 2020/8/24 下午6:04
 */
@Controller
@RequestMapping("yarn")
@ResponseBody
public class YarnController {
    private static final Log LOG= LogFactory.getLog(YarnController.class);

    @Autowired
    private YarnUtil yarnUtil;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private YarnService yarnService;

    @RequestMapping("test")
    public Result test(){
        Result test = Result.success("yarn connection test");
        return test;
    }
}
