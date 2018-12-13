/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petstar.dao;

import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.petstar.configurations.PoolDataSource;
import static org.petstar.configurations.Utils.convertSqlToDay;
import static org.petstar.configurations.Utils.convertSqlToDayHour;
import static org.petstar.configurations.Utils.sumarFechasDias;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.ResultString;
import org.petstar.dto.imagenDTO;

/**
 *
 * @author Ramiro
 */
public class UploadProtectorPantallaDAO {
    public void insertImagen(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertImagen ?, ?, ?, ?");
        Object[] params = {imagen.getNombre(), imagen.getDescripcion(), imagen.getImagen(), imagen.getId_usuario_registro()};
        
        qr.update(sql.toString(), params);
    }
    
    public imagenDTO getProtectorPantallaById(int idImagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectImagenById ?");
        Object[] params = {idImagen};
        
        ResultSetHandler rsh = new BeanHandler(imagenDTO.class);
        imagenDTO imagen = (imagenDTO) qr.query(sql.toString(), rsh, params);
        
        if(imagen != null){
            imagen.setFecha_registro(sumarFechasDias(imagen.getFecha_registro(), 2));
            imagen.setFecha_registro_string(convertSqlToDay(imagen.getFecha_registro()));
            
            if(imagen.getFecha_modifica_registro() != null){
                imagen.setFecha_modifica_registro_string(convertSqlToDayHour(imagen.getFecha_modifica_registro()));
            }
        }       
        return imagen;
    }
    
    public void updateDatosProtectorPantalla(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateDatosImagen ?, ?, ?, ?");
        Object[] params = {imagen.getId_imagen(), imagen.getNombre(), imagen.getDescripcion(), imagen.getId_usuario_modifica_registro()};
        
        qr.update(sql.toString(), params);
    }
    
    public void updateImagen(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateImagen ?, ?");
        Object[] params = {imagen.getId_imagen(), imagen.getImagen()};
        
        qr.update(sql.toString(), params);
    }
    
    public List<imagenDTO> getAllKioscos() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectAllImagen");
        
        ResultSetHandler rsh = new BeanListHandler(imagenDTO.class);
        List<imagenDTO> lista = (List<imagenDTO>) qr.query(sql.toString(), rsh);
        
        for(imagenDTO imagen : lista){
            imagen.setFecha_registro(sumarFechasDias(imagen.getFecha_registro(), 2));
            imagen.setFecha_registro_string(convertSqlToDay(imagen.getFecha_registro()));
            
            if(imagen.getFecha_modifica_registro() != null){
                imagen.setFecha_modifica_registro_string(convertSqlToDayHour(imagen.getFecha_modifica_registro()));
            }
        }
        return lista;
    }
    
    public ResultString seleccionImagen(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_seleccionImagen ?, ?");
        Object[] params = {imagen.getId_imagen(), imagen.getId_usuario_modifica_registro()};
        
        ResultSetHandler rsh = new BeanHandler(ResultString.class);
        ResultString fecha = (ResultString) qr.query(sql.toString(), rsh ,params);
        return fecha;
    }
    
    public ResultInteger validaSeleccionImagen(int idImagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_validaSeleccionImagen ?");
        Object[] params ={idImagen};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger result = (ResultInteger) qr.query(sql.toString(), rsh, params);
                
        return result;
    }
    
    public void updateDefaultImagen() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_updateSeleccionDefault");
        
        qr.update(sql.toString());
    }
    
    public void deleteImagen(imagenDTO imagen) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_deleteImagen ?");
        Object[] params = {imagen.getId_imagen()};
        
        qr.update(sql.toString(), params);
    }
}
