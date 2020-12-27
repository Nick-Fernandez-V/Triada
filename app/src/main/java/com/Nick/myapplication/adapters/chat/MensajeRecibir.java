package com.Nick.myapplication.adapters.chat;


public class MensajeRecibir extends Mensaje{
    private Long hora;

    public MensajeRecibir(){
    }

    public MensajeRecibir( Long hora ){
        this.hora = hora;
    }

    public MensajeRecibir( String mensaje, String urlFoto, String type_mensaje, Long hora,String usuario){
        super(mensaje, urlFoto, type_mensaje,usuario);
        this.hora = hora;
    }

    public Long getHora(){
        return hora;
    }

    public void setHora( Long hora ){
        this.hora = hora;
    }
}
