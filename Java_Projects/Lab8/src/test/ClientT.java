package test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import javafx.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javafx.application.Application;

public class ClientT extends Application{
    static Socket socket;

    public static void  main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUPConection();
        //myStart(primaryStage,"");
        new Thread(()->{
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()){
                sender(sc.nextLine());
            }
        }).start();
        new Thread(()->{
            String s;
            while ( (s = reciver()) != null){
                System.out.println(s);
            }
        }).start();


    }

    void myStart(Stage primaryStage, String mass){
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
                sender("logout");
            }
        });
        Label label = new Label("Application");
        BorderPane.setAlignment(label, Pos.CENTER);
        Button reg = new Button("Sign up");
        reg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               // regLis(primaryStage);
            }
        });
        reg.setMaxSize(100,50);
        Button log = new Button("Log in");
        log.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //logLis(primaryStage);
            }
        });
        log.setMaxSize(100,50);
        Label masLable = new Label(mass);
        VBox vBox = new VBox(reg,log,masLable);
        vBox.setAlignment(Pos.CENTER);
        ComboBox lang = new ComboBox();
        BorderPane.setAlignment(lang,Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setTop(label);
        root.setBottom(lang);
        root.setCenter(vBox);
        primaryStage.setScene(new Scene(root,400,300));
        primaryStage.show();
    }


    public void setUPConection(){
       try {
          socket = new Socket("localhost", 6666);
       }catch (Exception e){
        e.printStackTrace();
        }
    }

    public static void sender(String s){
        try {
            DataOutputStream o = new DataOutputStream(socket.getOutputStream());
            o.writeUTF(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String reciver(){
        String s = "";
        try{
            DataInputStream i = new DataInputStream(socket.getInputStream());
            s = i.readUTF();
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }
}
