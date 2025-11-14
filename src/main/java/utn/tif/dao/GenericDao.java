package utn.tif.dao;

import java.sql.Connection;
import java.util.List;

public interface GenericDao<T> {
    T crear(T entity, Connection conn) throws Exception;
    T leer(Long id, Connection conn) throws Exception;
    List<T> leerTodos(Connection conn) throws Exception;
    T actualizar(T entity, Connection conn) throws Exception;
    void eliminar(Long id, Connection conn) throws Exception;
}