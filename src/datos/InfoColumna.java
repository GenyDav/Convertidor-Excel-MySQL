package datos;

/**
 * Clase encargada de crear objetos con la información necesaria para definir 
 * una columna de una tabla MySQL.
 * @author Geny
 * @version 1.0
 */
public class InfoColumna{
    private String nombre;
    private int tipo;
    private String parametros;
    private boolean primaryKey; 
    private boolean notNull; 
    private boolean unique; 
    private boolean unsigned;
    private boolean autoIncrement;
    
    /**
     * Constructor que crea un nuevo objeto que representa una columna de 
     * una hoja en un archivo Excel, pero con las características propias de una
     * columna de una tabla MySQL.
     * @param nombre nombre de la columna
     * @param tipo tipo de dato de la columna
     */
    public InfoColumna(String nombre, int tipo){
        this.nombre = nombre;
        if(tipo==3)
            parametros = "";
        if(tipo==9)
            parametros = "45";
        primaryKey = false;
        notNull = false;
        unique = false;
        unsigned = false;
        autoIncrement = false;
        this.tipo = tipo;
    }

    /**
     * Método que permite obtener el nombre de la columna.
     * @return nombre de la columna
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Método que permite obtener el tipo de dato de la columna.
     * @return número entero correspondiente al tipo de dato de la columna.
     */
    public int getTipo() {
        return tipo;
    }
    
    /**
     * Método que permite obtener el nombre del tipo de dato de la columna. 
     * @return Cadena de caracteres con el nombre del tipo de dato.
     */
    public String getNombreTipo(){
        return Tipo.NOMBRES[tipo];
    }
    
    /**
     * Método que permite obtener los parámetros del tipo de dato de la columna.
     * @return parámetros de la columna
     */
    public String getParametros() {
        return parametros;
    }

    /**
     * Método que permite saber si la columna está marcada como llave primaria.
     * @return booleano que indica si la columna está marcada como llave primaria
     */
    public boolean getPK() {
        return primaryKey;
    }

    /**
     * Método que permite saber si los campos de la columna no pueden estar vacíos.
     * @return booleando que indica si la columna está marcada como NOT NULL
     */
    public boolean getNN() {
        return notNull;
    }

    /**
     * Método que permite saber si los valores de la columna deben ser únicos.
     * @return booleando que indica si la columna está marcada como UNIQUE
     */
    public boolean getUQ() {
        return unique;
    }

    /**
     * Método que permite saber si la columna solo admite valores númericos no
     * negativos.
     * @return booleano que indica si la columna está marcada como UNSIGNED
     */
    public boolean getUN() {
        return unsigned;
    }

    /**
     * Método que permite saber si el valor numérico de los campos de una columna 
     * irán aumentando en uno de forma automática.
     * @return booleano que indica si la columna está marcada como AUTO_INCREMENT
     */
    public boolean getAI() {
        return autoIncrement;
    }
    
    /**
     * Método que permite modificar el tipo de dato de la columna.
     * @param tipo número entero que hace referencia al tipo de dato de la columna
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Método que permite modificar los parámetros del tipo de dato de la columna.
     * @param parametros parámetros de la columna
     */
    public void setParametros(String parametros) {
        this.parametros = parametros;
    }
    
    /**
     * Método para indicar si la columna forma parte de la llave primaria.
     * @param pk booleano que indica si el campo debe ser marcado como llave primaria
     */
    public void setPK(boolean pk) {
        this.primaryKey = pk;
    }
    
    /**
     * Método para indicar si es obligatorio que la columna contenga datos (no
     * puede estar vacía).
     * @param nn booleano que indica si el campo debe ser marcado como no nulo
     */
    public void setNN(boolean nn) {
        this.notNull = nn;
    }
    
    /**
     * Método que permite indicar si los valores de una columna deben ser únicos.
     * @param uq booleando que indica si el campo debe ser marcado como unique.
     */
    public void setUQ(boolean uq) {
        this.unique = uq;
    }
    
    /**
     * Método que permite indicar si la columna solo permite insertar valores
     * númericos no negativos.
     * @param un booleano que indica si el campo debe ser marcado como unsigned
     */
    public void setUN(boolean un) {
        this.unsigned = un;
    }
    
    /**
     * Método que permite indicar si la columna permite que sus valores
     * incrementen en uno automáticamente al insertar nuevos registros.
     * @param ai booleano que indica si el campo debe ser marcado como auto_increment
     */
    public void setAI(boolean ai) {
        this.autoIncrement = ai;
    }
}
