package com.unknown.rfiddemo001;

import com.mot.rfid.api3.*;
import com.unknown.rfiddemo001.rfidPOJO.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTML;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class RFIDBase {

    RFIDReader myReader = null;
    public Hashtable tagStore = null;
    public String hostName = null;
    public int port = 5084;
    public long uniqueTagsCount = 0;
    public long totalTagsCount = 0;
    public AntennaInfo antennaInfo = null;
    public Map<String, Short> tagSeenCountMap = new HashMap<>();

    public RFIDReader getMyReader(){
        return myReader;
    }

    public RFIDBase(){
//        Create reader object
        myReader = new RFIDReader();
//        Hashtable to store tag data
        tagStore = new Hashtable();
//        Create antenna info
        antennaInfo = new AntennaInfo();

    }

    public boolean connectToReader(String readerHostname, int readerPort){
        boolean retVal = false;
        hostName = readerHostname;
        port = readerPort;
        myReader.setHostName(hostName);
        myReader.setPort(port);
        try{
            myReader.connect();

            myReader.Events.setInventoryStartEvent(true);
            myReader.Events.setInventoryStopEvent(true);
            myReader.Events.setAccessStartEvent(true);
            myReader.Events.setAccessStopEvent(true);
            myReader.Events.setAntennaEvent(true);
            myReader.Events.setGPIEvent(true);
            myReader.Events.setBufferFullEvent(true);
            myReader.Events.setBufferFullWarningEvent(true);
            myReader.Events.setReaderDisconnectEvent(true);
            myReader.Events.setReaderExceptionEvent(true);
            myReader.Events.setTagReadEvent(true);
            myReader.Events.setAttachTagDataWithReadEvent(false);
            myReader.Events.setTemperatureAlarmEvent(true);
            myReader.Events.addEventsListener(new RFIDEventHandler());
            retVal = true;
        } catch (OperationFailureException e) {
            e.printStackTrace();
        } catch (InvalidUsageException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public void disconnctReader(){
        try{
        myReader.disconnect();
        } catch (OperationFailureException e) {
            e.printStackTrace();
        } catch (InvalidUsageException e) {
            e.printStackTrace();
        }
    }

    public void startRead(){
//        Simple inventory
        try{
            myReader.Actions.purgeTags();
            myReader.Actions.Inventory.perform();
        } catch (OperationFailureException e) {
            e.printStackTrace();
        } catch (InvalidUsageException e) {
            e.printStackTrace();
        }
    }

    public void stopRead(){
        try{
            myReader.Actions.Inventory.stop();
        } catch (OperationFailureException e) {
            e.printStackTrace();
        } catch (InvalidUsageException e) {
            e.printStackTrace();
        }
    }

    public class RFIDEventHandler implements RfidEventsListener{

        @Override
        public void eventReadNotify(RfidReadEvents rfidReadEvents) {
            //Read Tags
            TagData[] myTags = myReader.Actions.getReadTags(100);
//            TagDataArray myTags1 = myReader.Actions.getReadTagsEx(1000);
            if (myTags!=null){
                for (int index=0; index<myTags.length; index++){
                    TagData tag = myTags[index];
                    String tagID = tag.getTagID();
                    String memBank = tag.getMemoryBankData();
                    short antennaID = tag.getAntennaID();
                    short peakRSSI = tag.getPeakRSSI();
                    short tagSeenCount = 0;

                    if (tagSeenCountMap.containsKey(tagID)){
                        tagSeenCountMap.put(tagID, (short) (tagSeenCountMap.get(tagID) + (short)1));
                        tagSeenCount = tagSeenCountMap.get(tagID);
                    }
                    else{
                        tagSeenCountMap.put(tagID, (short)1);
                        tagSeenCount = 1;
                    }

                    Message message = new Message();
                    message.setTagId(tagID);
                    message.setMemBankData(memBank);
                    message.setAntennaId(antennaID);
                    message.setRssi(peakRSSI);
                    message.setTagSeenCount(tagSeenCount);


                    CommService.send(message);
                    System.out.println(tagID);
                    System.out.println("membank");
                    System.out.println(memBank);
                    System.out.println("membank");
                    System.out.println(peakRSSI);
                    System.out.println("membank");
                    System.out.println(tagSeenCount);
                }
            }
        }

        @Override
        public void eventStatusNotify(RfidStatusEvents rfidStatusEvents) {

        }
    }
}

