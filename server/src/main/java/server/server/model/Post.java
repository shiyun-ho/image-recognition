package server.server.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Post implements Serializable {
    private String complain;
    private String title;
    private byte[] image;
    private Integer postId;

    //getters and setters
    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    //method to set the data properties of Model object Post from sql rowset
    public static Post populate(ResultSet rs) throws SQLException {
        final Post p = new Post();
        p.setPostId(rs.getInt("id"));
        p.setComplain(rs.getString("complain"));
        p.setTitle(rs.getString("title"));
        p.setImage(rs.getBytes("blobc"));

        return p;
    }
}
