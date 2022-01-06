package test;

import java.io.IOException;
import java.util.ArrayList;
import other.Person;
import util.Base64;
import util.Util;

public class Base64Test03 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Person> nombresLista = new ArrayList<>();
        
        Person Marcos = new Person("Marcos", 29, 1.78);
        Person Adrian = new Person("Adrian", 39, 1.78);
        Person Daniel = new Person("Daniel", 49, 1.78);
        Person David = new Person("David", 59, 1.78);

        nombresLista.add(Marcos);
        nombresLista.add(Adrian);
        nombresLista.add(Daniel);
        nombresLista.add(David);

        System.out.println(nombresLista);

        byte[] nombresBA = Util.objectToByteArray(nombresLista);
        String nombresB64 = Base64.encode(nombresBA);
        System.out.println(nombresB64);

        byte[] nombresBA2 = Base64.decode(nombresB64);
        ArrayList<String> nombres2 = (ArrayList<String>) Util.byteArrayToObject(nombresBA2);
        System.out.println(nombres2);            
    }
    
}
