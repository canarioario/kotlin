package me.parzibyte.agenda;

public class Utilerias {
    public static final String TABLA_PERSONA = "contacto";
    public static final String NOMBRE_BD = "sqlite";
    public static final String CAMPO_CORREO = "correo";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CREAR_TABLA_PERSONA = "create table "
            + TABLA_PERSONA
            + " (" + CAMPO_CORREO + " TEXT, "
            + CAMPO_NOMBRE + " TEXT, " + CAMPO_TELEFONO + " TEXT)";
}
