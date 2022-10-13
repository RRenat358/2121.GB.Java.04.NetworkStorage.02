package ru.rrenat358.network;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.Command;

import java.util.function.Consumer;


@Log4j2
public class ClientNetworkHandler extends SimpleChannelInboundHandler<String> {

    private Command command;
    private Consumer<String> callback;

    public ClientNetworkHandler() {
    }

    public ClientNetworkHandler(Command command, Consumer<String> callback) {
        this.command = command;
        this.callback = callback;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(command);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.debug(s + "<====== channelRead0()");
        callback.accept(s);

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.debug("ClientNetworkHandler. channelUnregistered = " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("ClientHandler exception");
        cause.printStackTrace();
//        ctx.close();
    }



}
