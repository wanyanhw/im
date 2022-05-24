package com.wanyan.imclient.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author wanyanhw
 * @date 2022/5/6 17:01
 */
public class ImClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        JSONObject jsonMsg = JSONObject.parseObject(msg);
        String clientName = jsonMsg.getString("client");
        String message = jsonMsg.getString("msg");
        if (CacheUtil.getClient().equals(clientName)) {
            clientName = "我";
        }
        System.out.println(LogUtil.buildLog(clientName, message));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LogUtil.buildLog(ctx.channel().id().asLongText(), "客户端已退出"));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    }
}
