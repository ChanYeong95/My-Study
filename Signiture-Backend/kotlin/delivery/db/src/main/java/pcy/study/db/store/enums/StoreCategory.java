package pcy.study.db.store.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreCategory {

    CHINESE_FOOD("중식"),
    WESTERN_FOOD("양식"),
    KOREAN_FOOD("한식"),
    JAPANESE_FOOD("일식"),
    CHICKEN("치킨"),
    PIZZA("피자"),
    HAMBURGER("햄버거"),
    COFFEE_TEA("커피&차"),
    ;

    private final String display;
}
