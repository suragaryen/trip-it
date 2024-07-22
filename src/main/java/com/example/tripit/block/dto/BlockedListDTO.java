package com.example.tripit.block.dto;

import java.time.LocalDateTime;

import com.example.tripit.user.dto.UserDTO;
import com.example.tripit.user.entity.UserEntity;

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

}
