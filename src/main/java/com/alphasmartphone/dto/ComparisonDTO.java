package com.alphasmartphone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonDTO {
    private List<PhoneDTO> phones;
    private List<String> specs;
}
