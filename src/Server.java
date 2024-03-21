import org.json.JSONObject;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Server {

    public static void main(String[] args) throws IOException {
        // inicia o servidor
        new Server(12345).executa();
    }

    private int porta;
    private List<PrintStream> clientes;

    public Server (int porta) {
        this.porta = porta;
        this.clientes = new ArrayList<PrintStream>();
    }

    public void executa () throws IOException {
        ServerSocket servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");

        InetAddress enderecoServidor = servidor.getInetAddress();
        System.out.println("Servidor iniciado em " +Util.obterEnderecoIPFornecidoPeloAP() + " na porta " + this.porta);

        while (true) {
            // aceita um cliente
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " +  cliente.getInetAddress().getHostAddress() );

            // adiciona saida do cliente à lista
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            this.clientes.add(ps);

            // cria tratador de cliente numa nova thread
            TrataCliente tc = new TrataCliente(cliente.getInputStream(), this,cliente.getInetAddress().getHostAddress());
            new Thread(tc).start();
        }

    }

    public void distribuiMensagem(String msg) {
        // envia msg para todo mundo
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }
}

class TrataCliente implements Runnable {

    private InputStream cliente;
    private Server servidor;

    public String ip;

    public TrataCliente(InputStream cliente, Server servidor, String ip) {
        this.cliente = cliente;
        this.servidor = servidor;
        this.ip = ip;
    }

    public void run() {
        // quando chegar uma msg, distribui pra todos
        Scanner s = new Scanner(this.cliente);


        while (s.hasNextLine()) {
            String msg  = s.nextLine();
          /*  JSONObject json = new JSONObject();

            int index = msg.indexOf("|");
            String ipOring = msg.substring(0,index);
            msg = msg.substring(index+1);

            json.put("ip", ipOring);
            json.put("message", msg);*/

            servidor.distribuiMensagem(msg);
        }
        s.close();
    }
}