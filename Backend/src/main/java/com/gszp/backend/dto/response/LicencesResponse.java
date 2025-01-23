package com.gszp.backend.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicencesResponse {

    @Builder.Default
    private List<LicenceDto> licences = new ArrayList<>();
}
