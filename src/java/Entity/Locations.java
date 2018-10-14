package Entity;

import Utils.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Locations
{
    int id;
    String latitude, longitude;
    Users usr_id;
    LocalDateTime time;
    
    public Locations(){}

    public Locations(int id, String latitude, String longitude, Users usr_id, LocalDateTime time) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.usr_id = usr_id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Users getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(Users usr_id) {
        this.usr_id = usr_id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    public boolean Ins_Upd()
    {
        String sql;
        if(id == 0)
            sql = "insert into locations (usr_id, loc_lat, loc_long, loc_time) "
                                +"values ('$2','$3','$4','$5')";
        else
            sql = "update locations set usr_id='$2',loc_lat='$3',loc_long='$4',loc_time='$5' where loc_id='$1'";
        
        sql = sql.replace("$1", id+"");
        sql = sql.replace("$2", usr_id.getId()+"");
        sql = sql.replace("$3", latitude);
        sql = sql.replace("$4", longitude);
        sql = sql.replace("$5", time.toString()+"");
        
        Conexao con = new Conexao();
        
        return con.manipular(sql);
    }
     
    public boolean Delete()
    {
        String sql = "delete from locations where loc_id="+id;
        
        Conexao con = new Conexao();
        return con.manipular(sql);
    }
     
    public ArrayList<Locations> Select(String condicao)
    {
        Users usr;
        ArrayList<Locations> lista = new ArrayList();
        String sql = "select * from locations where "+condicao;
        ResultSet rs=new Conexao().consultar(sql);

        try
        {
            while(rs.next())
            {
                usr = new Users();
                usr.setId(rs.getInt("usr_id"));
                usr.get();
                lista.add(new Locations(rs.getInt("loc_id"),rs.getString("loc_lat"),rs.getString("loc_long"),usr,rs.getTimestamp("loc_time").toLocalDateTime()));
                /*int cont = 30;
                while(rs.next() && cont!=0)
                    cont--;*/
            }
        } catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
         
        return lista;
    }
    
    public boolean get()
    {
        String sql = "select * from locations where usr_id = "+id;
        ResultSet rs=new Conexao().consultar(sql);
        try
        {
            if(rs.next())
            {
                usr_id = new Users();
                usr_id.setId(rs.getInt("usr_id"));
                usr_id.get();
                latitude = rs.getString("loc_lat");
                longitude = rs.getString("loc_long");
                time = rs.getTimestamp("loc_time").toLocalDateTime();
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

    public boolean lastLoc(int usr)
    {
        String sql = "select * from locations where usr_id = "+usr+" order by loc_time desc limit 1";
        ResultSet rs=new Conexao().consultar(sql);
        try
        {
            if(rs.next())
            {
                id = rs.getInt("loc_id");
                usr_id = new Users();
                usr_id.setId(rs.getInt("usr_id"));
                usr_id.get();
                latitude = rs.getString("loc_lat");
                longitude = rs.getString("loc_long");
                time = rs.getTimestamp("loc_time").toLocalDateTime();
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
    
    public ArrayList<Locations> getAllLoc(String filt)
    {
        Locations loc;
        ArrayList<Locations> lista = new ArrayList();
        String sql = "select * from users u inner join roles r on r.rol_id = u.rol_id"
                + " where upper(u.usr_name) like upper('%"+filt+"%') or upper(r.rol_description) like upper('%"+filt+"%')";
         
        ResultSet rs=new Conexao().consultar(sql);
         
        try
        {
            while(rs.next())
            {
                loc = new Locations();
                loc.lastLoc(rs.getInt("usr_id"));
                if (loc.getId() > 0)
                    lista.add(loc);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
         
        return lista;
    }
    
}
