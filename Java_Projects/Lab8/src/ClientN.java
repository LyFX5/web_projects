import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientN extends Application {
    static DatagramChannel channel;
    static boolean flag = false;
    static boolean showFlag = false;
    static ArrayList<String> incomingTable = new ArrayList<>();
    static ObservableList<Animal> data = FXCollections.observableArrayList();
    static Gson gson = new Gson();

    public static void main(String[] args) {
        launch(args);
    }

    private static void setUPConaction() {
        String IP = "localhost";
        int port = 6666;
        try {
            SocketAddress sa = new InetSocketAddress(IP, port);
            channel = DatagramChannel.open();
            channel.connect(sa);

            new Thread(() -> {
                while (true) {
                    try {
                        byte[] buf = new byte[1024];
                        ByteBuffer bbuf = ByteBuffer.wrap(buf);
                        channel.read(bbuf);
                        String s = new String(buf);
                        if (showFlag){
                            if (!s.contains("end")){
                                incomingTable.add(s);
                                continue;
                            }else {
                                showFlag = false;
                                showAnalizator();
                            }
                        }
                        if (s.contains("you are successfully login")){
                            flag = true;
                        }
                        if (s.contains("show")){
                            showFlag = true;
                            continue;
                        }else {
                            System.out.println(s);
                        }
                    } catch (Exception exe) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Server is temporarily unavailable or port is not correct!");
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void showAnalizator(){
        ArrayList<Animal> animals = new ArrayList<>();
        incomingTable.stream().forEach((x)->{
            String[] strings = x.split(" ");
            Animal animal = gson.fromJson(strings[1],Animal.class);
            animal.setOwner(strings[0]);
            animals.add(animal);
        });
        data.clear();
        data.addAll(animals);
    }

    public static void setData(){
        String animals = "";
        ArrayList<Animal> a = new ArrayList<>();
        a.addAll(data);
        for (int i = 0; i < a.size(); i++){
            animals += (gson.toJson(a.get(i)) + "x");
        }
        sender("reSet "+animals);
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        myStart(primaryStage);
        setUPConaction();
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
        enter.setMaxSize(60,50);
        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                avtorisation(stage,login,pas);
            }
        });
        Button c = new Button("Main page");
        c.setMaxSize(80,50);
        c.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myStart(stage);
            }
        });
        VBox vBox1 = new VBox(10,l,login,p,pas,enter,c);
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
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                registration(stage,name,email);
            }
        });
        b.setMaxSize(60,50);
        Button a = new Button("Main page");
        a.setMaxSize(80,50);
        a.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myStart(stage);
            }
        });
        VBox vBox = new VBox(10,label,n,name,e,email,b,a);
        vBox.setAlignment(Pos.CENTER);
        ComboBox lan = new ComboBox();
        BorderPane.setAlignment(lan,Pos.CENTER);
        root.setTop(label);
        root.setCenter(vBox);
        root.setBottom(lan);
        stage.setScene(new Scene(root,400,300));
        stage.show();
    }
    private void registration(Stage stage, TextField name,TextField email){
        String user = name.getText();
        String mail = email.getText();
        String s = "register: "+user+","+mail;
        sender(s);
        myStart(stage);
    }

    private void avtorisation(Stage stage, TextField login, PasswordField password){
        String log = login.getText();
        String pas = password.getText();
        String s = "login: "+log+","+pas;
        sender(s);
        try{
            Thread.sleep(5000);
        }catch (InterruptedException ie){}
        if (flag) {
            application(stage,log);
            flag = false;
        }else {
            myStart(stage);
        }
    }

    void myStart(Stage primaryStage){
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        Label label = new Label("Application");
        BorderPane.setAlignment(label,Pos.CENTER);
        Button reg = new Button("Sign up");
        reg.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                regLis(primaryStage);
            }
        });
        reg.setMaxSize(100,50);
        Button log = new Button("Log in");
        log.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logLis(primaryStage);
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
    }

    private void application(Stage stage, String username){
        BorderPane root = new BorderPane();
        Label name = new Label(username);
        GridPane commands = new GridPane();
        Canvas canvas = new Canvas(150,150);
        GraphicsContext context = canvas.getGraphicsContext2D();
        FlowPane draw = new FlowPane(canvas);
        draw.setMaxWidth(100);

        draw.setAlignment(Pos.CENTER_LEFT);
        Button logout = new Button("Log out");
        logout.addEventHandler(ActionEvent.ACTION, event -> {
            myStart(stage);
            sender("logout");
        });

        //здесь будет таблица
        data.addListener(new ListChangeListener<Animal>() {
            @Override
            public void onChanged(Change<? extends Animal> c) {
                setData();
            }
        });
        TableView<Animal> table = new TableView<>();
        Label lbl = new Label();
        Label lll = new Label();
        table.setEditable(true);
        final TextField addName = new TextField();
        addName.setPromptText("Name");
        final TextField addSize = new TextField();
        addSize.setPromptText("Size");
        final TextField addLocation = new TextField();
        addLocation.setPromptText("Location");
        Button add = new Button("add");
        add.setMinWidth(100);
        HBox hb = new HBox(addName,addSize,addLocation);
        add.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data.add(new Animal(
                        username,
                        addName.getText(),
                        addSize.getText(),
                        addLocation.getText()
                ));
                addName.clear();
                addSize.clear();
                addLocation.clear();
            }
        });
        Button remove = new Button("remove");
        remove.setMinWidth(100);
        remove.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(lll.getText().equals(name.getText())){
                    data.remove(new Animal(
                            username,
                            addName.getText(),
                            addSize.getText(),
                            addLocation.getText()
                    ));
                    addName.clear();
                    addSize.clear();
                    addLocation.clear();
                }}
        });
        //data.add(new Animal("Vasiliy","Cat","5","10.0"));
        //data.add(new Animal("Guest","Dog","50","39.4"));
        TableColumn owner = new TableColumn("Owner");
        owner.setMinWidth(100);
        owner.setCellValueFactory(
                new PropertyValueFactory<Animal, String>("owner"));


        TableColumn names = new TableColumn("Name");
        names.setCellValueFactory(
                new PropertyValueFactory<Animal, String>("names"));
        names.setCellFactory(TextFieldTableCell.forTableColumn());
        names.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Animal, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Animal, String> event) {
                        if(lll.getText().equals(username)) {
                            ((Animal) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow())).setNames(event.getNewValue());
                        }
                    }
                }
        );
        names.setMinWidth(100);

        TableColumn size = new TableColumn("Size");
        size.setMinWidth(100);
        size.setCellValueFactory(
                new PropertyValueFactory<Animal,String>("size"));
        size.setCellFactory(TextFieldTableCell.forTableColumn());
        size.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Animal, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Animal, String> event) {
                        if(lll.getText().equals(username)) {
                            ((Animal) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow())).setSize(event.getNewValue());}
                    }
                }
        );


        TableColumn location = new TableColumn("Location");
        location.setMinWidth(100);
        location.setCellValueFactory(
                new PropertyValueFactory<Animal,String>("location"));
        TableView.TableViewSelectionModel<Animal> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Animal>() {
            @Override
            public void changed(ObservableValue<? extends Animal> observable, Animal oldValue, Animal newValue) {
                if (newValue != null) {
                    //context.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
                    context.fillOval(50+Double.parseDouble(newValue.getLocation())*3,
                            50 + Double.parseDouble(newValue.getLocation())*2,
                            40+Integer.parseInt(newValue.getSize())%10,
                            30+Integer.parseInt(newValue.getSize())%20);
                    context.setFill(Color.color(Math.random(),Math.random(),Math.random()));
                    lbl.setText("Selected: " + newValue.getNames());
                    lll.setText(newValue.getOwner());
                }
            }
        });


        location.setCellFactory(TextFieldTableCell.forTableColumn());
        location.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Animal,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Animal,String> event) {
                        if(lll.getText().equals(username)) {
                            ((Animal) event.getTableView().getItems().get(
                                    event.getTablePosition().getRow())).setLocation(event.getNewValue());}
                    }
                }
        );
        table.setItems(data);
        table.getColumns().addAll(owner,names,size,location);
        table.setMaxHeight(300);
        FlowPane tb = new FlowPane(Orientation.VERTICAL,20,20,table,hb);
        tb.setAlignment(Pos.CENTER);
        root.setCenter(tb);

        commands.setAlignment(Pos.CENTER);
        Button remove_last = new Button("remove last");
        remove_last.setMinWidth(100);
        Button add_if_max = new Button("add if max");
        add_if_max.setMinWidth(100);
        Button show = new Button("show");
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sender("show");
            }
        });
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
        VBox vBox1 = new VBox(name,logout,draw,lbl);
        root.setLeft(vBox1);
        root.setBottom(vBox);

        stage.setScene(new Scene(root,700,500));
        stage.show();
    }

    private static void sender(String string){
        byte[] b = string.getBytes();
        ByteBuffer bb = ByteBuffer.wrap(b);
        try {
            channel.write(bb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bb.clear();
    }

}



