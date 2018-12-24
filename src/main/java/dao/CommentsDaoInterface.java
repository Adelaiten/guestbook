package dao;

import models.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentsDaoInterface {
    List<Comment> readComments() throws SQLException;
    void addComment(String userNickName, String userDescription, String postDay) throws SQLException;
    void updateComment(int id, String description) throws SQLException;
    void deleteComment(int id) throws SQLException;
}
