package org.example.Utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class StubContext {
    private static final Map<String, Remote> stubs = new HashMap<>();;
    private static Registry registry;


    public static void addStub(String name, Remote stub) {
//        if(registry ==null){
//            try {
//                registry = LocateRegistry.createRegistry(1099);
//
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
//        }
        try {
            Naming.rebind(name, stub);
        } catch (RemoteException | MalformedURLException e) {
            throw new RuntimeException(e);
        }

        stubs.put(name, stub);
    }

    public static Remote getStub(String name) {
        try {
            Remote stub = Naming.lookup(name);

            if (stub != null) {
                stubs.put(name, stub);
            }

            return stub;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void unbindAll() {
        try {
            for (String name : stubs.keySet()) {
                // Unbind the RMI object
                Naming.unbind(name);
            }

            // Clear the stubs map
            stubs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}