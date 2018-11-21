/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.controller;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.petstar.dto.UrlKioscosDTO;
import org.petstar.model.OutputJson;
import org.petstar.model.ResponseJson;
import static org.petstar.configurations.Utils.convertStringToSql;
import org.petstar.dao.UrlKioscoDao;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UserDTO;

/**
 *
 * @author Ramiro
 */
public class ControllerUrlKioscos {
    private static final String MSG_LOGOUT = "Inicie sesión nuevamente";
    private static final String MSG_ERROR  = "Descripción de error: ";
    private static final String MSG_SUCESS = "OK";
    private static final String MSG_INVALID = "Valor o Descripción ya existe";
    private static final String MSG_PERFIL = "Este perfil no cuenta con los permisos para realizar la acción";
    
    public OutputJson insertUrlKioscos(HttpServletRequest request){
        ControllerAutenticacion autenticacion = new ControllerAutenticacion();
        ResponseJson response = new ResponseJson();
        OutputJson output = new OutputJson();
        
        Gson gson = new Gson();
        
        try{
            UserDTO sesion = autenticacion.isValidToken(request);
            if(sesion != null){
                if(sesion.getId_perfil() == 1){
                    UrlKioscoDao urlDao = new UrlKioscoDao();
                    String jsonString = request.getParameter("data");
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    UrlKioscosDTO url = gson.fromJson(jsonResponse.getJSONObject("url").toString(), UrlKioscosDTO.class);
                    
                    ResultInteger result = urlDao.validaUrl(url.getUrl());

                    if(result.getResult().equals(0)){
                        url.setFecha_registro(convertStringToSql(url.getFecha_registro_string()));
                        urlDao.insertUrlKiosco(url);

                        response.setMessage(MSG_SUCESS);
                        response.setSucessfull(true);
                    }else{
                        response.setMessage(MSG_INVALID);
                        response.setSucessfull(false);
                    }
                }else{
                    response.setMessage(MSG_PERFIL);
                    response.setSucessfull(false);
                }
            }else{
                response.setMessage(MSG_LOGOUT);
                response.setSucessfull(false);
            }
        }catch(Exception ex){
            response.setMessage(MSG_ERROR + ex.getMessage());
            response.setSucessfull(false);
        }
        output.setResponse(response);
        return output;
    }
}
