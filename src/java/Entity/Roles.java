package Entity;

import Utils.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Roles
{
    int id, usr_admin;
    String description;

    public Roles(){}
    
    public Roles(int id, String rol_description, int usr_admin) {
        this.id = id;
        this.description = rol_description;
        this.usr_admin = usr_admin;
    }

    public int getUsr_admin() {
        return usr_admin;
    }

    public void setUsr_admin(int usr_admin) {
        this.usr_admin = usr_admin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public boolean Ins_Upd()
    {
        String sql;
        if(id == 0)
            sql = "insert into roles (rol_description, usr_admin) "
                                    +"values ('$2', '$3')";
        else
            sql = "update roles set rol_description='$2', rol_description='$3' where rol_id='$1'";
        
        sql = sql.replace("$1", id+"");
        sql = sql.replace("$2", description);
        sql = sql.replace("$3", usr_admin+"");
        
        Conexao con = new Conexao();
        
        return con.manipular(sql);
    }
    
    public boolean Delete()
    {
        String sql = "delete from roles where rol_id="+id;
        
        Conexao con = new Conexao();
        return con.manipular(sql);
    }
     
    public boolean get()
    {
        String sql = "select * from roles where rol_id = "+this.id;
        ResultSet rs=new Conexao().consultar(sql);
        try
        {
            if(rs.next())
            {
                description = rs.getString("rol_description");
                usr_admin = rs.getInt("usr_admin");
            }
            else
                return false;
        } catch (SQLException e)
        {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    public ArrayList<Roles> Select(String condicao)
    {
        ArrayList<Roles> lista = new ArrayList();
        String sql = "select * from roles where "+condicao; 
        ResultSet rs=new Conexao().consultar(sql);
         
        try
        {
            while(rs.next())
                lista.add(new Roles(rs.getInt("rol_id"),rs.getString("rol_description"),rs.getInt("usr_admin")));
        } catch (SQLException e)
        {
            System.out.println(e);
        } 
        return lista;
    }
}
