package com.resterwolf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите полный путь к файлу:");
        String fileName = scanner.nextLine();

        if (fileName == null || fileName.isEmpty()) {
            System.exit(0);
        }

        try {

            URI fileURI = new URI("file://" + fileName);
            File file = new File(fileURI);
            Document document = Jsoup.parse(file, StandardCharsets.UTF_8.name());
            Elements elementsTd = document.select("td:nth-child(3)");

            StringBuilder result = new StringBuilder();
            for (int i = 1; i < elementsTd.size(); i++) {
                result.append(elementsTd.get(i).text());
                if (i != elementsTd.size() - 1)
                    result.append(", ");
            }
            writeToClipboard(result.toString(), null);
            System.out.println("Данные скопированы в буфер!");

        } catch (URISyntaxException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            System.in.read();
        } catch (IOException ex) {

        }

        scanner.close();
    }

    public static void writeToClipboard(String s, ClipboardOwner owner) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(s);
        clipboard.setContents(transferable, owner);
    }


}
