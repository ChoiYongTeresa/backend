package com.choiteresa.fonation.domain.foodmarket.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceFoodApiRequestDto {
    String serviceKey; // ����Ű
    String numOfRows; // �� ������ ��� ��
    String pageNo; // ������ ��ȣ
    String dataType; // �����ڷ�����
    String areaCd; // ���� �ڵ�
    String unitySignguCd; // ���սñ����ڵ�
    String spctrCd; // ���������ڵ�
    String preferCnttgClscd; // ��ȣ��ǰ�з��ڵ�
}
