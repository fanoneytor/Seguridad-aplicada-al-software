package trabajo1;

import java.util.ArrayList;

public class Usuario {
    private String clave;
    private String llavePublica;
    ArrayList<String> mensajes;
    
    public Usuario(String clave, String llavePublica){
        this.clave = clave;
        this.llavePublica = llavePublica;
        mensajes = new ArrayList<String>();
    }
    public ArrayList<String> getLista(){
        return mensajes;
    }
    @Override
    public String toString(){
        return clave + " " + llavePublica + " " + mensajes;
    }
}
