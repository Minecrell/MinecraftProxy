package net.minecrell.minecraftproxy.logging;

import net.minecrell.minecraftproxy.util.Closeable;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ProxyLogger extends Logger implements Closeable {
    private final FileHandler fileHandler;

    public ProxyLogger() {
        super("MinecraftProxy", null);

        Formatter formatter = new LoggingFormatter();

        Handler handler = new ConsoleHandler();
        handler.setFormatter(formatter);
        this.addHandler(handler);

        if ((this.fileHandler = this.createFileHandler()) != null) {
            fileHandler.setFormatter(formatter);
            this.addHandler(fileHandler);
        }
    }

    private FileHandler createFileHandler() {
        try {
            return new FileHandler("proxy.log", 10 * 1024 * 1024, 1, true);
        } catch (IOException e) {
            this.log(Level.SEVERE, "Unable to start file logging handler!", e); return null;
        }
    }

    @Override
    public void close() {
        if (fileHandler != null) {
            fileHandler.flush(); fileHandler.close();
        }
    }
}
