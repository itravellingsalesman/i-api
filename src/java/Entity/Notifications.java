package Entity;

import Utils.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Notifications
{
    private int id, status, urgency;
    private String message;
    private LocalDateTime crt_time, rsv_time;
    private Users usr_idsend, usr_idrcv, usr_idans;
    private Roles rol_id;

    public Notifications(){}
    
    public Notifications(int id, int status, String mssage, LocalDateTime crt_time, LocalDateTime rsv_time, Users usr_idsend, Users usr_idrcv, Users usr_idans, int urgency, Roles rol_id) {
        this.id = id;
        this.status = status;
        this.message = mssage;
        this.crt_time = crt_time;
        this.rsv_time = rsv_time;
        this.usr_idsend = usr_idsend;
        this.usr_idrcv = usr_idrcv;
        this.usr_idans = usr_idans;
        this.urgency = urgency;
        this.rol_id = rol_id;
    }

    public Roles getRol_id() {
        return rol_id;
    }

    public void setRol_id(Roles rol_id) {
        this.rol_id = rol_id;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMssage() {
        return message;
    }

    public void setMssage(String mssage) {
        this.message = mssage;
    }

    public LocalDateTime getCrt_time() {
        return crt_time;
    }

    public void setCrt_time(LocalDateTime crt_time) {
        this.crt_time = crt_time;
    }

    public LocalDateTime getRsv_time() {
        return rsv_time;
    }

    public void setRsv_time(LocalDateTime rsv_time) {
        this.rsv_time = rsv_time;
    }

    public Users getUsr_idsend() {
        return usr_idsend;
    }

    public void setUsr_idsend(Users usr_idsend) {
        this.usr_idsend = usr_idsend;
    }

    public Users getUsr_idrcv() {
        return usr_idrcv;
    }

    public void setUsr_idrcv(Users usr_idrcv) {
        this.usr_idrcv = usr_idrcv;
    }

    public Users getUsr_idans() {
        return usr_idans;
    }

    public void setUsr_idans(Users usr_idans) {
        this.usr_idans = usr_idans;
    }
    
    public boolean Ins_Upd()
    {
        String sql;
        if(id == 0)
            sql = "insert into notifications (not_message, not_status, not_createdtime, not_resolvedtime, usr_idsend,"
                    + "usr_idreceive,usr_idanswer, not_urgency, rol_id) "
                    +"values ('$2','$3','$4','$5','$6','$7','$8','$9','$A')";
        else
            sql = "update notifications set not_message='$2',not_status='$3',not_createdtime='$4',not_resolvedtime='$5',"
                    + "usr_idsend='$6',usr_idreceive='$7',usr_idanswer='$8',not_urgency='$9',rol_id='$A'"
                    + " where not_id='$1'";
        
        sql = sql.replace("$1", id+"");
        sql = sql.replace("$2", message);
        sql = sql.replace("$3", status+"");
        sql = sql.replace("$4", crt_time.toString());
        sql = sql.replace("$6", usr_idsend.getId()+"");
        sql = sql.replace("$9", urgency+"");
        //Received Time
        if (rsv_time != null)
            sql = sql.replace("$5", rsv_time.toString()+"");
        else
            sql = sql.replace("'$5'","null");
        //Received id
        if (usr_idrcv != null)
            sql = sql.replace("$7", usr_idrcv.getId()+"");
        else
            sql = sql.replace("'$7'","null");
        //Answer Id
        if (usr_idans!= null)
            sql = sql.replace("$8", usr_idans.getId()+"");
        else
            sql = sql.replace("'$8'", "null");
        //Role Id
        if (rol_id!= null)
            sql = sql.replace("$A", rol_id.getId()+"");
        else
            sql = sql.replace("'$A'", "null");
        
        Conexao con = new Conexao();

        return con.manipular(sql);
        //return con.getMensagemErro();
    }
     
    public boolean Delete()
    {
        String sql = "delete from users where usr_id="+id;
        
        Conexao con = new Conexao();
        return con.manipular(sql);
    }
     
    public ArrayList<Notifications> Select(String condicao)
    {
        //Notifications role = new ();
        ArrayList<Notifications> lista = new ArrayList();
        String sql = "select * from notifications where "+condicao;
        Users usend, urcv, uans;
        Roles rol;
        ResultSet rs=new Conexao().consultar(sql);
         
        try
        {
            while(rs.next())
            {
                usend = new Users();
                usend.setId(rs.getInt("usr_idsend"));
                usend.get();
                urcv = new Users();
                urcv.setId(rs.getInt("usr_idreceive"));
                urcv.get();
                uans = new Users();
                uans.setId(rs.getInt("usr_idanswer"));
                uans.get();
                rol = new Roles();
                rol.setId(rs.getInt("rol_id"));
                rol.get();   
                
                lista.add(new Notifications(rs.getInt("usr_id"),rs.getInt("usr_status"),rs.getString("message"),rs.getTimestamp("crt_time").toLocalDateTime(),rs.getTimestamp("rsv_time").toLocalDateTime(),usend,urcv,uans, rs.getInt("not_urgency"),rol));
            }
        } catch (SQLException e)
        {
            System.out.println(e);
        }
         
        return lista;
    }
    
    public boolean get()
    {
        String sql = "select * from notifications where not_id = "+id;
        
        ResultSet rs=new Conexao().consultar(sql);
        try
        {
            if(rs.next())
            {
                if (rs.getInt("usr_idsend") > 0)
                {
                    usr_idsend = new Users();
                    usr_idsend.setId(rs.getInt("usr_idsend"));
                    usr_idsend.get();
                }
                
                if (rs.getInt("usr_idreceive") > 0)
                {
                    usr_idrcv = new Users();
                    usr_idrcv.setId(rs.getInt("usr_idreceive"));
                    usr_idrcv.get();
                }
                
                if (rs.getInt("usr_idanswer") > 0)
                {
                    usr_idans = new Users();
                    usr_idans.setId(rs.getInt("usr_idanswer"));
                    usr_idans.get();
                }
                
                if (rs.getInt("rol_id") > 0)
                {
                    rol_id = new Roles();
                    rol_id.setId(rs.getInt("rol_id"));
                    rol_id.get();
                }
                
                status = rs.getInt("not_status");
                message = rs.getString("not_message");
                if (rs.getTimestamp("not_createdtime") != null)
                    crt_time = rs.getTimestamp("not_createdtime").toLocalDateTime();
                if (rs.getTimestamp("not_resolvedtime") != null)
                    rsv_time = rs.getTimestamp("not_resolvedtime").toLocalDateTime();
                urgency = rs.getInt("not_urgency");
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
    
    public ArrayList<Notifications> getAll(int rol, int usu)
    {
        LocalDateTime dtcrt, dtrcv;
        ArrayList<Notifications> lista = new ArrayList();
        String sql;
        if (rol > 0)
            sql = "select * from notifications where usr_idsend = "+usu+" and not_status = 1"
                  +" or rol_id = "+rol;
        else
            sql = "select * from notifications where usr_idsend = "+usu+" and not_status = 1 or usr_idreceive = "+usu;
        
        Users usend, urcv, uans;
        Roles role;
        ResultSet rs=new Conexao().consultar(sql);
        
        try
        {
            usend = new Users();
            urcv = new Users();
            uans = new Users();
            role = new Roles();
            while(rs.next())
            {
                if (rs.getInt("usr_idsend") > 0)
                {
                    
                    usend.setId(rs.getInt("usr_idsend"));
                    usend.get();
                }
                if (rs.getInt("usr_idreceive") > 0)
                {
                    urcv.setId(rs.getInt("usr_idreceive"));
                    urcv.get();
                }
                if (rs.getInt("usr_idanswer") > 0)
                {
                    uans.setId(rs.getInt("usr_idanswer"));
                    uans.get();
                }
                if (rs.getInt("rol_id") > 0)
                { 
                    role.setId(rs.getInt("rol_id"));
                    role.get();   
                }
                
                if (rs.getTimestamp("not_createdtime") == null)
                    dtcrt = null;   
                else
                    dtcrt = rs.getTimestamp("not_createdtime").toLocalDateTime();
                
                if (rs.getTimestamp("not_resolvedtime") == null)
                    dtrcv = null;   
                else
                    dtrcv = rs.getTimestamp("not_resolvedtime").toLocalDateTime();
                
                lista.add(new Notifications(rs.getInt("not_id"),rs.getInt("not_status"),rs.getString("not_message"),dtcrt,dtrcv,usend,urcv,uans, rs.getInt("not_urgency"),role));
            }
        } catch (SQLException e)
        {
            System.out.println(e);
        }
         
        return lista;
    }
}
