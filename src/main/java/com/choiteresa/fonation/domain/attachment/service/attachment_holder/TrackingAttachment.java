package com.choiteresa.fonation.domain.attachment.service.attachment_holder;


import com.choiteresa.fonation.domain.attachment.entity.Attachment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingAttachment {
    private static int SEQUENCE = 0;
    private long trackingId; // Tracking Id
    private String groupId;
    private Attachment attachment; // Tracking File;

    public TrackingAttachment(Attachment trackingAttachment){
        this.attachment = trackingAttachment;
        this.trackingId = generateId();
    }
    public static long generateId(){
        return (SEQUENCE++ % Long.MAX_VALUE);
    }

    public void setGroupId(String groupId){
        this.groupId = groupId;
    }
}
