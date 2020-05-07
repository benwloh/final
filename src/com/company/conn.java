package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface conn {
    boolean active = true;
    String getResp();
    void caccept() throws IOException;
    void connclose() throws IOException;
}
