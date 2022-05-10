package datos;

/**
 * Clase que permite obtener los nombres de los tipos de datos disponibles
 * para ser usados en el programa.
 * @author Geny
 * @version 1.0
 */
public class Tipo {
    public static String[] NOMBRES = {
        "TINYINT","SMALLINT","MEDIUMINT","INT","BIGINT",
        "FLOAT","DOUBLE","DECIMAL",
        "CHAR","VARCHAR",
        "BINARY","VARBINARY",
        //"TINYBLOB","MEDIUMBLOB","BLOB","LONGBLOB",
        //"TINYTEXT",
        "TEXT",
        //"MEDIUMTEXT","LONGTEXT",
        "SET","ENUM",
        "DATE","DATETIME","TIME","TIMESTAMP","YEAR"
    }; 
    
    public static final int TINYINT = 0;
    public static final int SMALLINT = 1;
    public static final int MEDIUMINT = 2;
    public static final int INT = 3;
    public static final int BIGINT = 4;
    public static final int FLOAT = 5;
    public static final int DOUBLE = 6;
    public static final int DECIMAL = 7;
    public static final int CHAR = 8;
    public static final int VARCHAR = 9;
    public static final int BINARY = 10;
    public static final int VARBINARY = 11;
    public static final int TEXT = 12;
    public static final int SET = 13;
    public static final int ENUM = 14;
    public static final int DATE = 15;
    public static final int DATETIME = 16;
    public static final int TIME = 17;
    public static final int TIMESTAMP = 18;
    public static final int YEAR = 19;         
}
