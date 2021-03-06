/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.service;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.petstar.configurations.Configuration;
import org.petstar.controller.ControllerUploadProtectorPantalla;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;

/**
 *
 * @author Tech-pro
 */

@MultipartConfig
@WebServlet(name = "UploadProtectorPantalla", urlPatterns = {"/UploadProtectorPantalla"})
public class UploadProtectorPantalla extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Configuration.setHeadersFile(response);
        OutputJson output = new OutputJson();
        Gson gson = new Gson();
        PrintWriter printWriter = response.getWriter();
        ControllerUploadProtectorPantalla pantalla = new ControllerUploadProtectorPantalla();
        try{
            
            String action = request.getParameter("action");

            switch(action){
                case "insertUploadProtectorPantalla":                    
                   output = pantalla.insertUploadProtectorPantalla(request);
                   break;
                case "getProtectorPantalla":
                    output = pantalla.getProtectorPantalla(request);
                   break;
                case "updateDatosProtectorPantalla":
                   output = pantalla.updateDatosProtectorPantalla(request);
                   break;
                case "updateImagen":
                   output = pantalla.updateImagen(request);
                   break;                   
                case "getAllImagen":
                   output = pantalla.getAllImagen(request);
                   break;
                case "seleccionImagen":
                   output = pantalla.seleccionImagen(request);
                   break;
                case "deleteImagen":
                   output = pantalla.deleteImagen(request);
                   break;
                case "cambiarStatus":
                   output = pantalla.changeStatus(request);
                   break;
            }
        } catch (Exception ex) {
            ResponseJson responseJson = new ResponseJson();
            responseJson.setMessage(ex.getMessage());
            responseJson.setSucessfull(false);
            output.setResponse(responseJson);
        } finally {
            printWriter.print(gson.toJson(output));
            printWriter.close();
        }
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Configuration.setHeadersFile(response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Service image";
    }// </editor-fold>

}
