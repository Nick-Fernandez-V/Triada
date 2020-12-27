package com.Nick.myapplication.adapters.chat;

import java.util.Map;

public class MensajeEnviar extends Mensaje {
    private Map hora;

    public MensajeEnviar(){
    }

    public MensajeEnviar( Map hora ){
        this.hora = hora;
    }

    public MensajeEnviar( String mensaje, String urlFoto, String type_mensaje, Map hora ,String usuario ){
        super(mensaje, urlFoto, type_mensaje, usuario);
        this.hora = hora;
    }

    public Map getHora(){
        return hora;
    }

    public void setHora( Map hora ){
        this.hora = hora;
    }
}
