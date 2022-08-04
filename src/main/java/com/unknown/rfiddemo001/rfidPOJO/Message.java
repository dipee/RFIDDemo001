package com.unknown.rfiddemo001.rfidPOJO;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private String tagId;
    private String memBankData;
    private Short antennaId;
    private Short Rssi;
    private Short TagSeenCount;
}