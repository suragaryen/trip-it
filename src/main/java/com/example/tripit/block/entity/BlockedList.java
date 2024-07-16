package com.example.tripit.block.entity;

import java.time.LocalDateTime;

import com.example.tripit.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blockedlist")
public class BlockedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Long blockId;

	@Column(name = "user_id")
	private Long userId; 

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "block_date")
    private LocalDateTime blockDate = LocalDateTime.now();



    
}
