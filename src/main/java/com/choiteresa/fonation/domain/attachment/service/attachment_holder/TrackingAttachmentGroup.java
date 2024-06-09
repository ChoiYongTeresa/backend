package com.choiteresa.fonation.domain.attachment.service.attachment_holder;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrackingAttachmentGroup {
    private List<TrackingAttachment> trackingAttachmentList;
    private LocalDateTime groupExpireDate;
    private String groupId;

    public TrackingAttachmentGroup(String givenGroupId) {
        this.groupId = givenGroupId;
        this.trackingAttachmentList = new ArrayList<>();
    }


    public void addToGroup(TrackingAttachment attachment) {
        // TrackingAttachment를 그룹에 등록
        attachment.setGroupId(this.getGroupId());

        // TrackingAttachmentGroup에 첨부파일을 추가
        trackingAttachmentList.add(attachment);
    }

    public void setGroupExpireDate(LocalDateTime givenExpireDate) {
        this.groupExpireDate = givenExpireDate;
    }

    public TrackingAttachment remove(Long trackingId) {
        // Tracking Id에 해당하는 첨부파일 하나를 삭제
        return trackingAttachmentList.stream()
                .filter(attachment -> attachment.getTrackingId() == trackingId)
                .findFirst().orElseThrow(()->new RuntimeException("tracking id not found: "+trackingId));
    }
}
