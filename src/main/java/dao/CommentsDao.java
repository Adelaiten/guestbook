package dao;


import models.Comment;

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
        String query = "INSERT INTO info (user_nick_name, user_description, post_day) VALUES(?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userNickName);
        preparedStatement.setString(2, userDescription);
        preparedStatement.setString(3, postDay);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateComment(int id, String description) throws SQLException{
        String query = "UPDATE info SET description = ? WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, description);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteComment(int id) throws SQLException{
        String query = "DELETE * FROM info WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
