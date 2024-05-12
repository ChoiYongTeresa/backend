package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceFoodApiResponseDto {
    ResponseFrame response;

    public List<PreferenceFoodDto> getPreferenceFoodList(){
        return Arrays.stream(this.getResponse().getBody().getItems())
                .map(itemFrame->PreferenceFoodDto.builder().
                        areaCd(itemFrame.areaCd).
                        unitySignguCd(itemFrame.unitySignguCd).
                        spctrCd(itemFrame.spctrCd).
                        preferCnttgClscd(itemFrame.preferCnttgClscd).
                        holdQy(itemFrame.holdQy).build()).toList();
    }
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class ResponseFrame{
    HeaderFrame header;
    BodyFrame body;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class BodyFrame {
    String dataType;
    ItemFrame[] items;
    String pageNo;
    String numOfRows;
    String totalCount;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class HeaderFrame {
    String resultCode;
    String resultMsg;
}

@Getter
@AllArgsConstructor
@NoArgsConstructor
class ItemFrame{
    String areaCd; // ����Ű
    String unitySignguCd; // �� ������ ��� ��
    String spctrCd; // ������ ��ȣ
    String preferCnttgClscd; // �����ڷ�����
    String holdQy; // ���� �ڵ�
}

