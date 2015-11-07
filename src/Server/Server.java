package Server;

import Service.YWeatherService;
import org.apache.xmlrpc.WebServer;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//сервер, созданный с использованием технологии XML-RPC
public class Server {
    private static int port = 9876;

    public static void main(String[] args) {
        System.out.println("Инициализация сервера... ");
        try {
            WebServer server = new WebServer(port);
            System.out.print("Привязка сервиса...");
            server.addHandler("YWeather", new YWeatherService());
            System.out.println(" OK");
            server.start();

            //кэш хранится на сервере, с дирректории "cache"
            System.out.print("Проверка наличия деректории кэша... ");
            InitCache.initializeServer();
            System.out.println("Сервер запущен успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class InitCache {
        private static void initializeServer() {
            File cachepath = new File("cache/");
            if (!(cachepath.exists() && cachepath.isDirectory()))
                if (cachepath.mkdirs())
                    System.out.println("Каталог создан!");
                else {
                    System.out.println("Ошибка при создании каталога кэша!");
                    System.out.println("Кэш не будет использован при работе сервера. Проверьте настройки доступа.");
                }
            else
                System.out.println("Каталог существует!");
        }
    }
}
