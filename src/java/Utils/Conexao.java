package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao
{   private Connection connect;
    private String erro="";

    public Conexao() {
        this("org.postgresql.Driver","jdbc:postgresql://localhost/",
                                        "hackathon","postgres","postgres123");
    }
    
    public Conexao(String driver,String local,String banco,String usuario,String senha)
    {   try {
            Class.forName(driver);
            String url = local+banco;
            connect = DriverManager.getConnection( url, usuario,senha);
            
            Statement statement = connect.createStatement();
            statement.executeUpdate("set client_encoding to 'UTF-8'");
        }
        catch ( ClassNotFoundException cnfex )
        { erro="Falha ao ler o driver JDBC: " + cnfex.toString(); }
        catch ( SQLException sqlex )
        { erro="Impossivel conectar com a base de dados: " + sqlex.toString(); }
        catch ( Exception ex )
        { erro="Outro erro: " + ex.toString(); }    
    }
    public String getMensagemErro() {
        return erro;
    }
    public boolean getEstadoConexao() {
        return connect != null;
    }
    public Connection getConnect() {
        return connect;
    }
    
    public boolean manipular(String sql)
    {
        try {
            Statement statement = connect.createStatement();
            
            int result = statement.executeUpdate( sql );
            statement.close();
            connect.close();
            //if(result>=1)
                return true;
        }
        catch ( SQLException sqlex )
        {  erro="Erro: "+sqlex.toString();
           return false;
        }
        //return false;
    }
    
    public boolean manipular2(String sql)
	{
        try {
            Statement statement = connect.createStatement();
            int result = statement.executeUpdate( sql );
            statement.close();
            //if(result>=1)
                return true;
        }
        catch ( SQLException sqlex )
        {  erro="Erro: "+sqlex.toString();
           return false;
        }
        //return false;
    }
    
    public ResultSet consultar2(String sql)
    {   ResultSet rs;
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery( sql );
        }
        catch ( SQLException sqlex )
        { erro="Erro: "+sqlex.toString();
          return null;
        }
        return rs;
    }
    
    public ResultSet consultar(String sql)
    {   ResultSet rs;
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery( sql );
            connect.close();
        }
        catch ( SQLException sqlex )
        { erro="Erro: "+sqlex.toString();
          return null;
        }
        return rs;
    }
    public int getMaxPK(String tabela,String chave) 
    {
        String sql="select max("+chave+") from "+tabela;
        int max=0;
        ResultSet rs= consultar2(sql);
        try 
        {
            if(rs.next())
                max=rs.getInt(1);
        }
        catch (SQLException sqlex)
        { 
             erro="Erro: " + sqlex.toString();
             return -1;
        }
        return max;
    }
}