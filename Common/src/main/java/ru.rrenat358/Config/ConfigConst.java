package ru.rrenat358.Config;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;


@Log4j2
public class ConfigConst {

    private ConfigConst() { // Prevents instantiation
    }

    //Common ==============================
    public static final String HOST = "localhost";

    public static final int PORT = 13582;



    //Client ==============================
    public static String CLIENT_TITLE ="= Cloud Client =";
    public static String CLIENT_REPO = "Client/DataUser/";




    //Server ==============================
    public static String SERVER_REPO = "Server/DataUser/";

    public static final int MAXIMUM_OBJECT_SIZE = 1024 * 1024 * 10;


    //Other common ==============================
    public static void logStartApp() {
        log.fatal("\n");
        log.fatal("==============================");
        log.fatal("=== Start App ================");
        log.fatal("==============================");
        log.log(Level.FATAL,"LogLevel == FATAL");
        log.log(Level.ERROR,"LogLevel == ERROR");
        log.log(Level.INFO,"LogLevel == INFO");
        log.log(Level.DEBUG,"LogLevel == DEBUG");
        log.log(Level.TRACE,"LogLevel == TRACE");
        log.log(Level.ALL,"LogLevel == ALL");
        log.log(Level.FATAL,"LogLevel ==  ↑ ");
        log.fatal("==============================\n\n\n");
    }



}
