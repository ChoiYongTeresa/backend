package com.choiteresa.fonation.domain.attachment.service.attachment_holder;

import lombok.Getter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;

public class TrackingAttachmentLifeCycle {

    private HashMap<String, List<TrackingAttachmentGroup>> attachmentGroupLifeCycleMapper;

    public TrackingAttachmentLifeCycle() {
        this.attachmentGroupLifeCycleMapper = new HashMap<>();
    }

    public void updateLifeCycle(TrackingAttachmentGroup trackingAttachmentGroup) {
        /*
            라이프 사이클에 대해서 그룹의 만료시간을 업데이트
            이전 만료시간에 속하는 그룹을 삭제 후, 새로운 만료시간에 대해서 그룹을 추가
        */

        // 라이프 사이클에서 그룹을 삭제 (이전 만료시간에 대해 그룹을 삭제하기 위함)
        detachFromLifeCycle(trackingAttachmentGroup);

        // 그룹의 만료 시간을 갱신
        trackingAttachmentGroup.setGroupExpireDate(getExpireDate());

        // 라이프 사이클에 등록
        addToLifeCycle(trackingAttachmentGroup);
    }

    public List<TrackingAttachmentGroup> removeExpiredGroup() {
        // 라이프 사이클에서 만료된 그룹을 제거하고, 해당 그룹을 반환

        List<TrackingAttachmentGroup> removedGroups = new ArrayList<>();
        Set<String> codeSet = attachmentGroupLifeCycleMapper.keySet();

        // 현재 홀딩중인 만료 시간을 순회
        for(String expireCode : codeSet){
            LocalDateTime groupExpireDate = convertCodeToLocalDateTime(expireCode);

            // 현재 만료시간이 경과했다면 라이프 사이클에서 제거
            if(isExpired(groupExpireDate)){
                List<TrackingAttachmentGroup> expiredGroups
                        = attachmentGroupLifeCycleMapper.remove(expireCode);

                // 삭제된 그룹에 만료된 그룹들을 추가
                removedGroups.addAll(expiredGroups);
            }
        }

        return removedGroups;
    }

    private TrackingAttachmentGroup detachFromLifeCycle(TrackingAttachmentGroup trackingAttachmentGroup) {
        // 라이프 사이클에서 그룹을 삭제, 만료 코드에 대해 그룹이 존재하지 않아도 별도의 처리를 하지 않음

        // 그룹의 만료코드를 가져옴
        String groupExpireCode = getExpireCode(trackingAttachmentGroup.getGroupExpireDate());

        // 만료 코드에 대한 그룹 리스트를 가져옴
        List<TrackingAttachmentGroup> groupList =
                attachmentGroupLifeCycleMapper.get(groupExpireCode);

        // 그룹 리스트에서, 해당 그룹을 삭제
        groupList.remove(trackingAttachmentGroup);

        // 삭제된 그룹을 반환
        return trackingAttachmentGroup;
    }

    private void addToLifeCycle(TrackingAttachmentGroup group) {
        // 라이프 사이클에 그룹을 추가

        // 날짜를 기반으로 expire_code 를 얻어옴
        String groupExpireCode = getExpireCode(group.getGroupExpireDate());

        // 해당 기간의 라이프 사이클 expire_code가 없을 시, 생성
        // [K: expire_code V: 그룹 리스트]
        if (!attachmentGroupLifeCycleMapper.containsKey(groupExpireCode)) {
            attachmentGroupLifeCycleMapper.put(groupExpireCode, new ArrayList<>());
        }

        // expire_code에 해당하는 리스트에 그룹을 추가하여 갱신
        List<TrackingAttachmentGroup> groupList
                = attachmentGroupLifeCycleMapper.get(groupExpireCode);

        groupList.add(group);
    }

    private boolean isExpired(LocalDateTime expireDate) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        return expireDate.isBefore(now);
    }

    private String getExpireCode(LocalDateTime localDateTime) {
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();

        return String.format("%d-%d-%d-%d",year,month,day,hour);
    }

    private LocalDateTime convertCodeToLocalDateTime(String code){
        StringTokenizer tokenizer = new StringTokenizer(code,"-");
        int expectedTokens = 4;
        int[] date = new int[expectedTokens];

        // 4개의 인자가 아니라면 RunTimeError 발생
        if(tokenizer.countTokens() < expectedTokens){
            throw new RuntimeException("expected 4 integer values, but.." + tokenizer);
        }

        IntStream.range(0,expectedTokens).forEach(i->date[i]=Integer.parseInt(tokenizer.nextToken()));

        return LocalDateTime.of(date[0],date[1],date[2],date[3],0);
    }

    private LocalDateTime getExpireDate() {
        // 기본 만료시간은 지금으로 부터 24시간 뒤
        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusHours(24);
    }
}
