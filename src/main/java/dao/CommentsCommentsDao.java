package dao;

import javax.xml.stream.events.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentsDao implements CommentsDaoInterface {

    private Connection connection;
    public CommentsDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Comment> readComments() throws SQLException {
        List<Comment> commentsList = new ArrayList<Comment>();
        String query = "SELECT * FROM info ORDER BY id;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Comment comment = new Comment();
            comment.setId(resultSet.getInt("id"));
            comment.setNickname(resultSet.getString("user_nick_name"));
            comment.setDescription(resultSet.getString("user_description"));
            comment.setDate(resultSet.getString("post_day"));
            commentsList.add(comment);
        }
        return commentsList;
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
