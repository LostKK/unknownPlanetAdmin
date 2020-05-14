package com.kk.web.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZKCurator {
	
	//zookeeper客户端
	private CuratorFramework client = null;
	
	final static Logger log = LoggerFactory.getLogger(ZKCurator.class);
	
	public ZKCurator(CuratorFramework client){
		this.client = client;
	}
	
	public void init(){
		client = client.usingNamespace("admin");
		
		try {
			//判断在admin命名空间下面是否有bgm节点 /admin/bgm
			if(client.checkExists().forPath("/bgm") == null){
				/**
				 * 对于Zookeeper来讲：有两种类型的节点：
				 * 持久节点：当你创建一个节点的时候，这个节点就永远存在，除非你手动删除  choose
				 * 临时节点：你创建一个节点之后，会话断开，会自动删除，当然也可以手动删除
				 */
				client.create().creatingParentsIfNeeded()         //递归创建
				.withMode(CreateMode.PERSISTENT)                  //节点类型：持久节点
				.withACL(Ids.OPEN_ACL_UNSAFE)                     //acl：匿名权限
				.forPath("/bgm");
				log.info("你的Zookeeper初始化成功了...");
				
				log.info("Zookeeper服务器状态：{0}",client.isStarted());
			}
		} catch (Exception e) {
			log.error("你的Zookeeper客户端连接、初始化错误了...");
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加或者删除bgm，向zookeeper-server创建子节点，供小程序后端监听
	 * @param bgmId
	 * @param operType
	 */
	public void sendBgmOperator(String bgmId, String operObject){
		
		try {
			client.create().creatingParentContainersIfNeeded()
					.withMode(CreateMode.PERSISTENT)                  //节点类型：持久节点
						.withACL(Ids.OPEN_ACL_UNSAFE)                     //acl：匿名权限
						.forPath("/bgm/" + bgmId,operObject.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
