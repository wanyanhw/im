package com.wanyan.imclient.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * @author wanyanhw
 * @date 2022/5/6 17:01
 */
public class ImClientHandler extends SimpleChannelInboundHandler<String> {

    private ClientCacheTemplate cacheTemplate = ClientCacheTemplate.instance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(LogUtil.buildLog(ctx.channel().id().asLongText(), msg));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LogUtil.buildLog(ctx.channel().id().asLongText(), "已退出"));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        cacheTemplate.saveChannel(CacheUtil.getClient(), ctx.channel());
    }
}
