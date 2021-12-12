package com.kein.ktech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany(mappedBy = "fatherComment")
    @JsonIgnore
    private List<Comment> listCommentsReply;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment fatherComment;
    @Column(name = "create_date")
    private Date createdDate;

    @Transient
    private long userId;
    @Transient
    private long productId;

}
