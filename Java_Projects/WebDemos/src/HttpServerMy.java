import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServerMy {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8089)) {
            System.out.println("Server started!");

            while (true) {
                // ожидаем подключения
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                // для подключившегося клиента открываем потоки
                // чтения и записи
                //try (; так сессия не закрывается
                      //{
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    PrintWriter output = new PrintWriter(socket.getOutputStream());
                    // ждем первой строки запроса
                    while (!input.ready());

                    // считываем и печатаем все что было отправлено клиентом
                    System.out.println();
                    while (input.ready()) {
                        System.out.println(input.readLine());
                    }

                    // отправляем ответ

                    output.println("HTTP/1.1 200 OK");
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();

                    String src = "Hello Tor mother!";
                    output.println("<p>" + src + "</p>");
                    output.flush();

                    // по окончанию выполнения блока try-with-resources потоки,
                    // а вместе с ними и соединение будут закрыты
                    //System.out.println("Client disconnected!");
               //}
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
