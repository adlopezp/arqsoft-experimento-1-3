/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.ecos.statusquo.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.crypto.hash.Sha512Hash;

/**
 *
 * @author Alvaro
 */
@WebServlet(name = "Sha512", urlPatterns = {"/sha512"})
public class Sha512 extends HttpServlet {

    public static final String key = "statusquo";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Sha512</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Sha512</h1>");
            out.println("<h3>" + request.getParameter("valor") + " = " + new Sha512Hash(request.getParameter("valor"), key, 1024).toString() + "</h3>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(401);
    }
}
