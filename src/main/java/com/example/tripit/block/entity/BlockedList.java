package com.example.tripit.block.entity;

import java.time.LocalDate;

import com.example.tripit.user.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "blockedlist")
public class BlockedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private int blockId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity userId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "block_date")
    private String blockDate;

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(String formattedDate) {
		this.blockDate = formattedDate;
	}

    
}
