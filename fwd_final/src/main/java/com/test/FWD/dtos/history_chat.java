package com.test.FWD.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class history_chat {
    private int id;
    private int receiver_id;
    private int sender_id;
    private String content;
    private String time;


}
