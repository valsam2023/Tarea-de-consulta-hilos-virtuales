package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectorUrl implements Runnable {
    private final String url;
    private int internalLinks = 0;

    public LectorUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            URL urlObj = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
            String line;
            StringBuilder html = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                html.append(line);
            }
            reader.close();

            String domain = Procesos.getDomainName(url);
            Set<String> internalUrls = new HashSet<>();

            Pattern pattern = Pattern.compile("href=[\"'](http[s]?://[^\"']+)[\"']");
            Matcher matcher = pattern.matcher(html.toString());

            while (matcher.find()) {
                String foundUrl = matcher.group(1);
                if (foundUrl.contains(domain)) {
                    internalUrls.add(foundUrl);
                }
            }

            internalLinks = internalUrls.size();
        } catch (Exception e) {
            internalLinks = -1;
        }
    }

    public String getUrl() {
        return url;
    }

    public int getInternalLinks() {
        return internalLinks;
    }
}
