package practica.servicios;

import practica.entidades.Estudiante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;


/**
 * Clase para encapsular los metodos de CRUD
 * Created by vacax on 30/04/16.
 */
public class EstudianteServices extends GestionDB<Estudiante> {

    private static EstudianteServices instancia;

    private EstudianteServices() {
        super(Estudiante.class);
    }

    public static EstudianteServices getInstancia(){
        if(instancia==null){
            instancia = new EstudianteServices();
        }
        return instancia;
    }

    /**
     *
     * @param nombre
     * @return
     */
    public List<Estudiante> findAllByNombre(String nombre){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Estudiante e where e.nombre like :nombre");
        query.setParameter("nombre", nombre+"%");
        List<Estudiante> lista = query.getResultList();
        return lista;
    }

    public List<Estudiante> consultaNativa(){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from estudiante ", Estudiante.class);
        //query.setParameter("nombre", apellido+"%");
        List<Estudiante> lista = query.getResultList();
        return lista;
    }

}