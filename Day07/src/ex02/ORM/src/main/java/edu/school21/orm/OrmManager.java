package edu.school21.orm;


import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Set;

import edu.school21.repositories.Repository;
import org.reflections.Reflections;
import edu.school21.orm.annotation.*;

public class OrmManager implements Repository {
    private final DataSource dataSource;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private String getTableName(Class<?> clazz) throws RuntimeException {
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            throw new RuntimeException("Entity has no annotation");
        } else {
            OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
            return ormEntity.table();
        }
    }

    public void createTable() {
        Reflections reflections = new Reflections("edu.school21.models");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(OrmEntity.class);
        for (Class clazz : classes) {
            Class<?> currentClass = clazz;
            OrmEntity ormEntity = currentClass.getAnnotation(OrmEntity.class);
            String tableName = ormEntity.table();
            String SQLQuery = String.format("DROP TABLE IF EXISTS %s CASCADE;", tableName);
            try (Connection connection = dataSource.getConnection();
                 Statement st = connection.createStatement()) {
                st.execute(SQLQuery);
                Field[] fields = currentClass.getDeclaredFields();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("CREATE TABLE IF NOT EXISTS %s (", tableName));
                for (Field field : fields) {
                    if (field.isAnnotationPresent(OrmColumnId.class)) {
                        stringBuilder.append(String.format(
                                "%s BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY", field.getName()));
                    }
                    if (field.isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                        stringBuilder.append(ormColumn.name());
                        if (field.getType().getSimpleName().equals("String")) {
                            stringBuilder.append(String.format(" VARCHAR(%d) NOT NULL", ormColumn.length()));
                        }
                        if (field.getType().getSimpleName().equals("Integer")) {
                            stringBuilder.append(" INTEGER NOT NULL");
                        }
                        if (field.getType().getSimpleName().equals("Double")) {
                            stringBuilder.append(" DOUBLE PRECISION NOT NULL");
                        }
                        if (field.getType().getSimpleName().equals("Long")) {
                            stringBuilder.append(" BIGINT NOT NULL");
                        }
                        if (field.getType().getSimpleName().equals("Boolean")) {
                            stringBuilder.append(" BOOLEAN");
                        }
                    }
                    stringBuilder.append(", ");
                }
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                stringBuilder.append(");");
                System.out.println(stringBuilder);
                st.execute(stringBuilder.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void save(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);
        StringBuilder stringBuilder = new StringBuilder(String.format("INSERT INTO %s (", tableName));
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                OrmColumnId columnId = field.getAnnotation(OrmColumnId.class);
                stringBuilder.append(columnId.name());
                stringBuilder.append(", ");
            }
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                stringBuilder.append(column.name());
                stringBuilder.append(", ");
            }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(") VALUES (");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object object = field.get(entity);
                if (object == null) {
                    stringBuilder.append("default");
                } else {
                    if (field.getType().getSimpleName().equals("String")) {
                        stringBuilder.append(String.format("'%s'", object));
                    } else {
                        stringBuilder.append(String.format("%s", object));
                    }
                }
                stringBuilder.append(", ");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(");");
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            System.out.println(stringBuilder);
            st.execute(stringBuilder.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Object entity) {
        Class<?> clazz = entity.getClass();
        String tableName = getTableName(clazz);
        StringBuilder stringBuilder = new StringBuilder(String.format("UPDATE %s SET ", tableName));
        Field[] fields = clazz.getDeclaredFields();
        Object idValue = null;
        String idName = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                OrmColumnId columnId = field.getAnnotation(OrmColumnId.class);
                field.setAccessible(true);
                idName = columnId.name();
                try {
                    idValue = field.get(entity);
                    if (idValue == null) {
                        System.err.println("Entity id not specified");
                        return;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                field.setAccessible(true);
                try {
                    Object object = field.get(entity);
                    if (object == null) {
                        System.err.println("Entity id not specified");
                        return;
                    } else {
                        if (field.getType().getSimpleName().equals("String")) {
                            stringBuilder.append(String.format("%s = '%s', ", column.name(), object));
                        } else {
                            stringBuilder.append(String.format("%s = %s, ", column.name(), object));
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(String.format(" WHERE %s = %s;", idName, idValue));
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            System.out.println(stringBuilder);
            st.executeUpdate(stringBuilder.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T findById(Long id, Class<T> clazz) {
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        String tableName = getTableName(clazz);
        StringBuilder stringBuilder = new StringBuilder(String.format("SELECT * FROM %s WHERE id = %d;", tableName, id));
        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement()) {
            System.out.println(stringBuilder);
            ResultSet rs = st.executeQuery(stringBuilder.toString());
            if (!rs.next()) {
                System.err.println("Entity not found");
                return object;
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
                    field.set(object, rs.getLong(ormColumnId.name()));
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    field.set(object, rs.getObject(ormColumn.name()));
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return object;
    }


}
