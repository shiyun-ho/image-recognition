package server.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class FileUploadRepository {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate template;

    //INSERT FILE INTO SQL TABLE
    //prepared statement object takes in the string
    private static final String INSERT_POST_SQL = "INSERT INTO posts(blobc, title, complain) VALUES (?,?,?)";

    public void upload(MultipartFile file, String title, String complain) throws SQLException, IOException {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_POST_SQL)){
            InputStream inputStream = file.getInputStream();

            //set binary stream by supplying the values in place of the question mark placeholders.
                //in this case, parameterIndex (first value), inputStream (blob in bytes), and the size of file
            preparedStatement.setBinaryStream(1, inputStream, file.getSize());
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, complain);

            //execute() is for any, executeQuery() is for GET, this is for INSERT, UPDATE, and DELETE
            preparedStatement.executeUpdate();

        }
    }






}
