package com.northeast.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sessionId;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
    private String userId;
}
