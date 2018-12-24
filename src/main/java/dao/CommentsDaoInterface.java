package dao;

import java.sql.SQLException;
import java.util.List;

public interface CommentsDaoInterface {
    List<Comment> readComments() throws SQLException;
    void addComment(String userNickName, String userDescription, String postDay) throws SQLException;
    void updateComment(String id, String description) throws SQLException;
    void deleteComment(String id) throws SQLException;
}
