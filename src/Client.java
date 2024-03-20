import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.io.*;
import org.json.JSONObject;

public class Client {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        // dispara cliente
        String enderecoIP = "192.168.12.86"; // Coloque o endereço IP retornado pelo servidor aqui
        int porta = 12345;
        // dispara cliente
        new Client(enderecoIP, porta,Util.obterEnderecoIPFornecidoPeloAP()).executa();
    }

    private String host;
    private int porta;

    public String ip;

    public Client (String host, int porta,String ip) {
        this.host = host;
        this.porta = porta;
        this.ip = ip;
    }

    public void executa() throws UnknownHostException, IOException {
        Socket cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor! "+this.ip);

        // thread para receber mensagens do servidor
        Recebedor r = new Recebedor(cliente.getInputStream(),this);
        new Thread(r).start();

        // lê msgs do teclado e manda pro servidor
        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());

        String texto;
        while (teclado.hasNextLine()) {
            texto = teclado.nextLine();
            if (texto.equals("sair")) {
                System.exit(0);
            } else {
                saida.println(this.ip+"|"+texto);
            }
        }
        saida.close();
        teclado.close();
        cliente.close();
    }
}

class Recebedor implements Runnable {

    private InputStream servidor;
    private Client client;

    public Recebedor(InputStream servidor,Client client) {
        this.servidor = servidor;
        this.client = client;
    }

    public void run() {
        // recebe msgs do servidor e imprime na tela
        Scanner s = new Scanner(this.servidor);

        while (s.hasNextLine()) {
            String out = s.nextLine();


            JSONObject json = new JSONObject(out);
            String message = json.getString("message");
            String ipOring = json.getString("ip");

            if(!client.ip.equals(ipOring)){
                System.out.println(message);
            }

        }
    }
}