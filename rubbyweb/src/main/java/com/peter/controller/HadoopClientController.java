package com.peter.controller;

import com.peter.message.Result;
import com.peter.service.HadoopClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lcc
 * @date 2020/9/1 上午10:53
 */
@Controller
@RequestMapping("hadoop")
public class HadoopClientController {
    private static final Log LOG=LogFactory.getLog(HadoopClientController.class);

    @Autowired
    private HadoopClientService hadoopClientService;

    @GetMapping("hdfs/test")
    @ResponseBody
    public Result hdfsTest(){
        Result result = hadoopClientService.test();
        LOG.info(result);
        return result;
    }

    @GetMapping("platforms")
    @ResponseBody
    public Result platforms(){
        Result platforms = hadoopClientService.platforms();
        return platforms;
    }

    @GetMapping("yarns")
    @ResponseBody
    public Result yarns(){
        Result yarns = hadoopClientService.yarns();
        return Result.success(yarns);
    }
}
