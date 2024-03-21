import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.io.*;
import org.json.JSONObject;

public class Client {

    private String host;
    private int porta;

    public String ip;
    PrintStream saida;
    Socket cliente;

    public Recebedor recebedor;
    public Client (String host, int porta,String ip) {
        this.host = host;
        this.porta = porta;
        this.ip = ip;
    }

    public void executa() throws UnknownHostException, IOException {
         cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor! "+this.ip);

        // thread para receber mensagens do servidor
        recebedor = new Recebedor(cliente.getInputStream(),this);
        new Thread(recebedor).start();

         saida = new PrintStream(cliente.getOutputStream());


        //saida.close();
        //teclado.close();
       // cliente.close();
    }
    public void enviarMensagem(String message){
        saida.println(this.ip+"|"+message);
    }
    public void closeCliente() throws IOException {
        saida.close();
         cliente.close();
    }
}

class Recebedor implements Runnable {

    private InputStream servidor;
    private Client client;

    public JSONObject json;

    public Recebedor(InputStream servidor,Client client) {
        this.servidor = servidor;
        this.client = client;
    }


    public void run() {
        // recebe msgs do servidor e imprime na tela
        Scanner s = new Scanner(this.servidor);

        while (s.hasNextLine()) {
            String out = s.nextLine();


            json = new JSONObject(out);
            String message = json.getString("message");
            String ipOring = json.getString("ip");

            if(!client.ip.equals(ipOring)){
                System.out.println(message);
            }
           // System.out.println(json);
        }
    }
}