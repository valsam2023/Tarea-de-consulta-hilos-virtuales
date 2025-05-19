package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> urls = Procesos.readUrls("urls_parcial1.txt");
        List<Thread> threads = new ArrayList<>();
        List<LectorUrl> processors = new ArrayList<>();

        for (String url : urls) {
            LectorUrl processor = new LectorUrl(url);
            Thread thread = Thread.startVirtualThread(processor);
            threads.add(thread);
            processors.add(processor);
        }

        for (Thread t : threads) t.join();

        Procesos.writeResults("resultados.csv", processors);
        System.out.println("-El archivo csv se ha generado correctamente.");
    }
}
