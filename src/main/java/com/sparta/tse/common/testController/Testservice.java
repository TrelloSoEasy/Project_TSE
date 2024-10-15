package com.sparta.tse.common.testController;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Testservice {

    public int testservice() {
        throw new ApiException(ErrorStatus._TEST_ERROR);
    }
}
