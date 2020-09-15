package dk.dd.rmi.dbclient;
/**
 *
 * @author Dora Di
 */
import java.io.File;
import java.rmi.Naming;
import java.util.List;
import dk.dd.rmi.dbserver.*;

public class RMIClientDB 
{
    public static void main(String args[])throws Exception
    {
            String path = "C:/Users/Highyard/IdeaProjects/SystemIntegration-RMI/soft2020fall-si-master/code/P5-RMI-DB-Server/src/main/resources/data.json";

            // name =  rmi:// + ServerIP +  /EngineName;
            String remoteEngine = "rmi://localhost/BankServices";
            
            // Create local stub, lookup in the registry searching for the remote engine - the interface with the methods we want to use remotely
            BankInterface obj = (BankInterface) Naming.lookup(remoteEngine);

        int size = obj.getReport(new File(path));
        System.out.println("DB amount of records: " + size);
    }
  
} 
