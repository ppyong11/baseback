package jhcode.blog.board.dto.response;

import jhcode.blog.board.Board;
import jhcode.blog.comment.dto.response.ResCommentDto;
import jhcode.blog.file.dto.response.ResBoardDetailsFileDto;
import jhcode.blog.file.dto.response.ResFileUploadDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * -Response-
 * 게시글 상세, 수정 요청에 대한 정보를 반환
 */

@Getter
@Setter
@NoArgsConstructor
public class ResBoardDetailsDto {

    // board info
    private Long boardId;
    private String title;
    private String content;
    private String category;
    private String writerName;
    private String createdDate;
    private String modifiedDate;

    // comments
    private List<ResCommentDto> comments;

    // file
    private List<ResBoardDetailsFileDto> files;

    @Builder
    public ResBoardDetailsDto(Long boardId, String title, String content, String category, String writerName, String createdDate, String modifiedDate, List<ResCommentDto> comments, List<ResBoardDetailsFileDto> files) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.writerName = writerName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
        this.files = files;
    }

    public static ResBoardDetailsDto fromEntity(Board board) {
        return ResBoardDetailsDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .writerName(board.getMember().getUsername())
                .createdDate(board.getCreateDate().toString())
                .modifiedDate(board.getModifiedDate().toString())
                .comments(board.getComments().stream()
                        .map(ResCommentDto::fromEntity)
                        .collect(Collectors.toList()))
                .files(board.getFiles().stream()
                        .map(ResBoardDetailsFileDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}