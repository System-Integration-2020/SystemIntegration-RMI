package dk.dd.rmi.dbserver;

/**
 *
 * @author Dora Di
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BankImplementation extends UnicastRemoteObject implements BankInterface
{
    // public static String url = "jdbc:h2:mem:Bank";
    public static String url = "jdbc:h2:C:/Users/Highyard/IdeaProjects/SystemIntegration-RMI/soft2020fall-si-master/code/P5-RMI-DB-Server/src/main/resources/db/bank";
    public static String user = "sa";
    public static String password = "";
    public static String driver = "org.h2.Driver";

    BankImplementation()throws RemoteException{}

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name)
    {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/millionaires")
    public List<Customer> getMillionaires()
    {

        List<Customer> list=new ArrayList<Customer>();
        try
        {
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url, user, password);
            PreparedStatement ps=con.prepareStatement("select * from Customer where amount >= 1000000;");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {  
                Customer c=new Customer();
                c.setAccnum(rs.getLong(1));
                c.setName(rs.getString(2));  
                c.setAmount(rs.getDouble(3));
                System.out.println(c);
                list.add(c);  
            }  
            con.close();  
        }
        catch(Exception e)
        {
            System.out.println(e);
        }  
        return list;  
    }

    public int getReport(File jsonInputFile)
    {
        int i = 0;
        try
        {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);

            JSONParser parser = new JSONParser();
            PreparedStatement writePs = null;
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(jsonInputFile));
            for (Object object: jsonArray) {
                JSONObject customer = (JSONObject) object;
                String accnumVal = (String) customer.get("accnum");

                String nameVal = (String) customer.get("name");

                String amountVal = (String) customer.get("amount");


                writePs = con.prepareStatement("INSERT INTO Customer " + "VALUES (?, ?, ?)");
                writePs.setString(1, accnumVal);
                writePs.setString(2, nameVal);
                writePs.setString(3, amountVal);
                writePs.executeUpdate();
            }

            PreparedStatement ps = con.prepareStatement("select * from Customer");

            ResultSet rs=ps.executeQuery();

            while(rs.next())
            {
                i++;
            }


            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return i;
    }
}  



