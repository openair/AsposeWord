package com.asposewords.restfulapi;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Abdul Basit Ali
 */
@WebServlet(urlPatterns = {"/ServletClass"})
public class ServletClass extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpClientClass clientObj = new HttpClientClass();

        if (request.getParameter("name").equals("put")) {
            try {
                
                String[] ss = request.getParameterValues("name");

                String queryFormed = ss[1].toString();
                String[] splitQuery = queryFormed.split(",");
                String responseMessage = "";
                try {
                    responseMessage = clientObj.httpPutMethod(splitQuery[1], splitQuery[2]);//srcFile and format
                } catch (Exception ee) {
                }
                String json = new Gson().toJson(responseMessage);
                response.setContentType("application/json");
                response.getWriter().write(json);
            } catch (IOException ex) {
                Logger.getLogger(ServletClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (request.getParameter("name").equals("post")) {
            try {
                
                String[] ss = request.getParameterValues("name");

                String queryFormed = ss[1].toString();
                String[] splitQuery = queryFormed.split(",");
                String responseMessage = "";
                try {
                    responseMessage = clientObj.httpPostMethod(splitQuery[1]);//srcFile  
                } catch (Exception ee) {
                }
                String json = new Gson().toJson(responseMessage);
                response.setContentType("application/json");
                response.getWriter().write(json);
            } catch (IOException ex) {
                Logger.getLogger(ServletClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (request.getParameter("name").equals("get")) {
            try {
                
                String[] ss = request.getParameterValues("name");

                String queryFormed = ss[1].toString();
                String[] splitQuery = queryFormed.split(",");
                String responseMessage = "";
                try {
                    responseMessage = clientObj.httpGetMethod(splitQuery[1]);//srcFile
                } catch (Exception ee) {
                }
                String json = new Gson().toJson(responseMessage);
                response.setContentType("application/json");
                response.getWriter().write(json);
            } catch (IOException ex) {
                Logger.getLogger(ServletClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
