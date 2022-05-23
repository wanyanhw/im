package com.wanyan.imserver.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wanyanhw
 * @date 2022/5/6 16:58
 */
public class ImServerHandler extends SimpleChannelInboundHandler<String> {

    private ServerCacheTemplate serverCacheTemplate = new ServerCacheTemplate();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(LogUtil.buildLog(ctx.channel().id().asLongText(), msg));
        serverCacheTemplate.sendToAll(msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel ctxChannel = ctx.channel();
        System.out.println(LogUtil.buildLog(ctxChannel.id().asLongText(), "加入频道"));
        serverCacheTemplate.saveChannel(ctxChannel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LogUtil.buildLog(ctx.channel().id().asLongText(), "已退出"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(LogUtil.buildLog(ctx.channel().id().asLongText(), "异常关闭"));
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LogUtil.buildLog(ctx.channel().id().asLongText(), "上线了"));
    }
}
