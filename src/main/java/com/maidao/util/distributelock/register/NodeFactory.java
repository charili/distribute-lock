package com.maidao.util.distributelock.register;

import lombok.Data;
import org.I0Itec.zkclient.ZkClient;


/**
 * @author liqing
 * * @date
 */
@Data
public class NodeFactory
{
    private String rootName;
    private ZkClient zkClient;
    
    public NodeFactory(String rootName, ZkClient zkClient)
    {
        this.rootName = rootName;
        this.zkClient = zkClient;
    }
    
    public void init()
    {
        createIfNotExits(rootName);
    }
    
    private void createIfNotExits(String rootName)
    {
        String basePath = "/".concat(rootName);
        if (!zkClient.exists(basePath))
        {
            zkClient.createPersistent(basePath, true);
        }
    }

}
