package com.github.bartcowski.authorization.client;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class BrowserOpener {

    public void browse(String url) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(URI.create(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
