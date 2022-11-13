package com.example.result;

import java.io.Serializable;

public interface IResultCode extends Serializable {
    Integer getCode();

    String getCodeMeaning();
}
