package ru.rrenat358;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class ServerNetworkHandler extends SimpleChannelInboundHandler<Command> {
    private static final Logger logger = LogManager.getLogger(ServerNetworkHandler.class);

    private static final String serverDataUserPath = ConfigConst.SERVER_REPO;

    private List<String> list = new ArrayList<>();


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) throws Exception {
        System.out.println("command = " + command);
        if (command.getCommand().equals("put")) {
            readCommand_File(command);
        }
        list.forEach(System.out::println);

        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(
//                String.format("Server: Файл получен: \n%s", command.getFile().getName())
//                String.format("Server: Файл получен: ")
//                String.format("list.stream().iterator()")
                "list.stream().iterator()"
        );
//        System.out.println("Файл сохранён: \n" + command.getFile().getName());
        channelFuture.addListener(ChannelFutureListener.CLOSE);

//        list.forEach(System.out::println);


/*
        ChannelFuture channelFuture2 = channelHandlerContext.writeAndFlush(
                "list.forEach...."
        );
        channelFuture2.addListener(ChannelFutureListener.CLOSE);
*/


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ServerHandler exception");
        cause.printStackTrace();
        ctx.close();
    }



    private void updateFileList(String dir) {
//        System.out.println(dir); //todo log4j
        File file = new File(dir);
        for (File f : file.listFiles()) {
//            System.out.println(f.getName());
            list.add("F" + " | " + f.getName());
        }
    }


    private void readCommand_File(Command command) throws IOException {

        Path root = Path.of(serverDataUserPath);
        Files.createDirectories(root);
        Path filePath = root.resolve(command.getFile().getPath());
        System.out.println("Файл получен и будет сохранён: \n" + filePath);

        Files.createDirectories(filePath.getParent());
        try {
            Files.createFile(filePath);
        } catch (FileAlreadyExistsException ignored) {
            // do nothing
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Files.write(filePath, command.getData());

        String dir = String.valueOf(filePath.getParent());
        updateFileList(dir);

    }



}
