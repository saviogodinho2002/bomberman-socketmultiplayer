import javax.swing.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
            throws UnknownHostException, IOException {
        // dispara cliente
        String enderecoIP = "192.168.12.86"; // Coloque o endereço IP retornado pelo servidor aqui
        int porta = 12345;
        // dispara cliente
        Client c1 = new Client(enderecoIP, porta,Util.obterEnderecoIPFornecidoPeloAP());
        c1.executa();

        Game game = new Game(c1);
        JFrame frame = new JFrame();

        frame.add(game);
        frame.setTitle("Zeldinha");
        frame.setLocationRelativeTo(null);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);


        Thread thread = new Thread(game);

        thread.start();
        Scanner sc = new Scanner(System.in);
        while (true)
            if (sc.hasNextLine())
                c1.enviarMensagem(sc.nextLine());

    }

}