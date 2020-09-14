package com.peter.component;

import com.peter.message.Result;
import com.peter.message.ResultCode;
import com.peter.service.HadoopClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author lcc
 * @date 2020/9/2 下午2:08
 */
@Component
public class HadoopClientFallbackFactory implements FallbackFactory<HadoopClientService> {
    @Override
    public HadoopClientService create(Throwable throwable) {
        return new HadoopClientService() {
            @Override
            public Result test() {
                return Result.failure(ResultCode.SYSTEM_INNER_ERROR,"test出错了");
            }

            @Override
            public Result platforms() {
                return Result.failure(ResultCode.SYSTEM_INNER_ERROR,"查询platform出错了");
            }

            @Override
            public Result yarns() {
                return Result.failure(ResultCode.SYSTEM_INNER_ERROR,"查询yarn出错了");
            }
        };
    }
}
