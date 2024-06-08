package com.choiteresa.fonation.domain.attachment.service.attachment_holder;


import com.choiteresa.fonation.domain.attachment.entity.Attachment;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AttachmentTracker {

    // 기부 신청 form ID당, TrackingAttachment Mapper를 가지고 있음
    // TrackingAttachment Id는 업로드 시 발급되며, 프론트에서 컴포넌트가 해당 아이디를 가지고 잇을 수 있도록 함
    private HashMap<String,TrackingAttachmentGroup> attachmentGroupTracker;
    private TrackingAttachmentLifeCycle trackingAttachmentLifeCycle;

    public AttachmentTracker(){
        attachmentGroupTracker = new HashMap<>();
        trackingAttachmentLifeCycle = new TrackingAttachmentLifeCycle();
    }

    public String addTrackingAttachment(Attachment attachment, String groupId){
        // Attachment 를 TrackingAttachment 로 생성하여 그룹에 추가
        TrackingAttachment trackingAttachment = new TrackingAttachment(attachment);

        // group id를 통해서 그룹을 불러오고, 그룹에 TrackingAttachment를 등록
        TrackingAttachmentGroup trackingAttachmentGroup = findGroupById(groupId);
        trackingAttachmentGroup.addToGroup(trackingAttachment);

        // 라이프 사이클 갱신
        trackingAttachmentLifeCycle.updateLifeCycle(trackingAttachmentGroup);

        // 추가한 그룹의 ID 반환
        return trackingAttachmentGroup.getGroupId();
    }

    private TrackingAttachmentGroup createGroup(String groupId){
        // TrackingAttachmentGroup 생성
        TrackingAttachmentGroup trackingAttachmentGroup = new TrackingAttachmentGroup(groupId);
        attachmentGroupTracker.put(trackingAttachmentGroup.getGroupId(), trackingAttachmentGroup);

        return trackingAttachmentGroup;
    }

    public TrackingAttachmentGroup findGroupById(String groupId){
        // Group Id로 TrackingAttachmentGroup 을 반환, 없다면 새로 생성하여 반환
        return attachmentGroupTracker.getOrDefault(groupId, createGroup(groupId));
    }


    public List<Attachment> unTrackingExpiredAttachment(){
        // 모든 tracking file 을 삭제
        List<TrackingAttachmentGroup> removedGroups =
                trackingAttachmentLifeCycle.removeExpiredGroup();
        List<Attachment> removedAttachmentList = new ArrayList<>();

        // 각 그룹을 Tracker에서 삭제
        for (TrackingAttachmentGroup group : removedGroups) {
            // Attachment를 추출하기
            List<Attachment> attachments =
                    group.getTrackingAttachmentList().stream()
                            .map(TrackingAttachment::getAttachment)
                            .toList();

            // 그룹에 속한 모든 Attachment를 removed 리스트에 추가
            removedAttachmentList.addAll(attachments);

            // 그룹트래커에서 해당 그룹의 Id를 삭제
            attachmentGroupTracker.remove(group.getGroupId());
        }

        return removedAttachmentList;
    }

    public void remove(Long groupId){
        // formId에 해당하는 모든 첨부파일을 삭제
        attachmentGroupTracker.remove(groupId);
    }

    public TrackingAttachment removeOne(Long groupId, Long trackingId){
        // formId에 해당하는 첨부 파일 중, trackingId와 일치하는 항목을 삭제
        TrackingAttachmentGroup trackingAttachmentMapper = attachmentGroupTracker.get(groupId);
        return trackingAttachmentMapper.remove(trackingId);
    }
}

