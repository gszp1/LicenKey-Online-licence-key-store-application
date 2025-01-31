package com.gszp.backend.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetKeysResponse {

    @Builder.Default
    List<KeyDto> keys = new ArrayList<>();
}
