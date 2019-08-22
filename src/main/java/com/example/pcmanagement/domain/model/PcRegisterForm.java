package com.example.pcmanagement.domain.model;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PcRegisterForm {
    @NotBlank(groups=ValidGroup1.class)
    private String pcId;

    @NotBlank(groups=ValidGroup1.class)
    private String type;

    @NotNull(groups=ValidGroup1.class)
    private Date buyDate;

    @NotBlank(groups=ValidGroup1.class)
    private String purpose;

    @NotBlank(groups=ValidGroup1.class)
	private String maker;

    @NotBlank(groups=ValidGroup1.class)
    private String serial;

    @NotBlank(groups=ValidGroup1.class)
	private String macAddress;

    @NotBlank(groups=ValidGroup1.class)
    private String cpu;

    @NotNull(groups=ValidGroup1.class)
    private int score;

    @NotNull(groups=ValidGroup1.class)
    private int memory;

    @NotBlank(groups=ValidGroup1.class)
    private String resolution;

    @NotBlank(groups=ValidGroup1.class)
    private String graphics;

    @NotBlank(groups=ValidGroup1.class)
	private String os;

}
