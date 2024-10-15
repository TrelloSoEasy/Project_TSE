package com.sparta.tse.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode{


    //jwt token 예외
    _BAD_REQUEST_UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,400,"지원되지 않는 JWT 토큰입니다."),
    _BAD_REQUEST_ILLEGAL_TOKEN(HttpStatus.BAD_REQUEST,400,"잘못된 JWT 토큰입니다."),
    _UNAUTHORIZED_INVALID_TOKEN(HttpStatus.UNAUTHORIZED,401,"유효하지 않는 JWT 서명입니다."),
    _UNAUTHORIZED_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,401,"만료된 JWT 토큰입니다."),
    _UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED,401,"JWT 토큰 검증 중 오류가 발생했습니다."),
    _FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN, 403, "관리자 권한이 없습니다."),
    _NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, 404, "JWT 토큰이 필요합니다."),
    _NO_MORE_STORE(HttpStatus.BAD_REQUEST,400,"최대 3개 운영가능"),

    _TEST_ERROR(HttpStatus.BAD_REQUEST, 404, "ApiException 예외 처리 테스트"),

    //Auth,USer관련 코드
    _USERNAME_IS_SAME(HttpStatus.BAD_REQUEST,400,"변경하려는 이름이 전과 동일합니다"),
    _NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST,400,"이메일을 찾을 수 없습니다."),
    _DELETED_USER(HttpStatus.BAD_REQUEST,400,"탈퇴한 계정입니다."),
    _PASSWORD_NOT_MATCHES(HttpStatus.UNAUTHORIZED,401,"비밀번호가 틀렸습니다."),
    _DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST,400,"중복된 이메일입니다."),
    _INVALID_EMAIL_FORM(HttpStatus.BAD_REQUEST,400,"이메일 형식이 올바르지 않습니다."),
    _INVALID_PASSWORD_FORM(HttpStatus.BAD_REQUEST,400,"비밀번호는 최소 8자 이상이어야 하며, 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."),
    _INVALID_USER_INFO(HttpStatus.BAD_REQUEST,400,"변경하려는 정보가 잘못되었습니다."),
    _INVALID_BIRTHDAY(HttpStatus.BAD_REQUEST,400,"잘못된 생일 값입니다"),
    _BAD_REQUEST_NOT_FOUND_USER(HttpStatus.BAD_REQUEST,400,"해당 유저를 찾을 수 없습니다."),
    _PASSWORD_IS_DUPLICATED(HttpStatus.BAD_REQUEST,400,"이미 사용중인 비밀번호로 변경할 수 없습니다."),
    _INVALID_USER_ROLE(HttpStatus.BAD_REQUEST,400,"잘못된 유저권한 입니다."),
    _USER_ROLE_IS_NULL(HttpStatus.BAD_REQUEST,400,"유저 권한이 없습니다."),
    _INVALID_USER_NAME(HttpStatus.BAD_REQUEST,400 ,"유저이름은 최소 3자 이상,20자 이하여야 하며, 대소문자 포함 영문,숫자만 사용가능합니다." ),

    // 메뉴 예외
    _AUTH_OWNER_MENU(HttpStatus.FORBIDDEN, 403, "메뉴 생성 및 수정은 사장님만 가능합니다."),
    _AUTH_OWNER_MENU_DELETED(HttpStatus.FORBIDDEN, 403, "메뉴 삭제는 사장님만 가능합니다."),
    _NOT_FOUND_MENU(HttpStatus.NOT_FOUND, 404, "해당 메뉴를 찾을 수 없습니다."),
    _UNAUTHORIZED_STORE_ACCESS(HttpStatus.FORBIDDEN, 403, "가게 사장님만 메뉴를 관리할 수 있습니다."),

    // 가게 예외
    _NOT_FOUND_USER(HttpStatus.NOT_FOUND, 404, "사용자를 찾을 수 없습니다."),
    _FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, 403, "사장님 권한이 없습니다."),
    _CONFLICT_STORE_NAME(HttpStatus.CONFLICT, 409, "이미 존재하는 가게 이름입니다."),
    _BAD_REQUEST_INVALID_DATA(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 데이터입니다."),
    _NOT_FOUND_STORE(HttpStatus.NOT_FOUND, 404, "가게를 찾을 수 없습니다."),
    _BAD_REQUEST_STORE_CLOSED(HttpStatus.BAD_REQUEST,400,"현재 영업시간이 아닙니다."),
    _CLOSED_STORE(HttpStatus.NOT_FOUND, 404, "해당 가게는 폐업 상태입니다."),
    _BAD_REQUEST_INVALID_EMAIL(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 이메일입니다."),



    //주문/장바구니 관련 예외
    _BAD_REQUEST_NOT_FOUND_ORDER(HttpStatus.BAD_REQUEST, 400, "해당 주문을 찾을 수 없습니다."),
    _BAD_REQUEST_INVALID_STATUS_ACCEPTED_OR_REJECTED(HttpStatus.BAD_REQUEST,400, "수락 대기 상태의 주문만 수락하거나 거절할 수 있습니다."),
    _BAD_REQUEST_INVALID_STATUS_INVALID_ORDER(HttpStatus.BAD_REQUEST,400, "진행 상태의 주문만 완료하거나 취소할 수 있습니다."),
    _FORBIDDEN_NO_AUTHORITY_MANAGE_ORDER(HttpStatus.FORBIDDEN,403,"해당 가게 사장님만 해당 주문을 관리할 수 있습니다."),
    _BAD_REQUEST_MIN_ORDER_AMOUNT(HttpStatus.BAD_REQUEST,400,"요청하신 주문금액이 가게 최소 주문금액보다 적습니다."),
    _BAD_REQUEST_CAN_NOT_CHANGE_TO_WAITING(HttpStatus.BAD_REQUEST,400,"진행중인 주문이거나 완료된 주문을 대기 상태로 변경할 수는 없습니다."),
    _FORBIDDEN_DELETE_CART(HttpStatus.FORBIDDEN,403,"해당 장바구니를 삭제할 수 있는 권한이 없습니다."),
    _BAD_REQUEST_DUPLICATE_CART_ITEM(HttpStatus.BAD_REQUEST,400,"같은 가게의 같은 메뉴를 장바구니에 넣을 수 업습니다."),
    _BAD_REQUEST_UNABLE_TO_DELETE_CART(HttpStatus.BAD_REQUEST,400,"현재 진행중인 주문으로 바뀐 장바구니는 삭제할 수 없습니다."),
    _BAD_REQUEST_NOT_FOUND_CART(HttpStatus.BAD_REQUEST,400,"해당 장바구니를 찾을 수 없습니다."),

    // 리뷰 예외
    _BAD_REQUEST_NOT_ORDERER(HttpStatus.BAD_REQUEST, 400, "주문자가 아닙니다."),
    _BAD_REQUEST_DUP_REVIEW(HttpStatus.BAD_REQUEST, 400, "이미 리뷰를 작성했습니다."),
    _BAD_REQUEST_ORDER_STATUS(HttpStatus.BAD_REQUEST, 400, "완료된 주문에 대해서만 리뷰 작성이 가능합니다."),

    // 서버 예외
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류가 발생했습니다.");




    private final HttpStatus httpStatus;
    private final Integer statusCode;
    private final String message;

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .statusCode(statusCode)
                .httpStatus(httpStatus)
                .message(message)
                .build();
    }
}
