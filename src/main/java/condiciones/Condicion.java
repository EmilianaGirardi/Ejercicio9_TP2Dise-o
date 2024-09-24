package condiciones;

import lombok.AllArgsConstructor;

public interface Condicion {
    String getCondicion();

    @AllArgsConstructor
    public class CondicionLU implements Condicion{
        private String valorLU;
        @Override
        public String getCondicion(){
            String condicion = "WHERE e.libretaUniversitaria = ".concat('"'+ valorLU + '"');
            return condicion;
        }
    }

    @AllArgsConstructor
    public class CondicionGenero implements Condicion{
        private char genero;
        @Override
        public String getCondicion(){
            String condicion = "WHERE e.genero = ".concat('"'+ String.valueOf(genero) + '"');
            return condicion;
        }
    }

    @AllArgsConstructor
    public class CondicionVacia implements Condicion{
        @Override
        public String getCondicion(){
            return "";
        }
    }

    @AllArgsConstructor
    public class CondicionInsc implements Condicion{
        @Override
        public String getCondicion(){
            return "";
        }
    }

    @AllArgsConstructor
    public class CondicionEstXCarreraYCiudad implements Condicion {
        private int idCarrera;
        private String ciudad;
        @Override
        public String getCondicion(){
            String query = "WHERE e.ciudad =";
            return null;
        }
    }

    @AllArgsConstructor
    public class CondicionCarr implements Condicion{
        @Override
        public String getCondicion(){
            return "";
        }
    }
}


