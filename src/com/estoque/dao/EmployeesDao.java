/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estoque.dao;

import com.estoque.banco.ConexaoBD;
import com.estoque.model.Employees;
import com.estoque.model.User;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeesDao extends ConexaoBD {

    public boolean daoSaveemployees(Employees employees, User user) {

        String SalveEmployees = "call sp_save_employees ("
                + "'" + employees.getEmployees_name()+ "',"
                + "'" + employees.getEmployees_function()+ "',"
                + "'" + user.getLogin()+ "',"
                + "'" + user.getPassword()+ "',"
                + "'" + user.getProfile()+ "'"
                + ")";
        try {
            this.getConectar();
            this.executarSql(SalveEmployees);
            return true;
        } catch (Exception erro) {
            erro.printStackTrace();
            return false;
        } finally {
             this.getfecharConexao();
        }

    }

 /*   public ArrayList<Employees> daoListemployees() {
        ArrayList<Employees> listemployees = new ArrayList<>();
        Employees employees = new Employees();
        
        try {
            this.getConectar();
            this.executarSql("sp_update_employees"
                + ""
            );
        } catch (Exception e) {
        
        } finally {
            this.getfecharConexao();
        }
    };
*/
    /**
     * metodo listar funcionarios
     *
     * @return
     */
     public ArrayList<User> daoListemployees(){
        ArrayList<User> listEmployees = new ArrayList<>();
        User user = new User();
        Employees employees = new Employees();
        try {
            this.getConectar();
            this.executarSql("select e.id,e.employees_name, e.employees_function, u.login, u.password, u.profile\n" +
"		from tb_employees e inner join tb_user u on e.id = u.id_employees");
            while(this.getResultSet().next()){
                user = new User();
                employees = new Employees();
                        
                employees.setId(this.getResultSet().getInt(1));
                employees.setEmployees_name(this.getResultSet().getString(2));
                employees.setEmployees_function(this.getResultSet().getString(3));
                
                
                user.setLogin(this.getResultSet().getString(4));
                user.setPassword(this.getResultSet().getString(5));
                user.setProfile(this.getResultSet().getString(6));
                
                user.setEmployees(employees);
                listEmployees.add(user);
            }
        }catch(SQLException e){
            System.out.println("Erro: "+e.getMessage());
        }finally{
            this.getfecharConexao();
        }
        return listEmployees;
    }
}
