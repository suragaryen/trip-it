package com.example.tripit.block.dto;

import java.time.LocalDateTime;

public class BlockedListDTO {

    private Long blockId;
    private String userNickname;
    private String blockedUserNickname;
    private LocalDateTime blockDate;

    public BlockedListDTO(Long blockId, String userNickname, String blockedUserNickname, LocalDateTime blockDate) {
        this.blockId = blockId;
        this.userNickname = userNickname;
        this.blockedUserNickname = blockedUserNickname;
        this.blockDate = blockDate;
    }

    // Getters and Setters
}
