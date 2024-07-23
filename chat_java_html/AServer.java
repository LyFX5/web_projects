import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.InetAddress;
import java.net.http.HttpClient;



public class AServer {

    public static void main (String[] args) throws Exception {
        Dictionary<InetAddress, String> messageTable = new Hashtable<InetAddress, String>();
        String messageTableText = "TOPIC";
        AServer server = new AServer();
        int portNumber = 8080;
        File pageFile = new File("page.html");
        final ServerSocket myServer = new ServerSocket(portNumber);
        System.out.println("I have Connected To Port " + portNumber);
        boolean running = true;
        while(running) {
            //See if anyone connects
            try(Socket client = myServer.accept()) {     
                // get html page
            	BufferedReader reader = new BufferedReader(new FileReader(pageFile));
            	// send the page to the client
            	PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                server.displayMessageToClient(printWriter, messageTableText);
                server.sendPage(printWriter, reader);
            	// read clients message
            	BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            	String message = server.readInput(input);
                // put the message to the table
                messageTable.put(client.getInetAddress(), message);
                messageTableText = server.formMessageTableText(messageTable);
                reader.close();
                printWriter.close();
                input.close();
            }
            catch(IOException e) {
                System.out.println("Something went wrong streaming the page");
                e.printStackTrace(System.out);
                System.exit(1);
            }
        }
        try {
            myServer.close();
        }
        finally {
            System.out.println("Server Is now closed");
        }        
    }
    
    private void displayMessageToClient(PrintWriter printWriter, String topic) throws Exception {
        //PrintWriter output = new PrintWriter(client.getOutputStream());
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type: text/html; charset=utf-8");
        printWriter.println();
        printWriter.println("<p>" + topic + "</p>");
        printWriter.flush();
    }
    
    private void sendPage(PrintWriter printWriter, BufferedReader reader) throws Exception {
        /*
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type: text/html; charset=utf-8");
        */
        // printWriter.println("Content-Length: " + pageFile.length());
        printWriter.println("\r\n");
        String line = reader.readLine(); // line to go line by line from file
        while (line != null) // repeat till the file is read
        {
            printWriter.println(line); // print current line
            line = reader.readLine(); // read next line
        }
    }    
    
    private String readInput(BufferedReader input) throws Exception {
        String input_line = "";
        while (!input.ready());
        System.out.println();
        while (input.ready()) {
            String line = input.readLine();
	    System.out.println(line);
	    
	    String[] parts = line.split(" ");
	    
	    String first_part = parts[0];
	    String last_part = parts[parts.length-1];
	   
	    if (first_part.equals("GET") & last_part.equals("HTTP/1.1")) {
	        for (int i = 1; i < parts.length-1; i++) {
	            input_line += parts[i];
		}
	    }
        }
        String result = "";
        if ( ! input_line.equals("/")) {
	    String[] parts = input_line.split("/?message=");
	    result = parts[parts.length-1];
        }
        return result;
    }
    
    private void processInputLine(String line) throws Exception {
        String user_name = "drevopis";
        if (line.equals(user_name)) {
            System.out.println("AAA " + line);
	} else {
            System.out.println(line);
        }
    }
    
    private String formMessageTableText(Dictionary<InetAddress, String> messageTable) throws Exception {
        String messageTableText = "";
        Enumeration clients = messageTable.keys();
	while (clients.hasMoreElements()) {
	    InetAddress address = (InetAddress) clients.nextElement();
	    String clientsMessage = messageTable.get(address);
	    if (clientsMessage != null && !clientsMessage.trim().isEmpty()) {
	        messageTableText += address.toString() + " : " + clientsMessage + "\n";
	    }
	}
	System.out.println(messageTableText);
        return messageTableText;
    }
    
    
    
    /*
    public void testSimpleParsing() throws Exception {
        HttpParams params = new BasicHttpParams();
        SessionInputBuffer inbuf = new SessionInputBuffer(1024, 128);
        HttpRequestFactory requestFactory = new DefaultHttpRequestFactory();
        HttpRequestParser requestParser = new HttpRequestParser(inbuf,
requestFactory, params);
        requestParser.fillBuffer(newChannel("GET /whatever
HTTP/1.1\r\nSome header: stuff\r\n\r\n"));
        HttpRequest request = (HttpRequest) requestParser.parse();
        assertNotNull(request);
        assertEquals("/whatever", request.getRequestLine().getUri());
        assertEquals(1, request.getAllHeaders().length);
    }
    */
    
} 






