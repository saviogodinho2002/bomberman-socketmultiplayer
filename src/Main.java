import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        // dispara cliente
        String enderecoIP = "192.168.1.9"; // Coloque o endereço IP retornado pelo servidor aqui
        int porta = 12345;
        // dispara cliente
        Client c1 = new Client(enderecoIP, porta,Util.obterEnderecoIPFornecidoPeloAP());
        c1.executa();
        Scanner sc = new Scanner(System.in);
        while (true)
            if (sc.hasNextLine())
                c1.enviarMensagem(sc.nextLine());

    }

}