package com.Nick.myapplication.adapters.chat;

public class Mensaje {

    private String mensaje;
    private  String urlFoto;
    private String type_mensaje;
    private  String usuario;
    public Mensaje() {

    }

    public Mensaje( String mensaje, String urlFoto,String type_mensaje,String usuario) {
        this.mensaje = mensaje;
        this.urlFoto = urlFoto;
        this.type_mensaje = type_mensaje;
        this.usuario = usuario;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getUrlFoto(){
        return urlFoto;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setUsuario( String usuario ){
        this.usuario = usuario;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setUrlFoto( String urlFoto ){
        this.urlFoto = urlFoto;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }
}
