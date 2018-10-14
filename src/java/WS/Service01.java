package WS;

import Entity.Format;
import Entity.Locations;
import Entity.Notifications;
import Entity.Roles;
import Entity.Users;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * REST Web Service
 *
 * @author Leonardo
 */
@Path("service01")
public class Service01 {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Teste
     */
    public Service01() {
    }

    /**
     * Retrieves representation of an instance of WS.Teste
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/text")
    //MediaType.APPLICATION_JSON
    public String getJson() {
        return "";
    }
    
    @GET
    @Produces("application/json")
    @Path("user/location/{id}")
    public String getUsuario(@PathParam("id") int id)
    {
        Format f = new Format();
        Gson g = new Gson();
        Locations l = new Locations();
        
        f.setSuccess(l.lastLoc(id));
        f.setMessage("");
        f.setData(l);
        return g.toJson(f);
    }
    
    @GET
    @Produces("application/json")
    @Path("user/locations/{id}")
    public String getLocations(@PathParam("id") String filter)
    {
        Format f = new Format();
        Gson g = new Gson();
        Locations l = new Locations();
        ArrayList<Locations> lista;
        lista = l.Select(" usr_id = '"+filter+"'");
        f.setSuccess(!lista.isEmpty());
        f.setMessage("");
        f.setData(lista);
        return g.toJson(f);
    }
    
    @GET
    @Produces("application/json")
    @Path("notification/{id}")
    public String getNotification(@PathParam("id") int id)
    {
        Format f = new Format();
        Gson g = new Gson();
        Notifications n = new Notifications();
        n.setId(id);
        f.setSuccess(n.get());
        f.setMessage("");
        f.setData(n);
        return g.toJson(f);
    }
    
    @POST
    @Consumes({"application/json"})
    @Path("user/login")
    public String login(String content)
    {
        JSONParser ps = new JSONParser();
        try
        {
            JSONObject obj = (JSONObject) ps.parse(content);
            Gson g = new Gson();
            Format f = new Format();
            Users u = new Users();
            Roles r = new Roles();
            String usr, psswd;
            usr = (String)obj.get("username");
            psswd = (String)obj.get("password");
            
            u.setUsername(usr);
            u.setPassword(psswd);
            
            f.setSuccess(u.login());
            f.setMessage("");
            f.setData(u);
            return g.toJson(f);
        }
        catch (ParseException e)
        {
            return "Erro!";
        }
    }
    
   @POST
    @Consumes({"application/json"})
    @Path("user/location")
    public String getAllLocations(String content)
    {
        JSONParser ps = new JSONParser();
        try
        {
            JSONObject obj = (JSONObject) ps.parse(content);
            Gson g = new Gson();
            Format f = new Format();
            Locations l = new Locations();
            String filter;
            ArrayList<Locations> lista;
            filter = (String)obj.get("filter");
            
            lista = l.getAllLoc(filter);
            //l.setPassword(psswd);
            f.setSuccess(!lista.isEmpty());
            f.setMessage("");
            f.setData(lista);
            return g.toJson(f);
        }
        catch (ParseException e)
        {
            return "Erro!";
        }
    }
    
    @POST
    @Consumes({"application/json"})
    @Path("user/location/save")
    public String setLocation(String content)
    {
        JSONParser ps = new JSONParser();
        try
        {
            JSONObject obj = (JSONObject) ps.parse(content);
            Gson g = new Gson();
            Format f = new Format();
            Users u = new Users();
            Locations l = new Locations();
            
            l.setLatitude((String)obj.get("latitude"));
            l.setLongitude((String)obj.get("longitude"));
            u.setId(Integer.parseInt((String)obj.get("id")));
            l.setUsr_id(u);
            l.setTime(LocalDateTime.now());
            
            f.setSuccess(l.Ins_Upd());
            f.setMessage("");
            f.setData("");
            return g.toJson(f);
        }
        catch (ParseException e)
        {
            return "Erro!";
        }
    }
    
    @POST
    @Consumes({"application/json"})
    @Path("user/status")
    public String setStatus(String content)
    {
        JSONParser ps = new JSONParser();
        try
        {
            JSONObject obj = (JSONObject) ps.parse(content);
            Gson g = new Gson();
            Format f = new Format();
            Users u = new Users();
            
            u.setId(Integer.parseInt((String)obj.get("id")));
            u.get();
            u.setStatus(Integer.parseInt((String)obj.get("status")));
            
            f.setSuccess(u.Ins_Upd());
            f.setMessage("");
            f.setData("");
            return g.toJson(f);
        }
        catch (ParseException e)
        {
            return "Erro!";
        }
    }
    
    @POST
    @Consumes({"application/json"})
    @Path("user/notification/send")
    public String sendNotification(String content)
    {
        JSONParser ps = new JSONParser();
        try
        {
            JSONObject obj = (JSONObject) ps.parse(content);
            Gson g = new Gson();
            Format f = new Format();
            Users usend, urcv, uans;
            Roles rol;
            Notifications n = new Notifications();

            if (Integer.parseInt((String)obj.get("usr_idreceive")) == 0)
            {
                n.setUsr_idrcv(null);
                n.setRsv_time(null);
            }
            else
            {
                urcv = new Users();
                urcv.setId(Integer.parseInt((String)obj.get("usr_idreceive")));
                n.setUsr_idrcv(urcv);
                n.setRsv_time(LocalDateTime.now());
            }
            
            if (Integer.parseInt((String)obj.get("usr_idanswer")) == 0)
                n.setUsr_idans(null);
            else
            {
                uans = new Users();
                uans.setId(Integer.parseInt((String)obj.get("usr_idanswer")));
                n.setUsr_idans(uans);
            }
            
            if (Integer.parseInt((String)obj.get("rol_id")) == 0)
                n.setRol_id(null);
            else
            {
                rol = new Roles();
                rol.setId(Integer.parseInt((String)obj.get("usr_idreceive")));
                n.setRol_id(rol);
            }
            
            n.setCrt_time(LocalDateTime.now());
            n.setUrgency(Integer.parseInt((String)obj.get("not_urgency")));
            n.setStatus(Integer.parseInt((String)obj.get("status")));
            usend = new Users();
            usend.setId(Integer.parseInt((String)obj.get("usr_idsend")));
            n.setUsr_idsend(usend);
            n.setMssage((String)obj.get("message"));
            
            f.setSuccess(n.Ins_Upd());
            f.setMessage("");
            f.setData("");
            return g.toJson(f);
        }
        catch (ParseException e)
        {
            return "Erro!";
        }
    }
    
    @POST
    @Consumes({"application/json"})
    @Path("notification/getall")
    public String getAllNotifications(String content)
    {
        JSONParser ps = new JSONParser();
        try
        {

            JSONObject obj = (JSONObject) ps.parse(content);
            Gson g = new Gson();
            Format f = new Format();
            ArrayList<Notifications> lista = new ArrayList();
            Notifications n = new Notifications();

            lista = n.getAll(Integer.parseInt((String)obj.get("role")), Integer.parseInt((String)obj.get("id")));
            
            f.setSuccess(!lista.isEmpty());
            f.setMessage("");
            f.setData(lista);
            return g.toJson(f);
        }
        catch (ParseException e)
        {
            return "Erro!";
        }
    }
    
}