package net.tharow.systemshell;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
    @NonNull
    public static String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader isOut = new BufferedReader(new InputStreamReader(is))) {
            isOut.lines().forEach(s -> sb.append(s).append("\n"));
        }
        return sb.toString();
    }
}
