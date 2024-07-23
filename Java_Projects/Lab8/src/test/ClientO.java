package test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class ClientO extends Application {
    public static void main(String[] args){

        launch(args);
        if (true){//(args.length != 2){
            //System.out.println("Enter IP and port");
        //}else {
            String IP = "localhost";//args[0];
            int port = 0;
            try{
                port = Integer.parseInt("6666");//args[1]);
            }catch (NumberFormatException e){
                System.out.print("Port is a number!");
                System.exit(0);
            }
            try {
                SocketAddress sa = new InetSocketAddress(IP, port);
                DatagramChannel channel = DatagramChannel.open();
                channel.connect(sa);

                new Thread(()->{
                    while (true) {
                        try {
                            String sin;
                            byte[] buf = new byte[1024];
                            ByteBuffer bbuf = ByteBuffer.wrap(buf);
                            channel.read(bbuf);
                            sin = new String(buf);
                            System.out.println(sin);
                        } catch (Exception exe) {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.err.println("Server is temporarily unavailable or port is not correct!");
                        }
                    }
                }).start();

                new Thread(()->{
                    while (true) {
                        String s = "";
                        Scanner sc = new Scanner(System.in);
                        s = sc.nextLine();
                        if (s.equals("login")) {
                            System.out.println("Enter your login, please");
                            String first = sc.nextLine();
                            System.out.println("Enter you password, please");
                            char[] chars = System.console().readPassword();
                            String second = String.valueOf(chars);
                            s = "login: " + first + "," + second;
                        }
                        if (s.equals("register")) {
                            System.out.println("Chose and enter any login, please");
                            String first = sc.nextLine();
                            System.out.println("Enter your email, please");
                            String second = sc.nextLine();
                            s = "register: " + first + "," + second;
                        }
                        byte[] b = s.getBytes();
                        ByteBuffer bb = ByteBuffer.wrap(b);
                        try {
                            channel.write(bb);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        bb.clear();
                    }
                }).start();
            }catch (IOException e){e.printStackTrace();}
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        Label label = new Label("Application");
        BorderPane.setAlignment(label,Pos.CENTER);
        Button reg = new Button("Sign up");
        reg.setMaxSize(100,50);
        Button log = new Button("Log in");
        log.setMaxSize(100,50);
        VBox vBox = new VBox(reg,log);
        vBox.setAlignment(Pos.CENTER);
        ComboBox lang = new ComboBox();
        BorderPane.setAlignment(lang,Pos.CENTER);
        reg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                regLis(primaryStage);
            }
        });
        log.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logLis(primaryStage);
            }
        });
        BorderPane root = new BorderPane();
        root.setTop(label);
        root.setBottom(lang);
        root.setCenter(vBox);
        primaryStage.setScene(new Scene(root,800,550));
        primaryStage.show();
    }

    static void regLis(Stage stage){
        BorderPane bp = new BorderPane();
        Label label = new Label("REGISTRATION");
        BorderPane.setAlignment(label,Pos.CENTER);
        bp.setTop(label);
        stage.setScene(new Scene(bp,300,300));
        stage.show();
        //System.out.println("reg is working");
    }
    static void logLis(Stage stage){
        BorderPane bp = new BorderPane();
        Label label = new Label("AUTHENTICATION");
        BorderPane.setAlignment(label,Pos.CENTER);
        bp.setTop(label);
        stage.setScene(new Scene(bp,300,300));
        stage.show();
        //System.out.println("log is working");
    }
}



