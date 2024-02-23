package com.example.shoppingmall_comp.domain.items.dto;

import com.example.shoppingmall_comp.domain.items.entity.Category;
import com.example.shoppingmall_comp.domain.items.entity.ItemOption;
import com.example.shoppingmall_comp.domain.items.entity.SoldOutState;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "상품 응답 DTO")
public record ItemResponse (

        @Schema(description = "상품 id", example = "1")
        Long itemId,

        @Schema(description = "상품 이름", example = "노트북")
        String itemName,

        @Schema(description = "카테고리 id", example = "1")
        Long categoryId,

        @Schema(description = "상품 가격", example = "879000")
        int price,

        @Schema(description = "상품 수량", example = "1000")
        int count,

        @Schema(description = "상품 옵션", example = "색상: WHITE")
        List<Option> optionValue,

        @Schema(description = "상품 품절상태", example = "품절")
        SoldOutState soldOutState,

        @Schema(description = "상품 상세 설명", example = "가볍고 화질이 선명해요.")
        String description

) {
    public record Option (
            String key,
            String value
    ) {
    }
}