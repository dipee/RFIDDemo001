package com.unknown.rfiddemo001;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RfidWSMessage {
private String tagID;
private String memoryBankData;
private Short antennaID;
private Short peakRSSI;
private Short tagSeenCount;
}
