package com.unknown.rfiddemo001;

import com.mot.rfid.api3.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Hashtable;

@Service
public class RFIDBase {

    RFIDReader myReader = null;
    public Hashtable tagStore = null;
    public String hostName = null;
    public int port = 5084;
    public long uniqueTagsCount = 0;
    public long totalTagsCount = 0;
    public AntennaInfo antennaInfo = null;

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
            if (myTags!=null){
                for (int index=0; index<myTags.length; index++){
                    TagData tag = myTags[index];
                    String key = tag.getTagID();
                    String memBank = new String();
                    System.out.println(key);
                }
            }
        }

        @Override
        public void eventStatusNotify(RfidStatusEvents rfidStatusEvents) {

        }
    }
}

