package com.example.tripit.block.dto;

import java.time.LocalDateTime;

import com.example.tripit.user.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class BlockedListDTO {

    private Long blockId;
    private UserDTO userId;
    private String nickname;
    private LocalDateTime blockDate;
    
    //페이징 전용 컬럼
    private LocalDateTime createdDate;
    
    //페이징
    @PrePersist
    protected void onCreate() {
    	createdDate = LocalDateTime.now();
    }
    

}
