package rmiserver;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIRegistry
{
    public static Registry registry;

    public static void main(String[] args)
    {
        try
        {
            System.out.println("RMI registry");

            registry = LocateRegistry.getRegistry("localhost", 1099);
            String[] boundNames = registry.list();
            System.out.println("Names bound to RMI registry at localhost:");
            for (String name : boundNames)
            {
                System.out.println("\t" + name);
            }
        }
        catch (Exception e)
        {
            System.err.println("Exception:" + e);
        }
    }
}

