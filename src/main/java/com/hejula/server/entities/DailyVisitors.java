package com.hejula.server.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "daily_visitors")
public class DailyVisitors {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long daily_visitors_seq;

    @ManyToOne(targetEntity = Accommodation.class)
    @JoinColumn(name = "accommodation_seq")
    @JsonIgnore
    private Accommodation accommodation;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date day;

    @Column(nullable = false)
    private int dayOfWeek;

    @Column(nullable = false)
    private int visitors;

}
