package com.example.starlog.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="fortunes", uniqueConstraints=@UniqueConstraint(columnNames={"user_id","fortune_date"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fortune {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "fortune_date", nullable = false)
    private LocalDate fortuneDate;

    @Lob
    @Column(nullable = false)
    private String content;

    public Fortune(User user, LocalDate fortuneDate, String content) {
        this.user = user;
        this.fortuneDate = fortuneDate;
        this.content = content;
    }
}
