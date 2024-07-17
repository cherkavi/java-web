package com.example.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class EchoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Echo Servlet</h1>");
    }
}
