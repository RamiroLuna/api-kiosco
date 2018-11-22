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
import static org.petstar.configurations.Utils.sumarFechasDias;
import org.petstar.dto.ResultInteger;
import org.petstar.dto.UrlKioscosDTO;

/**
 *
 * @author Ramiro
 */
public class UrlKioscoDao {
    public void insertUrlKiosco(UrlKioscosDTO url) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertPetUrlKioskos ?, ?, ?, ?");
        Object[] params = {url.getDescripcion(), url.getUrl(), url.getId_usuario_registro(), url.getFecha_registro()};
        
        qr.update(sql.toString(), params);
    }
    
    public ResultInteger validaUrl(String url) throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_insertValidaUrl ?");
        Object[] params = {url};
        
        ResultSetHandler rsh = new BeanHandler(ResultInteger.class);
        ResultInteger count = (ResultInteger)  qr.query(sql.toString(), rsh, params);
        return count;
    }
    
    public List<UrlKioscosDTO> getUrlKiosco() throws Exception{
        DataSource ds = PoolDataSource.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        StringBuilder sql = new StringBuilder();
        
        sql.append("EXEC sp_selectUrlKiosco");
        
        ResultSetHandler rsh = new BeanListHandler(UrlKioscosDTO.class);
        List<UrlKioscosDTO> lista = (List<UrlKioscosDTO>) qr.query(sql.toString(), rsh);
        
        for(UrlKioscosDTO url : lista){
            url.setFecha_registro(sumarFechasDias(url.getFecha_registro(), 2));
            url.setFecha_registro_string(convertSqlToDay(url.getFecha_registro()));
            
            if(url.getFecha_modifica_registro() != null){
                url.setFecha_modifica_registro(sumarFechasDias(url.getFecha_modifica_registro(), 2));
                url.setFecha_modifica_registro_string(convertSqlToDay(url.getFecha_modifica_registro()));
            }            
        }
        return lista;
    }
}
