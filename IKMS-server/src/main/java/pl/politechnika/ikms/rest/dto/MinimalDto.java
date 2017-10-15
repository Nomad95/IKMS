package pl.politechnika.ikms.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MinimalDto<ID,VALUE> implements Serializable{
    private static final long serialVersionUID = 1L;

    private ID id;

    private VALUE value;
}
