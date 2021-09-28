package com.hejula.server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity
@Table(name = "file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long fileSeq;

    @JsonBackReference(value = "accAndFileReference") //serialize에 제외됨
    @ManyToOne(targetEntity = Accommodation.class)
    @JoinColumn(name = "accommodation_seq")
    private Accommodation accommodation;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_seq")
    private Customer customer;

    @ManyToOne(targetEntity = Admin.class)
    @JoinColumn(name = "admin_seq")
    private Admin admin;

    @JsonBackReference(value = "reviewAndFileReference")
    @ManyToOne(targetEntity = Review.class)
    @JoinColumn(name = "review_seq")
    private Review review;


    @Column(nullable = false)
    private String fileNm;

    @Column(nullable = false)
    private String originalFileNm;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Date registDate;

}
