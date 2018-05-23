package com.maidao.util.distributelock.config;

import com.maidao.util.distributelock.lock.SimpleDistributedLock;
import com.maidao.util.distributelock.register.NodeFactory;
import org.I0Itec.zkclient.ZkClient;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author liqing
 * @Description:
 * @Date:created in 11:01 2018/4/27/027
 * @Modified By:
 */
@Configuration
public class DistributeLockConfig {
    // zk配置
    @Value("${distributeLock.zkServers}")
    private String zkServers;

    // 默认的SessionTimeout 是 2 * tickTime ~ 20 * tickTime
    @Value("${distributeLock.zkSessionTimeout}")
    private int zkSessionTimeout;

    @Value("${distributeLock.zkConnectionTimeout}")
    private int zkConnectionTimeout;

    @Value("${distributeLock.zkRootName}")
    private String zkRootName;


    @Bean(destroyMethod = "close")
    public ZkClient createZkClient()
    {
        return new ZkClient(zkServers, zkSessionTimeout, zkConnectionTimeout);
    }

    @Bean(initMethod = "init")
    public NodeFactory createNodeFactory(ZkClient zkClient)
    {
        return new NodeFactory(zkRootName, zkClient);
    }

    @Bean
    public SimpleDistributedLock createSimpleDistributeLock(ZkClient zkClient){
        String basePath= "/locker";
        if(Strings.isNotBlank(zkRootName)){
            basePath = "/".concat(zkRootName);
        }
        return new SimpleDistributedLock(zkClient,basePath);
    }
}
