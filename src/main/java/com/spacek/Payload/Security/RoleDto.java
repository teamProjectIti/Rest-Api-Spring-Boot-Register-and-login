package com.spacek.Payload.Security;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDto {
    private Long Id;
    @NotEmpty
    private String Name;

    public RoleDto(String name) {
    setName(name);
    }

}
