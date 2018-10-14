package Entity;

import Utils.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Users
{
    private int id, status;
    private String username, password, name, img;
    private Roles rol_id;

    public Users(){}

    public Users(int id, int status, String username, String password, String name, String img, Roles rol_id) {
        this.id = id;
        this.status = status;
        this.username = username;
        this.password = password;
        this.name = name;
        this.img = img;
        this.rol_id = rol_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Roles getRol_id() {
        return rol_id;
    }

    public void setRol_id(Roles rol_id) {
        this.rol_id = rol_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean Ins_Upd()
    {
        String sql;
        if(id == 0)
            sql = "insert into users (usr_name, usr_username, usr_password, usr_status, rol_id, usr_img) "
                                    +"values ('$2','$3','$4','$5','$6','$7')";
        else
            sql = "update users set usr_name='$2',usr_username='$3',usr_password='$4',usr_status='$5',rol_id='$6',usr_img='$7' where usr_id='$1'";
        
        sql = sql.replace("$1", id+"");
        sql = sql.replace("$2", name);
        sql = sql.replace("$3", username);
        sql = sql.replace("$4", password);
        sql = sql.replace("$5", status+"");
        sql = sql.replace("$6", rol_id.getId()+"");
        sql = sql.replace("$7", img);
        Conexao con = new Conexao();
        
        return con.manipular(sql);
    }
     
    public boolean Delete()
    {
        String sql = "delete from users where usr_id="+id;
        
        Conexao con = new Conexao();
        return con.manipular(sql);
    }
     
    public ArrayList<Users> Select(String condicao)
    {
        Roles role;
        ArrayList<Users> lista = new ArrayList();
        String sql = "select * from users where "+condicao;
         
        ResultSet rs=new Conexao().consultar(sql);
         
        try
        {
            while(rs.next())
            {
                role = new Roles();
                role.setId(rs.getInt("rol_id"));
                role.get();
                lista.add(new Users(rs.getInt("usr_id"),rs.getInt("usr_status"),rs.getString("usr_username"),rs.getString("usr_password"),rs.getString("usr_name"),rs.getString("usr_img"),role));
            }
        } catch (SQLException e)
        {
            System.out.println(e);
        }
         
        return lista;
    }
    
    public boolean get()
    {
        String sql = "select * from users where usr_id = "+id;
        
        ResultSet rs=new Conexao().consultar(sql);
        try
        {
            if(rs.next())
            {
                rol_id = new Roles();
                rol_id.setId(rs.getInt("rol_id"));
                rol_id.get();
                status = rs.getInt("usr_status");
                username = rs.getString("usr_username");
                password = rs.getString("usr_password");
                name = rs.getString("usr_name");
                img = rs.getString("usr_img");
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
    
    public boolean login()
    {
        String sql = "select * from users where usr_username='$1' and usr_password='$2'";
        sql = sql.replace("$1", username);
        sql = sql.replace("$2", password);
        
        ResultSet rs=new Conexao().consultar(sql);
        try
        {
            if(rs.next())
            {
                rol_id = new Roles();
                rol_id.setId(rs.getInt("rol_id"));
                rol_id.get();
                id = rs.getInt("usr_id");
                status = rs.getInt("usr_status");
                username = rs.getString("usr_username");
                password = rs.getString("usr_password");
                name = rs.getString("usr_name");
                img = rs.getString("usr_img");
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
}