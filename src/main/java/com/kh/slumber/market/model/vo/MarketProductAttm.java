package com.kh.slumber.market.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MarketProductAttm {
    
    private String productAttmNo;
    // private String productAttmName;
    // private String productAttmSaveName;
    private String productAttmURL;
    // private String productAttmRefNo; //상품번호참조할거임
    private String productAttmCategory; //어느위치에서사용할파일인지명시 예)서브이미지,상세정보이미지...
    
}
