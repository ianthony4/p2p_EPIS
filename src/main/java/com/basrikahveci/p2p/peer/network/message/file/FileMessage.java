package com.basrikahveci.p2p.peer.network.message.file;

import com.basrikahveci.p2p.peer.Peer;
import com.basrikahveci.p2p.peer.network.Connection;
import com.basrikahveci.p2p.peer.network.message.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class FileMessage implements Message, Serializable {

    private final String fileName;
    private final byte[] fileData;

    public FileMessage(String fileName, byte[] fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    @Override
    public void handle(Peer peer, Connection connection) {
        try {
            File outDir = new File("received_files");
            if (!outDir.exists()) outDir.mkdirs();
            File outFile = new File(outDir, fileName);
            try (FileOutputStream fos = new FileOutputStream(outFile)) {
                fos.write(fileData);
            }
            System.out.println("Archivo recibido y guardado: " + outFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileMessage fromFile(File file) throws IOException {
        return new FileMessage(file.getName(), Files.readAllBytes(file.toPath()));
    }
}

