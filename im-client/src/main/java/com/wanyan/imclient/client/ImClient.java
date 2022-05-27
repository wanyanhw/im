package com.wanyan.imclient.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Getter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 即时通讯客户端
 * @author wanyanhw
 * @date 2022/5/6 16:55
 */
@Component
public class ImClient {

    private final String EXIT = "exit";

    @Getter
    private ChannelFuture channelFuture;

    @Async(value = "asyncTaskExecutor")
    public void run(String host, int port) {
        if (channelFuture != null) {
            closeChannel();
        }
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new ImClientHandler());
                        }
                    });
            channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("客户端已启动，ID:" + channel.id().asLongText());

            ClientUser clientUser = new ClientUser();
            clientUser.setName(CacheUtil.getClient());
            clientUser.setChannelId(channel.id().asLongText());

            this.sendMsg(JSONObject.toJSONString(clientUser));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String readLine;
            StringBuilder stringBuilder = new StringBuilder();
            do {
                readLine = bufferedReader.readLine();
                if (!StringUtils.hasLength(readLine)) {
                    continue;
                }
                stringBuilder.append("{\"msg\":\"").append(readLine).append("\"}");
                this.sendMsg(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            } while (!EXIT.equals(readLine.toLowerCase()));
            closeChannel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    public void sendMsg(String msg) {
        Channel channel = channelFuture.channel();
        channel.writeAndFlush(msg);
    }

    public void closeChannel() {
        channelFuture.channel().close();
    }
}
