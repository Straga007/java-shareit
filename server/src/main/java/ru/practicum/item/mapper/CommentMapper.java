package ru.practicum.item.mapper;


import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.object.Comment;
import ru.practicum.user.object.User;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                ItemMapper.toItemDto(comment.getItem()),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }

    public static Comment toComment(CommentDto commentDto, User author) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                ItemMapper.toItem(commentDto.getItemDto()),
                author,
                commentDto.getCreated()
        );
    }
}
