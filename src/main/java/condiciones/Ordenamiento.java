package condiciones;

import lombok.AllArgsConstructor;

public interface Ordenamiento {
    String getOrdenamiento();

    @AllArgsConstructor
    public class OrdenamientoSimple implements Ordenamiento{
        private String atributo;
        @Override
        public String getOrdenamiento(){
            String ordenamiento = "ORDER BY ".concat(atributo);
            return ordenamiento;
        }
    }

    @AllArgsConstructor
    public class OrdenamientoVacio implements Ordenamiento{
        @Override
        public String getOrdenamiento(){
            return "";
        }
    }

    public class OrdenamientoCantInscriptos implements Ordenamiento{
        @Override
        public String getOrdenamiento() {
            return "GROUP BY id_carrera" +
                    "ORDER BY COUNT(*) DESC;";
        }
    }
}
