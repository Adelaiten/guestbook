package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CommentsDao implements CommentsDaoInterface {

    private Connection connection;
    public CommentsDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Comment> readComments() throws SQLException {
        return null;
    }

    @Override
    public void addComment(String userNickName, String userDescription, String postDay) throws SQLException{

    }

    @Override
    public void updateComment(String id, String description) throws SQLException{

    }

    @Override
    public void deleteComment(String id) throws SQLException{

    }
}
