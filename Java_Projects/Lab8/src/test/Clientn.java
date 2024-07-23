package test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Clientn {//extends Application {
    /*public static void main(String[] args){
        launch(args);
        if (args.length != 2){
            System.out.println("Enter IP and port");
        }else {
            String IP = args[0];
            int port = 0;
            try{
                port = Integer.parseInt(args[1]);
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
        begin(primaryStage);
    }

    private void begin(Stage primaryStage){
        Label label = new Label("Application");
        BorderPane.setAlignment(label,Pos.CENTER);
        Button reg = new Button("Sign up");
        reg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Client.this.regLis(primaryStage);
            }
        });
        reg.setMaxSize(100,50);
        Button log = new Button("Log in");
        log.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Client.this.logLis(primaryStage);
            }
        });
        log.setMaxSize(100,50);
        VBox vBox = new VBox(reg,log);
        vBox.setAlignment(Pos.CENTER);
        ComboBox lang = new ComboBox();
        BorderPane.setAlignment(lang,Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setTop(label);
        root.setBottom(lang);
        root.setCenter(vBox);
        primaryStage.setScene(new Scene(root,400,300));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    private void logLis(Stage stage){
        BorderPane root = new BorderPane();
        Label label1 = new Label("Вход в приложение");
        BorderPane.setAlignment(label1,Pos.CENTER);
        Label l = new Label("Логин");
        l.setMaxSize(100,50);
        TextField login = new TextField();
        login.setMaxSize(100,50);
        Label p = new Label("Пароль");
        p.setMaxSize(100,50);
        PasswordField pas = new PasswordField();
        pas.setMaxSize(100,50);
        Button enter = new Button("Log in");
        enter.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                application(stage,login.getText());
            }
        });
        enter.setMaxHeight(50);
        enter.setMinWidth(60);
        VBox vBox1 = new VBox(10,l,login,p,pas,enter);
        vBox1.setAlignment(Pos.CENTER);
        ComboBox lang1 = new ComboBox();
        BorderPane.setAlignment(lang1,Pos.CENTER);
        root.setTop(label1);
        root.setCenter(vBox1);
        root.setBottom(lang1);
        stage.setScene(new Scene(root,400,300));
        stage.show();
    }

    private void regLis(Stage stage){
        BorderPane root = new BorderPane();
        Label label = new Label("Создать аккаунт");
        BorderPane.setAlignment(label,Pos.CENTER);
        Label n = new Label("Username");
        n.setMaxSize(100,50);
        TextField name = new TextField();
        name.setMaxSize(100,50);
        Label e = new Label("Почта");
        e.setMaxSize(100,50);
        TextField email = new TextField();
        email.setMaxSize(100,50);
        Button b = new Button("Sign up");
        b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                begin(stage);
            }
        });
        b.setMinWidth(60);
        b.setMaxHeight(50);
        VBox vBox = new VBox(10,label,n,name,e,email,b);
        vBox.setAlignment(Pos.CENTER);
        ComboBox lan = new ComboBox();
        BorderPane.setAlignment(lan,Pos.CENTER);
        root.setTop(label);
        root.setCenter(vBox);
        root.setBottom(lan);
        stage.setScene(new Scene(root,400,300));
        stage.show();
    }

    private void application(Stage stage, String username){
        BorderPane root = new BorderPane();
        Label name = new Label(username);
        GridPane commands = new GridPane();
        commands.setAlignment(Pos.CENTER);
        Button remove = new Button("remove");
        remove.setMinWidth(100);
        Button remove_last = new Button("remove last");
        remove_last.setMinWidth(100);
        Button add = new Button("add");
        add.setMinWidth(100);
        Button add_if_max = new Button("add if max");
        add_if_max.setMinWidth(100);
        Button show = new Button("show");
        show.setMinWidth(100);
        Button info = new Button("info");
        info.setMinWidth(100);
        commands.add(remove,0,0);
        commands.add(remove_last,1,0);
        commands.add(add,2,0);
        commands.add(add_if_max,0,1);
        commands.add(show,1,1);
        commands.add(info,2,1);
        ComboBox lang = new ComboBox();
        VBox vBox = new VBox(40,commands,lang);
        vBox.setAlignment(Pos.CENTER);
        Button logout = new Button("Log out");
        logout.addEventHandler(ActionEvent.ACTION, event -> begin(stage));
        VBox vBox1 = new VBox(name,logout);
        root.setLeft(vBox1);
        root.setBottom(vBox);

        //здесь будет таблица

        stage.setScene(new Scene(root,700,500));
        stage.show();
    }*/
}



