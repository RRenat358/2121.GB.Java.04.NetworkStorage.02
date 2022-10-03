package ru.rrenat358.network;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.rrenat358.Command;

import java.util.function.Consumer;

public class NetworkHandler extends SimpleChannelInboundHandler<String> {

    private Command command;
    private Consumer<String> callback;

    public NetworkHandler() {
    }

    public NetworkHandler(Command command, Consumer<String> callback) {
        this.command = command;
        this.callback = callback;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(command);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        callback.accept(s);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientHandler. channelUnregistered = " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ClientHandler exception");
        cause.printStackTrace();
    }
}
