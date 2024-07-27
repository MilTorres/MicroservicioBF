package com.example.maeriklavanderia.repository;


import com.example.maeriklavanderia.models.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImplementacion implements UsuarioDao  {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario ";
        return entityManager.createQuery(query).getResultList();
    }


    @Override
    public void deleteUsuario(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario); //inserta objeto usuario en la bd mssql
    }


    // Método para obtener un usuario por ID

    @Transactional
    public Usuario obtenerPorId(long id) {
        String query = "FROM Usuario WHERE id = :id";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(query, Usuario.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Transactional
    @Override
    public Usuario obtenerPorRfId(String rfid)
    {
        try {
            String query = "FROM Usuario WHERE rfid = :rfid";
            TypedQuery<Usuario> typedQuery = entityManager.createQuery(query, Usuario.class);
            typedQuery.setParameter("rfid", rfid);
            System.out.println("----------rfid: "+rfid);



            Usuario usuario = typedQuery.getSingleResult();
            System.out.println("----------usuario: "+usuario);

            // Si el usuario es encontrado, actualiza el estado en la tabla Lavadoras
            if (usuario.getRfid().toString() !=  null) {
                String updateQuery = "UPDATE Lavadoras SET estado = 1 WHERE id = 1";

                entityManager.createNativeQuery(updateQuery).executeUpdate();
                System.out.println("Se cambio a activo la lavadora 1 " );
            }
            

            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No existe el usuario");
            // No se encontró ningún resultado, devolver null
            return null;
        } catch (Exception e) {
            System.out.println("No existe el usuario-catch 2");
            // Manejar otras excepciones si es necesario
            e.printStackTrace();
            return null;
        }
    }
    @Transactional
    public void actualizar(Long id, Usuario usuario) {
        // Encuentra el usuario existente
        Usuario usuarioExistente = entityManager.find(Usuario.class, id);

        if (usuarioExistente != null) {
            // Actualiza los campos necesarios
            usuarioExistente.setRfid(usuario.getRfid());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setCorreo(usuario.getCorreo());
            usuarioExistente.setContrasena(usuario.getContrasena());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setSaldo(usuario.getSaldo());
            usuarioExistente.setFecha(usuario.getFecha());

            // La entidad se actualiza automáticamente gracias a la persistencia de JPA
        } else {
            throw new RuntimeException("Usuario no encontrado con el id: " + id);
        }
    }


}
