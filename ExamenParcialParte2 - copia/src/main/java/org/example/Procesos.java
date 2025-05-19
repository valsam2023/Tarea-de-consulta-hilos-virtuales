package org.example;

import java.io.*;
import java.net.URI;
import java.util.*;

public class Procesos {
    public static List<String> readUrls(String path) throws IOException {
        return new ArrayList<>(new HashSet<>(
            java.nio.file.Files.readAllLines(new File(path).toPath())
        ));
    }

    public static void writeResults(String path, List<LectorUrl> results) throws IOException {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.println("URL,CantidadInternas");
            for (LectorUrl p : results) {
                writer.printf("%s,%d%n", p.getUrl(), p.getInternalLinks());
            }
        }
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost() != null ? uri.getHost().replace("www.", "") : "";
        } catch (Exception e) {
            return "";
        }
    }
}
