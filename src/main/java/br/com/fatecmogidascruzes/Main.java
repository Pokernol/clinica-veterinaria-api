package br.com.fatecmogidascruzes;

import br.com.fatecmogidascruzes.interacoes.Menu;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(ConnectionFactorySingleton::stopServer));

        Menu menu = new Menu();
        menu.exibirMenu();
        System.exit(0);

    }
}