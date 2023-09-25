package com.block6pathvariableheaders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    String body;
    List<String> headers;
    List<String> requestParams;


}
