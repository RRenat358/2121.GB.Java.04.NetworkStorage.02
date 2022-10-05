package ru.rrenat358;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ServerNetworkHandler extends SimpleChannelInboundHandler<Command> {

    private static final String serverDataUserPath = ConfigConst.SERVER_REPO;

    private List<String> list = new ArrayList<>();


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command command) throws Exception {
        System.out.println("command = " + command);
        if (command.getCommand().equals("put")) {

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

//            Files.walkFileTree(filePath,);


            String dir = String.valueOf(filePath.getParent());
            updateFileList(dir);

        }
        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(
                String.format("Server: Файл получен: \n%s", command.getFile().getName())

        );
        System.out.println("Файл сохранён: \n" + command.getFile().getName());
        channelFuture.addListener(ChannelFutureListener.CLOSE);

//        System.out.println(list.stream().forEach());
        list.forEach(System.out::println);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ServerHandler exception");
        cause.printStackTrace();
        ctx.close();
    }

    private void updateFileList(String dir) {

        System.out.println(dir);
//        String dir = filePath.getFileName().toString();

        File file = new File(dir);
        for (File f : file.listFiles()) {
            System.out.println(f.getName());
            list.add(f.getName());
        }

    }

    final File folder = new File("/home/you/Desktop");
//    listFilesForFolder(folder);

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }




}
