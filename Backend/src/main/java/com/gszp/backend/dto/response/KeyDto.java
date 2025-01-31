package com.gszp.backend.dto.response;

import com.gszp.backend.model.Key;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyDto {

    private Long licenceId;

    private String name;

    private String keyCode;

    private String platform;

    private String publisher;

    public static KeyDto fromKey(Key key) {
        return KeyDto.builder()
                .keyCode(key.getKeyCode())
                .licenceId(key.getLicence().getLicenceId())
                .name(key.getLicence().getName())
                .platform(key.getLicence().getPlatform().getName())
                .publisher(key.getLicence().getPublisher().getName())
                .build();
    }
}
