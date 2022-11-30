package com.nhnacademy.board.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "board_likes")
@Getter
@Setter
@NoArgsConstructor
public class BoardLike {

    @EmbeddedId
    private Pk pk;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "board_id")
        private Integer boardId;
    }
}
