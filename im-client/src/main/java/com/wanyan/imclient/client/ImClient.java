package com.wanyan.imclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 即时通讯客户端
 * @author wanyanhw
 * @date 2022/5/6 16:55
 */
public class ImClient {
    public static void main(String[] args) {
        try {
            new ImClient().run("127.0.0.1", 8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ChannelFuture channelFuture;

    public void close() {
        channelFuture.channel().close();
    }

    public void run(String host, int port) throws Exception {
        if (channelFuture != null) {
            close();
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
            System.out.println("客户端已启动，ID:" + channelFuture.channel().id().asLongText());
            channelFuture.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
