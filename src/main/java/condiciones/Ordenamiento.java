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
        /* En IncripcionRepository:
        public List<Carrera> getCarreras(Condicion c, Ordenamiento o){
        String query = "SELECT i.id_carrera FROM Inscripcion i".concat(c.getCondicion()).concat(ord.getOrdenamiento());
            TypedQuery<Inscripcion> q = em.createQuery(query, Estudiante.class);
            List<String> result = q.getResultList(); //lista de id_carrera
            List<Carrera> carreras = new List<Carrera>();
            for each (id in result){
                careras.add(CarreraRepository.getById(id);
            }
            return carreras;
        }
         */
    }
}
