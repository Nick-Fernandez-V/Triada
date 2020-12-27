package com.Nick.myapplication.adapters.amigos;

public class InfoDeAmigo {

    private String
            nombre,edad,genero,descripcion,pais,estado,urlFoto;

    public InfoDeAmigo(String nombre,String edad,String genero,String descripcion,String pais,String estado,String urlFoto){
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.descripcion = descripcion;
        this.pais = pais;
        this.estado = estado;
        this.urlFoto = urlFoto;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getEdad(){
        return edad;
    }

    public void setEdad(String edad){
        this.edad = edad;
    }

    public String getGenero(){
        return genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getPais(){
        return pais;
    }

    public void setPais(String pais){
        this.pais = pais;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public String getUrlFoto(){
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto){
        this.urlFoto = urlFoto;
    }
}
