package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.UsuarioResponseDao;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.UsuarioMapper;
import com.ec.prontiauto.utils.MailerClass;

@Service
public class UsuarioRepositoryImpl extends AbstractRepository<Usuario, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Usuario e WHERE (lower(e.nombres) like lower(:busqueda) or lower(e.apellidos) like lower(:busqueda)"
                + " or lower(e.username) like lower(:busqueda) or lower(e.correo) like lower(:busqueda)"
                + " or lower(e.documentoIdentidad) like lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("tipoDocumentoIdentidad");
        listFilters.add("pais");
        listFilters.add("provincia");
        listFilters.add("ciudad");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<UsuarioResponseDao> listResponseDao = UsuarioMapper.setEntityListToDaoReferenceList
                .apply((List<Usuario>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }

    public List<?> findPermisos(Integer idUsuario) {
        String dbQuery = "SELECT DISTINCT(p.nombre) FROM Usuario u, RolUsuario ru, Rol r, RolPermiso rp, Permiso p where u.id=:idUsuario"
                + " and u.id=ru.idUsuario" + " and ru.idRol=r.id and r.id=rp.idRol"
                + " and rp.idPermiso=p.id";
        List<?> listPermisos = this.customQuery(dbQuery).setParameter("idUsuario", idUsuario)
                .getResultList();
        return listPermisos;
    }

    public Usuario findByUsername(String username) {
        String dbQuery = "SELECT e FROM Usuario e WHERE e.username=:username";
        Usuario usuario = (Usuario) this.customQuery(dbQuery).setParameter("username", username)
                .getSingleResult();
        return usuario;
    }

    public Usuario findByIdUsuario(Integer id) {
        String dbQuery = "SELECT e FROM Usuario e WHERE e.id=:id";
        Usuario usuario = (Usuario) this.customQuery(dbQuery).setParameter("id", id)
                .getSingleResult();
        return usuario;
    }

    public void resetPassword(String correo) {
        try {
            String dbQuery2 = "select u from Usuario u where u.correo=:correo";
            List<Usuario> listUsuario = (List<Usuario>) this.customQuery(dbQuery2).setParameter("correo", correo)
                    .getResultList();
            if (listUsuario.size() > 0) {
                String dbQuery = "UPDATE Usuario e SET e.password=:password WHERE e.correo=:correo";
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String randomPassword = "";
                Random random = new Random();
                for (int i = 0; i < 8; i++) {
                    int value = random.nextInt(10 + 1) + 1;
                    randomPassword += value;
                }
                MailerClass mailer = new MailerClass();
                String password = passwordEncoder.encode(randomPassword);
                this.customQuery(dbQuery).setParameter("password",
                        password).setParameter("correo", correo).executeUpdate();

                mailer.sendMailSimple2(correo, "Reseteo de contraseña", "aqui nose que va",
                        randomPassword, 1000,
                        listUsuario.get(0).getNombres() + " " + listUsuario.get(0).getApellidos(),
                        "prontiauto");

            } else {
                throw new ApiRequestException("El correo no existe");
            }
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public Usuario createUser(Usuario usuario) {
        try {
            this.create(usuario);
            Usuario newUser=null;
            String query = "";
            if (usuario.getUsername() != null) {
                query = "select u from Usuario u where u.username=:username";

            } else {

                query = "select u from Usuario u where u.nombres=:nombres and u.apellidos=:apellidos";
            }

            if (usuario.getUsername() != null) {
                newUser= (Usuario) this.customQuery(query)
                        .setParameter("username", usuario.getUsername() != null ? usuario.getUsername() : "")
                        .getSingleResult();

            } else {

                newUser= (Usuario) this.customQuery(query)
                        .setParameter("nombres", usuario.getNombres())
                        .setParameter("apellidos", usuario.getApellidos())
                        .getResultList().stream().findFirst().orElse(usuario);

            }

            return newUser;
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public Usuario updateUser(Usuario usuario) {
        try {
            this.update(usuario);
            String query = "select u from Usuario u where u.username=:username";
            Usuario newUser = (Usuario) this.customQuery(query).setParameter("username", usuario.getUsername())
                    .getSingleResult();
            return newUser;
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public void updatePassword(String passwordActual, String passwordNuevo, Integer idUsuario) {
        try {
            Usuario usuario = this.findByIdUsuario(idUsuario);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean isMatch = passwordEncoder.matches(passwordActual, usuario.getPassword());
            if (isMatch) {
                String dbQuery = "UPDATE Usuario e SET e.password=:password WHERE e.id=:idUsuario";
                this.customQuery(dbQuery).setParameter("password", passwordEncoder.encode(passwordNuevo))
                        .setParameter("idUsuario", idUsuario).executeUpdate();
            } else {
                throw new ApiRequestException("La contraseña actual no coincide con la registrada");
            }
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
}
