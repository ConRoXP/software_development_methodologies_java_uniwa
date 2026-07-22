//The required fields are given in an index.html form.

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class echoDataEntered extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String fname = request.getParameter("fname");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String title = request.getParameter("title");
        String topic = request.getParameter("topic"); // DB / IS / AI / PL

        String topicLabel = topicToLabel(topic);

        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><meta charset='UTF-8'><title>Echo</title></head><body>");
            out.println("<h2>Data submitted</h2>");
            out.println("<ul>");
            out.println("<li>Name: " + safe(fname) + "</li>");
            out.println("<li>Surname: " + safe(surname) + "</li>");
            out.println("<li>Email: " + safe(email) + "</li>");
            out.println("<li>Title: " + safe(title) + "</li>");
            out.println("<li>Category: " + safe(topicLabel) + " (" + safe(topic) + ")</li>");
            out.println("</ul>");
            out.println("<a href='index.html'>Return to the form</a>");
            out.println("</body></html>");
        }
    }

    //If someone hits with GET, redirect to the form page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    private static String topicToLabel(String topic) {
        if (topic == null) return "";
        return switch (topic) {
            case "DB" -> "Data Bases";
            case "IS" -> "Information Systems";
            case "AI" -> "Artificial Intelligence";
            case "PL" -> "Programming Languages";
            default -> topic;
        };
    }

    //Escape HTML special characters
    private static String safe(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}