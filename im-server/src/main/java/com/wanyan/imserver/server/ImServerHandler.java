package com.wanyan.imserver.server;

import com.alibaba.fastjson.JSONObject;
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
        Channel ctxChannel = ctx.channel();
        JSONObject jsonMsg = JSONObject.parseObject(msg);
        Integer messageType = jsonMsg.getInteger("messageType");
        if (messageType != null && messageType == 1) {
            String name = jsonMsg.getString("name");
            serverCacheTemplate.saveChannel(name, ctxChannel);
            System.out.println(LogUtil.buildLog(name, "上线"));
            JSONObject s = new JSONObject();
            s.put("client", name);
            s.put("msg", "上线");
            serverCacheTemplate.sendToAll(s.toJSONString());
            return;
        }
        String clientName = serverCacheTemplate.getClientName(ctxChannel.id().asLongText());
        if (clientName == null) {
            ctxChannel.writeAndFlush("未登录");
            return;
        }
        System.out.println(LogUtil.buildLog(clientName, msg));

        JSONObject s = new JSONObject();
        s.put("client", clientName);
        s.putAll(jsonMsg);
        serverCacheTemplate.sendToAll(s.toJSONString());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String clientName = serverCacheTemplate.getClientName(ctx.channel().id().asLongText());
        serverCacheTemplate.remove(clientName);
        System.out.println(LogUtil.buildLog(clientName, "下线"));

        JSONObject s = new JSONObject();
        s.put("client", clientName);
        s.put("msg", "下线");
        serverCacheTemplate.sendToAll(s.toJSONString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String clientName = serverCacheTemplate.getClientName(ctx.channel().id().asLongText());
        System.out.println(LogUtil.buildLog(clientName, "账号异常"));
        ctx.channel().close();

        JSONObject s = new JSONObject();
        s.put("client", clientName);
        s.put("msg", "账号异常");
        serverCacheTemplate.sendToAll(s.toJSONString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }
}
